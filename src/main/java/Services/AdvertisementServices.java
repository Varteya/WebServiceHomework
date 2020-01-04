package Services;

import DTO.AdvertisementDTO;
import Repository.AdvertisementRepository;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import static javax.ejb.ConcurrencyManagementType.BEAN;

@Singleton
@Startup
@ConcurrencyManagement(BEAN)
public class AdvertisementServices {
    @EJB
    private AdvertisementRepository advertisementRepository;


    public AdvertisementDTO createAdvertisement (AdvertisementDTO advertisement) {
        int newID = advertisementRepository.createAdvertisement(advertisement);
        advertisement.setId(newID);
        return advertisement;
    }
}
