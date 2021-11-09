package Repository.Db;

import Domain.Persone;
import Repository.Repository;
import Utils.Exceptions.PersoneException;
import Utils.Generator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersoneDbRepo extends DbRepoId<Long, Persone> implements Repository<Long,Persone> {
    public PersoneDbRepo(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    public boolean save(Persone entity) {
        if(entity.getId()==null) entity.setId(generateId());
        super.sql= "insert into public.\"Persone\" values (?, ?, ?)";
        return super.save(entity);
    }

    @Override
    public Persone get(Long id) {
        sql= "select * from public.\"Persone\" where id_pers="+id.toString();
        return super.get(id);
    }

    @Override
    protected void deleteAll(){
        sql= "delete from public.\"Persone\" where id_pers != 0";
        super.deleteAll();
    }

    @Override
    public boolean update(Long id, Persone entity) {
        if(get(id)==null) throw new PersoneException("There is no persone with that id");
        sql= "update public.\"Persone\" set id_pers=?,first_name=?,last_name=? where id_pers=?";
        return super.update(id, entity);
    }

    @Override
    public boolean delete(Long id) {
        if(get(id)==null) throw new PersoneException("There is no persone with that id");
        sql="delete from public.\"Persone\" where id_pers=?";
        return super.delete(id);
    }


    @Override
    public int getSize() {
        sql="select count(*) as \"size\" from public.\"Persone\"";
        return super.getSize();
    }

    @Override
    public List<Persone> getAll() {
        sql="select * from public.\"Persone\"";
        return super.getAll();
    }

    @Override
    public List<Long> getAllIds() {
        sql= "select id_pers from public.\"Persone\"";
        return super.getAllIds();
    }

    @Override
    public Long generateId() {
        return Generator.generateId(getAllIds());
    }

    @Override
    public Persone getByOther(String... other) {
        return getByNames(other[0],other[1]);
    }

    private Persone getByNames(String first,String last){
        sql= "select * from public.\"Persone\" where first_name=? and last_name=?";
        return super.getByOther(first,last);
    }

    @Override
    protected void setSaveStatement(PreparedStatement ps, Persone entity) throws SQLException {
        ps.setLong(1, entity.getId());
        ps.setString(2, entity.getFirstName());
        ps.setString(3, entity.getLastName());
    }

    @Override
    protected Persone getGetStatement(ResultSet ps) throws SQLException {
        Persone pers=null;
        while (ps.next()) {
            Long id = ps.getLong("id_pers");
            String fN = ps.getString("first_name");
            String lN = ps.getString("last_name");
            pers = new Persone(id,fN,lN);    }
        return pers;
    }

    @Override
    protected List<Long> getAllIdStatement(ResultSet ps) throws SQLException {
        List<Long> listId=new ArrayList<>();
        while (ps.next()) {
            Long id = ps.getLong("id_pers");
            listId.add(id);
        }
        return listId;
    }

    @Override
    protected List<Persone> getAllStatement(ResultSet ps) throws SQLException {
        List<Persone> people=new ArrayList<>();
        while (ps.next()) {
            Long id = ps.getLong("id_pers");
            String fN = ps.getString("first_name");
            String lN = ps.getString("last_name");
            Persone pers = new Persone(id,fN,lN);
            people.add(pers);
        }
        return people;
    }

    @Override
    protected void setDeleteStatement(PreparedStatement ps, Long id) throws SQLException {
        ps.setLong(1, id);
    }

    @Override
    protected void setUpdateStatement(PreparedStatement ps, Long id, Persone entity) throws SQLException {
        ps.setLong(1, entity.getId());
        ps.setString(2, entity.getFirstName());
        ps.setString(3, entity.getLastName());
        ps.setLong(4, id);
    }

    @Override
    protected void setGetOtherStatement(PreparedStatement ps, String... other) throws SQLException {
        ps.setString(1,other[0]);
        ps.setString(2,other[1]);
    }


}
