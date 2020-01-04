package Repository;

import DTO.AdvertisementDTO;
import Exceptions.EntityDoesNotExistsException;

import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.sql.DataSource;

import java.sql.*;

import static javax.ejb.ConcurrencyManagementType.BEAN;

@Singleton
@Startup
@ConcurrencyManagement(BEAN)
public class AdvertisementRepository {
    @Resource(name = "Database")
    private DataSource dataSource;

    private static final String insertRequest =
            "INSERT INTO advertisement (header, body, category, phone, date, author_id)" +
                    " VALUES (?, ?, ?, ?, ?, ?);";
    private static final String userExistingRequest =
            "SELECT EXISTS (SELECT 1 FROM users WHERE id=?)";

    public int createAdvertisement (AdvertisementDTO advertisement) throws EntityDoesNotExistsException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement userExistingStatement = connection.prepareStatement(userExistingRequest);
            userExistingStatement.setInt(1, advertisement.getAuthorID());
            userExistingStatement.execute();
            try (ResultSet resultSet = userExistingStatement.getResultSet()){
                resultSet.next();
                boolean userExists = resultSet.getBoolean(1);
                if (!userExists){
                    throw new EntityDoesNotExistsException("Incorrect author_id");
                }
            }
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement(insertRequest, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, advertisement.getHeader());
                statement.setString(2, advertisement.getBody());
                statement.setString(3, advertisement.getCategory());
                statement.setString(4, advertisement.getPhoneNumber());
                statement.setDate(5, Date.valueOf(advertisement.getDate()));
                statement.setInt(6, advertisement.getAuthorID());
                statement.executeUpdate();
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    generatedKeys.next();
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
