package Controllers;


import DTO.AdvertisementDTO;
import Exceptions.EntityDoesNotExistsException;
import Services.AdvertisementServices;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import java.time.LocalDate;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Consumes(APPLICATION_JSON)
@Path("/advertisement")
public class AdvertisementRestController {
    @EJB
    private AdvertisementServices advertisementServices;

    @POST
    public AdvertisementDTO createAdvertisement (AdvertisementDTO advertisement) {
        try {
            advertisement.setDate(LocalDate.now());
            AdvertisementDTO createdAdvertisement = advertisementServices.createAdvertisement(advertisement);
            return createdAdvertisement;
        } catch (EntityDoesNotExistsException e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @DELETE
    public Response.Status deleteAdvertisement (int advertisementID) {
        try {
            advertisementServices.deleteAdvertisement(advertisementID);
            return Response.Status.NO_CONTENT;
        } catch (EntityDoesNotExistsException e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @GET
    @Path("{id}")
    public AdvertisementDTO findAdvertisement (@PathParam("id") int id){
        try {
            return advertisementServices.findAdvertisement(id);
        } catch (EntityDoesNotExistsException e){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}