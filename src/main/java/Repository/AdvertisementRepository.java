package Repository;

import DTO.AdvertisementDTO;
import DTO.UserDTO;
import Exceptions.EntityDoesNotExistsException;

import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.sql.DataSource;

import java.sql.*;
import java.time.LocalDate;

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
    private static final String advertisementExistingRequest =
            "SELECT EXISTS (SELECT 1 FROM advertisement WHERE id=?)";
    private static final String advertisementDeletingRequest =
            "DELETE FROM advertisement WHERE id=?";
    private static final String findingAdvertisementByIDRequest =
            "SELECT * FROM advertisement WHERE id=?";


    public int createAdvertisement (AdvertisementDTO advertisement) throws EntityDoesNotExistsException {
        try (Connection connection = dataSource.getConnection()) {
            if (!checkUserExisting(advertisement.getAuthorID())) {
                throw new EntityDoesNotExistsException("Incorrect author_id");
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

    public void deleteAdvertisement (int advertisementID) throws EntityDoesNotExistsException {
        try (Connection connection = dataSource.getConnection()){
            if (!checkAdvertisementExisting(advertisementID)){
                throw new EntityDoesNotExistsException("Incorrect advertisement_id");
            }
            PreparedStatement statement = connection.prepareStatement(advertisementDeletingRequest);
            statement.setInt(1, advertisementID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public AdvertisementDTO findAdvertisement (int advertisementID) throws EntityDoesNotExistsException {
        try (Connection connection = dataSource.getConnection()) {
            if (!checkAdvertisementExisting(advertisementID)){
                throw new EntityDoesNotExistsException("Incorrect advertisement_id");
            }
            PreparedStatement statement = connection.prepareStatement(findingAdvertisementByIDRequest);
            statement.setInt(1, advertisementID);
            statement.execute();
            try (ResultSet resultSet = statement.getResultSet()){
                resultSet.next();
                int id = resultSet.getInt(1);
                String header = resultSet.getString(2);
                String body = resultSet.getString(3);
                String category = resultSet.getString(4);
                String phone = resultSet.getString(5);
                LocalDate date = resultSet.getDate(6).toLocalDate();
                int authorID = resultSet.getInt(7);
                return new AdvertisementDTO(id, header, body, category, phone, date, authorID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("SQLException");
        }
    }

    private boolean checkAdvertisementExisting (int advertisementID) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement advertisementExistingStatement = connection.prepareStatement(advertisementExistingRequest);
        advertisementExistingStatement.setInt(1, advertisementID);
        advertisementExistingStatement.execute();
        try (ResultSet resultSet = advertisementExistingStatement.getResultSet()) {
            resultSet.next();
            return resultSet.getBoolean(1);
        }
    }

    private boolean checkUserExisting (int userID) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement userExistingStatement = connection.prepareStatement(userExistingRequest);
        userExistingStatement.setInt(1, userID);
        userExistingStatement.execute();
        try (ResultSet resultSet = userExistingStatement.getResultSet()) {
            resultSet.next();
            return resultSet.getBoolean(1);
        }
    }
}
