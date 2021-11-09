package Repository.Db;

import Domain.Relationship;
import Repository.RelationshipRepo;
import Utils.Exceptions.EntityRepoException;
import Utils.Exceptions.RelationshipRepoException;
import Utils.Generator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelationshipDbRepo extends DbRepoId<Long, Relationship> implements RelationshipRepo {
    public RelationshipDbRepo(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    public boolean save(Relationship entity) {
        if(entity.getId()==null) entity.setId(generateId());
        try{
            Relationship rel=null;
            rel=getByOther(entity.getFirstUserName(),entity.getSecondUserName());
            if(rel!=null)throw new RelationshipRepoException("There is a relationship with the same usernames");
            else{
                super.sql= "insert into public.\"Relationship\" values (?, ?, ?)";
                return super.save(entity);
            }
        }
        catch(EntityRepoException e){
            super.sql= "insert into public.\"Relationship\" values (?, ?, ?)";
            return super.save(entity);
        }
    }

    @Override
    public Relationship get(Long id) {
        sql= "select * from public.\"Relationship\" where id_rel="+id.toString();
        return super.get(id);
    }

    @Override
    public boolean update(Long id, Relationship entity) {
        if(get(id)==null) throw new RelationshipRepoException("There is no relationship with that id");
        sql= "update public.\"Relationship\" set id_rel=?,first_username=?,second_username=? where id_rel=?";
        return super.update(id, entity);

    }

    @Override
    public boolean delete(Long id) {
        if(get(id)==null) throw new RelationshipRepoException("There is no relationship with that id");
        sql="delete from public.\"Relationship\" where id_rel=?";
        return super.delete(id);
    }

    @Override
    protected void deleteAll() {
        sql= "delete from public.\"Relationship\" where id_rel != 0";
        super.deleteAll();
    }

    @Override
    public int getSize() {
        sql="select count(*) as \"size\" from public.\"Relationship\"";
        return super.getSize();
    }

    @Override
    public List<Relationship> getAll() {
        sql="select * from public.\"Relationship\"";
        return super.getAll();
    }

    @Override
    public List<Long> getAllIds() {
        sql= "select id_rel from public.\"Relationship\"";
        return super.getAllIds();
    }


    @Override
    public Long generateId() {
        return Generator.generateId(getAllIds());
    }

    @Override
    public Relationship getByOther(String... other) {
        return getByUserNames(other[0],other[1]);
    }

    @Override
    public Relationship getByUserNames(String username1, String username2) {
        sql= "select * from public.\"Relationship\" where first_username=? and second_username= ?";
        return super.getByOther(username1,username2);
    }


    @Override
    protected void setSaveStatement(PreparedStatement ps, Relationship entity) throws SQLException {
        ps.setLong(1, entity.getId());
        ps.setString(2, entity.getFirstUserName());
        ps.setString(3, entity.getSecondUserName());
    }

    @Override
    protected Relationship getGetStatement(ResultSet ps) throws SQLException {
        Relationship rel=null;
        while (ps.next()) {
            Long id = ps.getLong("id_rel");
            String fU = ps.getString("first_username");
            String sU = ps.getString("second_username");
            rel =new Relationship(id,fU,sU);
        }
        return rel;
    }



    @Override
    protected List<Long> getAllIdStatement(ResultSet ps) throws SQLException {
        List<Long> listId=new ArrayList<>();
        while (ps.next()) {
            Long id = ps.getLong("id_rel");
            listId.add(id);
        }
        return listId;
    }

    @Override
    protected List<Relationship> getAllStatement(ResultSet ps) throws SQLException {
        List<Relationship> relations=new ArrayList<>();
        while (ps.next()) {
            Long id = ps.getLong("id_rel");
            String fU = ps.getString("first_username");
            String sU = ps.getString("second_username");
            Relationship rel =new Relationship(id,fU,sU);
            relations.add(rel);
        }
        return relations;
    }

    @Override
    protected void setDeleteStatement(PreparedStatement ps, Long id) throws SQLException {
        ps.setLong(1, id);
    }

    @Override
    protected void setUpdateStatement(PreparedStatement ps, Long id, Relationship entity) throws SQLException {
        ps.setLong(1, entity.getId());
        ps.setString(2, entity.getFirstUserName());
        ps.setString(3, entity.getSecondUserName());
        ps.setLong(4, id);
    }

    @Override
    protected void setGetOtherStatement(PreparedStatement ps, String... other) throws SQLException {
        ps.setString(1,other[0]);
        ps.setString(2,other[1]);
    }


}
