package sg.edu.nus.iss.paf_assessment.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;

import sg.edu.nus.iss.paf_assessment.model.FormFields;
import sg.edu.nus.iss.paf_assessment.repo.ListingsRepository;

@Service
public class ListingsService {
    
    @Autowired
    private ListingsRepository listingRepo;

    public List<Document> searchListings(FormFields searchParams) {
        
        return listingRepo.searchListings(searchParams);
    }

    public Optional<Document> getListingById(String listingId) {
        
        return listingRepo.getListingById(listingId);
    }

    public List<String> getCountries() {
        return listingRepo.countriesList();
    }
}
