package sg.edu.nus.iss.paf_assessment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.paf_assessment.service.ListingsService;

@Controller
@RequestMapping
public class ListingsController {
    
    @Autowired
    private ListingsService svc;

    @GetMapping
    public ModelAndView getIndexPage(HttpSession session) {
        ModelAndView mav = new ModelAndView("landingPage.html");
        mav.addObject("searchParams", new HashMap<String,String>());
        return mav;
    }

    @GetMapping(path="/search")
    public ModelAndView getSearchedListings(@RequestParam HashMap<String, String> searchParams) {

        List<Document> searchedListings = svc.searchListings(searchParams);

        ModelAndView mav = new ModelAndView("resultListings.html");
        mav.addObject("resultListings", searchedListings);

        return mav;
    }

    @GetMapping(path="/search/{accomsId}")
    public ModelAndView displayListing(@PathVariable String listingId) {
        ModelAndView mav = new ModelAndView();

        Optional<Document> listing = svc.getListingById(listingId);

        if (listing.isEmpty()) {
            mav.setViewName("not_found");
            mav.addObject("errMessage", "Unable to find accomdations");
            return mav;
        }

        mav.addObject("listing", svc.getListingById(listingId).get());
        mav.setViewName("booking.html");
        return mav;
    }
}
