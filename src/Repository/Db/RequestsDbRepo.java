package Repository.Db;

import Domain.Relationship;
import Repository.Repository;
import Utils.Exceptions.EntityRepoException;
import Utils.Exceptions.RelationshipRepoException;
import Utils.Generator;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RequestsDbRepo extends DbRepoId<Long, Relationship> implements Repository<Long,Relationship> {

    /**
     * Basic constructor of a Db Repository
     *
     * @param url      to the database
     * @param username the user of the database
     * @param password the password of the user
     */
    public RequestsDbRepo(String url, String username, String password) {
        super(url, username, password);
    }


    @Override
    public boolean save(Relationship entity) {
        if(entity.getId()==null)
            entity.setId(generateId());

         Relationship rel;
         super.sql= "insert into public.\"Requests\" values (?, ?, ?,?,?)";
         return super.save(entity);

    }

    @Override
    public boolean update(Long id, Relationship entity) {
        if(get(id)==null) throw new RelationshipRepoException("There is no relationship with that id");
        sql= "update public.\"Requests\" set id_r=?,first_username=?,second_username=?, the_date=? , status=? where id_r=?";
        return super.update(id, entity);
    }

    /**
     * Deletes all the data from the repository
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on processing the data
     */
    @Override
    protected void deleteAll() {
        sql= "delete from public.\"Requests\"";
        super.deleteAll();
    }

    /**
     * Retrieves the corespondent relationship with that id
     * @param id the id of the relationship to be found
     * @return the relationship that has that id or null if there is no relationship with that id
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on retrieving the data
     */
    @Override
    public Relationship get(Long id) {
        sql= "select * from public.\"Requests\" where id_r="+id.toString();
        return super.get(id);
    }

    @Override
    public boolean delete(Long id) {
        if (get(id) == null) throw new RelationshipRepoException("There is no relationship with that id");
        sql = "delete from public.\"Requests\" where id_r=?";
        return super.delete(id);
    }

        @Override
    public List<Relationship> getAll() {
        sql="select * from public.\"Requests\"";
        return super.getAll();
    }

    @Override
    public List<Long> getAllIds() {
        sql= "select id_r from public.\"Requests\"";
        return super.getAllIds();
    }

    @Override
    public Long generateId() {
        return Generator.generateId(getAllIds());
    }

    @Override
    protected void setSaveStatement(PreparedStatement ps, Relationship entity) throws SQLException {
        ps.setLong(1, entity.getId());
        ps.setString(2, entity.getFirstUserName());
        ps.setString(3, entity.getSecondUserName());
        if(entity.getDtf()!=null)
            ps.setDate(4, Date.valueOf(entity.getDtf()));
        else
            ps.setNull(4, Types.DATE);
        ps.setString(5,entity.getStatus());
    }

    @Override
    protected Relationship getGetStatement(ResultSet ps) throws SQLException {
        Relationship rel=null;
        while (ps.next()) {
            Long id = ps.getLong("id_r");
            String fU = ps.getString("first_username");
            String sU = ps.getString("second_username");

            LocalDate the_data;
            try {
                the_data= ps.getDate("the_date").toLocalDate();
            }catch(PSQLException e){
                the_data=null;
            }
            String st=ps.getString("status");
            rel =new Relationship(id,fU,sU,the_data,st);

        }
        return rel;
    }

    @Override
    protected List<Long> getAllIdStatement(ResultSet ps) throws SQLException {
        List<Long> listId=new ArrayList<>();
        while (ps.next()) {
            Long id = ps.getLong("id_r");
            listId.add(id);
        }
        return listId;
    }

    @Override
    protected List<Relationship> getAllStatement(ResultSet ps) throws SQLException {
        List<Relationship> relations=new ArrayList<>();
        while (ps.next()) {
            Long id = ps.getLong("id_r");
            String fU = ps.getString("first_username");
            String sU = ps.getString("second_username");
            LocalDate lu;
            try {
                lu=ps.getDate("the_date").toLocalDate();
            }catch(PSQLException e){
                lu=null;
            }
            String st=ps.getString("status");
            Relationship rel =new Relationship(id,fU,sU,lu,st);

            relations.add(rel);
        }
        return relations;
    }

    /**
     * This function fills the request( prepared statement) with actual data for the sql
     * command for deleting a relationship from repository using his id
     * @param ps a PreparedStatement which will be used to fill the sql request for the db
     * @param id the id of the relationship to be deleted from the repository
     * @throws SQLException when there are problems with the prepared statements
     */
    @Override
    protected void setDeleteStatement(PreparedStatement ps, Long id) throws SQLException {
        ps.setLong(1, id);
    }

    @Override
    protected void setUpdateStatement(PreparedStatement ps, Long id, Relationship entity) throws SQLException {
        ps.setLong(1, entity.getId());
        ps.setString(2, entity.getFirstUserName());
        ps.setString(3, entity.getSecondUserName());

        if(entity.getDtf()!=null)
            ps.setDate(4, Date.valueOf(entity.getDtf()));
        else
            ps.setNull(4, Types.DATE);

        ps.setString(5, entity.getStatus());
        ps.setLong(6, id);
    }

    @Override
    protected void setGetOtherStatement(PreparedStatement ps, String... other) throws SQLException {

    }
}
