package Controllers;

import DTO.AdvertisementDTO;
import DTO.UserDTO;
import Repository.UserRepository;
import Services.UserServices;


import javax.ejb.EJB;
import javax.ws.rs.*;

import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Consumes(APPLICATION_JSON)
@Path("/user")
public class UserRestController {
    @EJB
    private UserServices userServices = new UserServices();

    @POST
    public UserDTO createUser (UserDTO user){
        UserDTO createdUser = userServices.createUser(user);
        System.out.println(createdUser.toString());
        return createdUser;
    }

    @GET
    public String getMessage () {
        System.out.println("sdlfsdlfo");
        return "Hello world";
    }

    @GET
    @Path("/{id}/adverts")
    public List<AdvertisementDTO> findUsersAdvertisements (@PathParam("id") int id) {
        return userServices.findUsersAdvertisements(id);
    }
}
