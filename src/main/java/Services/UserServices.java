package Services;

import DTO.UserDTO;
import Repository.UserRepository;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import static javax.ejb.ConcurrencyManagementType.BEAN;


@Singleton
@Startup
@ConcurrencyManagement(BEAN)
public class UserServices {
    @EJB
    private UserRepository userRepository;

    public UserDTO createUser(UserDTO user) {
        int result = userRepository.createUser(user);
        user.setId(result);
        return user;
    }
}
