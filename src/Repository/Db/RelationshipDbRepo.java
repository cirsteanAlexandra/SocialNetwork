package Repository.Db;

import Domain.Relationship;
import Repository.RelationshipRepo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RelationshipDbRepo extends DbRepoId<Long, Relationship> implements RelationshipRepo {
    public RelationshipDbRepo(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    public Long generateId() {
        return null;
    }

    @Override
    protected void setSaveStatement(PreparedStatement ps, Relationship entity) throws SQLException {

    }

    @Override
    protected Relationship getGetStatement(ResultSet ps) throws SQLException {
        return null;
    }

    @Override
    protected Relationship getGetOtherStatement(ResultSet ps) throws SQLException {
        return null;
    }

    @Override
    protected List<Long> getAllIdStatement(ResultSet ps) throws SQLException {
        return null;
    }

    @Override
    protected List<Relationship> getAllStatement(ResultSet ps) throws SQLException {
        return null;
    }

    @Override
    protected void setDeleteStatement(PreparedStatement ps, Long aLong) throws SQLException {

    }

    @Override
    protected void setUpdateStatement(PreparedStatement ps, Long aLong, Relationship entity) throws SQLException {

    }

    @Override
    protected void setGetOtherStatement(PreparedStatement ps, String... other) throws SQLException {

    }

    @Override
    public Relationship getByUserNames(String username1, String username2) {
        return null;
    }
}
