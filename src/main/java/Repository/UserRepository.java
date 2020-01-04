package Repository;

import DTO.UserDTO;

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
public class UserRepository {
    @Resource(name = "Database")
    private DataSource dataSource;

    private static final String insertRequest =
            "INSERT INTO users (name, surname, email, is_company) VALUES (?, ?, ?, ?);";

    public int createUser(UserDTO user) {
        try {
            Connection connection = dataSource.getConnection();
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
}
