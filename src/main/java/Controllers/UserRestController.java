package Controllers;

import DTO.UserDTO;
import Repository.UserRepository;
import Services.UserServices;


import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Consumes(APPLICATION_JSON)
@Path("/user")
public class UserRestController {
    @EJB
    private UserServices userServices;

    @POST
    public String createUser (UserDTO user){
        UserDTO createdUser = userServices.createUser(user);
        System.out.println(createdUser.toString());
        return "User " + createdUser.toString() + " created successfully";
    }

    @GET
    public String getMessage () {
        System.out.println("sdlfsdlfo");
        return "Hello world";
    }
}
