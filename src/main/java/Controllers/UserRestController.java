package Controllers;

import DTO.UserDTO;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Consumes(APPLICATION_JSON)
@Path("/user")
public class UserRestController {
    @POST
    public String createUser (UserDTO user){
        System.out.println(user.toString());
        return "User created successfully";
    }

    @GET
    public String getMessage () {
        System.out.println("sdlfsdlfo");
        return "Hello world";
    }
}
