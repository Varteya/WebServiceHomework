package Repository;

import DTO.AdvertisementDTO;
import DTO.UserDTO;

import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.ejb.ConcurrencyManagementType.BEAN;

@Singleton
@Startup
@ConcurrencyManagement(BEAN)
public class UserRepository {
    @Resource(name = "Database")
    private DataSource dataSource;

    private static final String insertRequest =
            "INSERT INTO users (name, surname, email, is_company) VALUES (?, ?, ?, ?);";
    private static final String findUsersAdvertisementsRequest =
            "SELECT * FROM advertisement WHERE author_id=?";

    public int createUser(UserDTO user) {
        try (Connection connection = dataSource.getConnection()){
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement(insertRequest, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, user.getName());
                statement.setString(2, user.getSurname());
                statement.setString(3, user.getEmail());
                statement.setBoolean(4, user.isCompany());
                statement.executeUpdate();

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    generatedKeys.next();
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<AdvertisementDTO> findUsersAdvertisements (int userID) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(findUsersAdvertisementsRequest);
            statement.setInt(1, userID);
            statement.execute();
            List<AdvertisementDTO> answer = new ArrayList<>();
            try (ResultSet resultSet = statement.getResultSet()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String header = resultSet.getString(2);
                    String body = resultSet.getString(3);
                    String category = resultSet.getString(4);
                    String phone = resultSet.getString(5);
                    LocalDate date = resultSet.getDate(6).toLocalDate();
                    int authorID = resultSet.getInt(7);
                    answer.add(new AdvertisementDTO(id, header, body, category, phone, date, authorID));
                }
            }
            return answer;
        } catch (SQLException e) {
            throw new RuntimeException("SQLException");
        }
    }
}
