package com.example.Repository.Db;

import com.example.Domain.Message;
import com.example.Domain.Persone;
import com.example.Domain.User;
import com.example.Repository.MessageRepo;
import com.example.Utils.Exceptions.EntityRepoException;
import com.example.Utils.Exceptions.MessageRepoException;
import com.example.Utils.Generator;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessageDbRepo extends DbRepoId<Long,Message> implements MessageRepo {
    /**
     * Basic constructor of a Db Repository
     * @param url      to the database
     * @param username the user of the database
     * @param password the password of the user
     */
    public MessageDbRepo(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    public boolean save(Message entity) {
        if(entity.getId()==null && entity.getReceivers().size()==1) {
            entity.setId(generateId());
            super.sql= "insert into public.\"Messages\" values (?, ?, ?, ?, ?, ?)";
            return super.save(entity);
        }
        else {
            if(entity.getReceivers().size()==1){
                super.sql= "insert into public.\"Messages\" values (?, ?, ?, ?, ?, ?)";
                return super.save(entity);
            }
            boolean process=true;
            boolean firstTime=true;
            for (User user: entity.getReceivers()){
                Message mess= new Message(entity.getFrom(), entity.getMessage(), Arrays.asList(user),entity.getDate(),entity.getReply());
                if(firstTime && entity.getId()!=null) {
                    mess.setId(entity.getId());
                    firstTime=false;
                }
                else mess.setId(generateId());
                super.sql= "insert into public.\"Messages\" values (?, ?, ?, ?, ?, ?)";
                process= process && super.save(mess);
            }
            return process;
        }
    }

    @Override
    public Message get(Long id) {
        sql= "select * from public.\"Messages\" where id_mess="+id.toString();
        return super.get(id);
    }

    @Override
    public boolean update(Long id, Message entity) {
        if(get(id)==null) throw new MessageRepoException("There is no message with that id");
        sql= "update public.\"Messages\" set id_mess=?,sender=?,receiver=?,date_time=?,description=?,message_reply=? where id_mess=?";
        return super.update(id, entity);
    }

    @Override
    public boolean delete(Long id) {
        if(get(id)==null) throw new MessageRepoException("There is no message with that id");
        sql="delete from public.\"Messages\" where id_mess=?";
        return super.delete(id);
    }

    @Override
    protected void deleteAll() {
        sql= "delete from public.\"Messages\" where id_mess != 0";
        super.deleteAll();
    }

    @Override
    public int getSize() {
        sql="select count(*) as \"size\" from public.\"Messages\"";
        return super.getSize();
    }

    @Override
    public List<Message> getAll() {
        sql="select * from public.\"Messages\"";
        return super.getAll();
    }

    @Override
    public List<Long> getAllIds() {
        sql= "select id_mess from public.\"Messages\"";
        return super.getAllIds();
    }

    @Override
    public Message getByOther(String... other) {
       List<Message> mess=getBySR(other[0],other[1]);
       if(mess.isEmpty()) mess=getBySR(other[1],other[0]);
       return mess.isEmpty() ? null : mess.get(mess.size()-1);
    }

    @Override
    public Long generateId() {
        return Generator.generateId(getAllIds());
    }

    @Override
    protected void setSaveStatement(PreparedStatement ps, Message entity) throws SQLException {
        ps.setLong(1, entity.getId());
        ps.setString(2, entity.getFrom().getUsername());
        ps.setString(3, entity.getReceivers().get(0).getUsername());
        ps.setObject(4,entity.getDate());
        ps.setString(5, entity.getMessage());
        if (entity.getReply()!=null){
            ps.setLong(6,entity.getReply().getId());
        }
        else ps.setNull(6, Types.BIGINT);
    }

    @Override
    protected Message getGetStatement(ResultSet ps) throws SQLException {
        Message mess=null;
        while (ps.next()) {
            Long id = ps.getLong("id_mess");
            String from = ps.getString("sender");
            String to = ps.getString("receiver");
            LocalDateTime dateTime= ps.getObject("date_time",LocalDateTime.class);
            String message= ps.getString("description");
            Long id_replay;
            try {
                id_replay = ps.getLong("message_replay");
            }catch(PSQLException e){
                id_replay=null;
            }
            Message reply=null;
            if(id_replay!=null) reply=get(id_replay);
            mess=new Message(id,new User(from,new Persone("","")), message,Arrays.asList(new User(to,new Persone("",""))),dateTime,reply);
        }
        return mess;
    }

    @Override
    protected List<Long> getAllIdStatement(ResultSet ps) throws SQLException {
        List<Long> listId=new ArrayList<>();
        while (ps.next()) {
            Long id = ps.getLong("id_mess");
            listId.add(id);
        }
        return listId;
    }

    @Override
    protected List<Message> getAllStatement(ResultSet ps) throws SQLException {
        List<Message> messages=new ArrayList<>();
        while (ps.next()) {
            Long id = ps.getLong("id_mess");
            String from = ps.getString("sender");
            String to = ps.getString("receiver");
            LocalDateTime dateTime= ps.getObject("date_time",LocalDateTime.class);
            String message= ps.getString("description");
            Long id_replay;
            try {
                id_replay = ps.getLong("message_replay");
            }catch(PSQLException e){
                id_replay=null;
            }
            Message reply=null;
            if(id_replay!=null) reply=get(id_replay);
            Message mess=new Message(id,new User(from,new Persone("","")), message,Arrays.asList(new User(to,new Persone("",""))),dateTime,reply);
            messages.add(mess);
        }
        return messages;
    }

    @Override
    protected void setDeleteStatement(PreparedStatement ps, Long id) throws SQLException {
        ps.setLong(1, id);
    }

    @Override
    protected void setUpdateStatement(PreparedStatement ps, Long id, Message entity) throws SQLException {
        ps.setLong(1, entity.getId());
        ps.setString(2, entity.getFrom().getUsername());
        ps.setString(3, entity.getReceivers().get(0).getUsername());
        ps.setObject(4,entity.getDate());
        ps.setString(5, entity.getMessage());
        if (entity.getReply()!=null){
            ps.setLong(6,entity.getReply().getId());
        }
        else ps.setNull(6, Types.BIGINT);
        ps.setLong(7,id);
    }

    @Override
    protected void setGetOtherStatement(PreparedStatement ps, String... other) throws SQLException {
        ps.setString(1,other[0]);
        ps.setString(2,other[1]);
    }

    @Override
    public List<Message> getBySR(String sender, String Receiver) {
        List<Message> list;
        sql = "select * from public.\"Messages\" where sender=? and receiver=?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            setGetOtherStatement(ps, sender, Receiver);
            ResultSet resultSet = ps.executeQuery();
            list=getAllStatement(resultSet);
            return list;
        } catch (SQLException e) {
            throw new EntityRepoException(e.getMessage());
        }
    }

    @Override
    public List<Message> getByDateTime(LocalDateTime dateTime) {
        sql= "select * from public.\"Messages\" where date_time=?";
        List<Message> list;try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1,dateTime);
            ResultSet resultSet = ps.executeQuery();
            list=getAllStatement(resultSet);
            return list;
        } catch (SQLException e) {
            throw new EntityRepoException(e.getMessage());
        }
    }
}
