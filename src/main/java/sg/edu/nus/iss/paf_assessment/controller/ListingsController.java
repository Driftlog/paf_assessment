package sg.edu.nus.iss.paf_assessment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.paf_assessment.model.FormFields;
import sg.edu.nus.iss.paf_assessment.service.ListingsService;
import sg.edu.nus.iss.paf_assessment.util.ListingUtil;

@Controller
@RequestMapping
public class ListingsController {
    
    @Autowired
    private ListingsService svc;

    @GetMapping
    public ModelAndView getIndexPage(HttpSession session, Model model) {
        ModelAndView mav = new ModelAndView("landingPage.html");
        
        FormFields formFields = new FormFields();
        mav.addObject("countries", svc.getCountries());
        mav.addObject("formFields", formFields);
        return mav;
    }

    @GetMapping(path="/search")
    public ModelAndView getSearchedListings(@ModelAttribute FormFields formFields, Model model) {

        List<Document> searchedListings = svc.searchListings(formFields);
        ModelAndView mav = new ModelAndView("resultListings.html");

        if (searchedListings.isEmpty()) {
            mav.addObject("errMessage", "Unable to find any listing!");
            mav.setStatus(HttpStatus.NOT_FOUND);
        }

        System.out.println(searchedListings.get(0).get("id"));
        mav.addObject("resultListings", searchedListings);

        return mav;
    }

    @PostMapping(path="/{id}")
    public ModelAndView displayListing(@PathVariable String id) {

        ModelAndView mav = new ModelAndView();

        Optional<Document> listing = svc.getListingById(id);

        if (listing.isEmpty()) {
            mav.setViewName("not_found");
            mav.addObject("errMessage", "Unable to find accomdations");
            return mav;
        }

        mav.addObject("listing", svc.getListingById(id).get());
        mav.setViewName("booking.html");
        return mav;
    }
}
