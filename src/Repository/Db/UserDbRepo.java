package Repository.Db;

import Domain.Persone;
import Domain.User;
import Repository.UserRepo;
import Utils.Exceptions.EntityRepoException;
import Utils.Exceptions.UserRepoException;
import Utils.Generator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDbRepo extends DbRepoId<Long, User> implements UserRepo {

    public UserDbRepo(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    public boolean save(User entity) {
        if(entity.getId()==null) entity.setId(generateId());
        try{
            User user=null;
            user=getByOther(entity.getUsername());
            if(user!=null)throw new UserRepoException("There is a user with the same username");
            else{
                super.sql= "insert into public.\"Users\" values (?, ?, ?)";
                return super.save(entity);
            }
        }
        catch(EntityRepoException e){
            super.sql= "insert into public.\"Users\" values (?, ?, ?)";
            return super.save(entity);
        }
    }

    @Override
    public User get(Long id) {
        sql= "select * from public.\"Users\" where id_user="+id.toString();
        return super.get(id);
    }

    public void restoreToDefault(){
        deleteAll();
    }
    @Override
    protected void deleteAll(){
        sql= "delete from public.\"Users\" where id_user != 0";
        super.deleteAll();
    }

    @Override
    public boolean update(Long id, User entity) {
        if(get(id)==null) throw new UserRepoException("There is no user with that id");
        sql= "update public.\"Users\" set id_user=?,username=?,id_pers=? where id_user=?";
        return super.update(id, entity);
    }

    @Override
    public boolean delete(Long id) {
        if(get(id)==null) throw new UserRepoException("There is no user with that id");
        sql="delete from public.\"Users\" where id_user=?";
        return super.delete(id);
    }


    @Override
    public List<User> getAll() {
        sql="select * from public.\"Users\"";
        return super.getAll();
    }

    @Override
    public List<Long> getAllIds() {
        sql= "select id_user from public.\"Users\"";
        return super.getAllIds();
    }

    @Override
    public User getByOther(String... other) {
       return getByUserName(other[0]);
    }


    @Override
    public User getByUserName(String username) {
        sql= "select * from public.\"Users\" where username=?";
        return super.getByOther(username);
    }

    @Override
    public Long generateId() {
        return Generator.generateId(getAllIds());
    }

    @Override
    public int getSize() {
        sql="select count(*) as \"size\" from public.\"Users\"";
        return super.getSize();
    }

    @Override
    protected void setSaveStatement(PreparedStatement ps, User entity) throws SQLException {
        ps.setLong(1, entity.getId());
        ps.setString(2, entity.getUsername());
        ps.setLong(3, entity.getPers().getId());
    }

    @Override
    protected User getGetStatement(ResultSet ps)throws SQLException {
        User user=null;
        while (ps.next()) {
            Long id = ps.getLong("id_user");
            String username = ps.getString("username");
            Long id_pers = ps.getLong("id_pers");
            user = new User(id,username, new Persone(id_pers,"",""));
        }
        return user;
    }

    @Override
    protected int getSizeStatement(ResultSet ps) throws SQLException {
        int size=0;
        if (ps.next()) {
            size = ps.getInt("size");
        }
        return size;
    }

    @Override
    protected User getGetOtherStatement(ResultSet ps) throws SQLException {
        return getGetStatement(ps);
    }

    @Override
    protected List<Long> getAllIdStatement(ResultSet ps)throws SQLException {
        List<Long> listId=new ArrayList<>();
        while (ps.next()) {
            Long id = ps.getLong("id_user");
            listId.add(id);
        }
        return listId;
    }

    @Override
    protected List<User> getAllStatement(ResultSet ps) throws SQLException{
        List<User> users=new ArrayList<>();
        while (ps.next()) {
            Long id = ps.getLong("id_user");
            String username = ps.getString("username");
            Long id_pers = ps.getLong("id_pers");
            User utilizator = new User(id,username, new Persone(id_pers,"",""));
            users.add(utilizator);
        }
        return users;
    }

    @Override
    protected void setDeleteStatement(PreparedStatement ps, Long id)throws SQLException {
        ps.setLong(1, id);
    }

    @Override
    protected void setUpdateStatement(PreparedStatement ps, Long id, User entity)throws SQLException{
        ps.setLong(1, entity.getId());
        ps.setString(2, entity.getUsername());
        ps.setLong(3, entity.getPers().getId());
        ps.setLong(4, id);
    }

    @Override
    protected void setGetOtherStatement(PreparedStatement ps, String... other) throws SQLException {
        ps.setString(1,other[0]);
    }

}
