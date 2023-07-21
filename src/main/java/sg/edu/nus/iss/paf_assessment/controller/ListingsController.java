package sg.edu.nus.iss.paf_assessment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping
public class ListingsController {
    

    @GetMapping
    public ModelAndView getIndexPage(ModelAndView mav, HttpSession session) {
        
    }

    @GetMapping(path="/search")
    public ModelAndView getSearchedListings(@RequestParam MultiValueMap<String, String> searchParams) {
        
    }
}
