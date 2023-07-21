package sg.edu.nus.iss.paf_assessment.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;

@Repository
public class ListingsRepository {
    
    @Autowired
    private MongoTemplate template;


    // Query
    // db.listings.find({
    //     "address.country" : {
    //         $regex: "Australia",
    //         $options: "i"
    //     },
    //     accommodates : 2,
    //     price : {$gte: 0, $lte: 1000  }
    //         }, 
    //      {_id : 0, name: 1, price: 1, "images.picture_url":1 }).sort({price: -1});
    public List<Document> searchListings(HashMap<String, String> searchParams) {

        Criteria criteria1 = new Criteria().andOperator(Criteria.where("address.country").regex(searchParams.get("country"), "i"),
                                            Criteria.where("accomodates").is(searchParams.get("pax")),
                                            Criteria.where("price").gte(searchParams.get("min"))
                                            .andOperator(Criteria.where("price").lte("max")));


        Query query = Query.query(criteria1).with(Sort.by(Sort.Direction.DESC, "price"));
        query.fields()
            .exclude("_id")
            .include("name", "price", "images.price_url");

        List<Document> results = template.find(query, Document.class, "listings");

        return results;
    }

    //Query db.listings.find({_id: "16134812"});
    public Optional<Document> getListingById(String listingId) {
        Query query = Query.query(Criteria.where("_id").is(listingId));
        
        List<Document> result = template.find(query, Document.class, "listings");

        if (result.isEmpty()) {
            return Optional.empty();
        }
        
        return Optional.of(result.get(0));
    }
}
