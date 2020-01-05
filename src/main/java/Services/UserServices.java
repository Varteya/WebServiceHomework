package Services;

import DTO.AdvertisementDTO;
import DTO.UserDTO;
import Repository.UserRepository;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import java.util.List;

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

    public List<AdvertisementDTO> findUsersAdvertisements (int userID) {
        return userRepository.findUsersAdvertisements(userID);
    }
}
