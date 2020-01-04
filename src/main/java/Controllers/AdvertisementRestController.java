package Controllers;


import DTO.AdvertisementDTO;
import Exceptions.EntityDoesNotExistsException;
import Services.AdvertisementServices;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import java.time.LocalDate;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Consumes(APPLICATION_JSON)
@Path("/advertisement")
public class AdvertisementRestController {
    @EJB
    private AdvertisementServices advertisementServices;

    @POST
    public String createAdvertisement(AdvertisementDTO advertisement) {
        try {
            advertisement.setDate(LocalDate.now());
            AdvertisementDTO createdAdvertisement = advertisementServices.createAdvertisement(advertisement);
            return "Advertisement " + createdAdvertisement.toString() + " created successfully!";
        } catch (EntityDoesNotExistsException e) {
            return e.getMessage();
        }
    }
}