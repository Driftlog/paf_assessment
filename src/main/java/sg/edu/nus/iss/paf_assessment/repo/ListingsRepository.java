package sg.edu.nus.iss.paf_assessment.repo;

import java.util.List;


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
    public List<Document> searchListings(MultiValueMap<String, String> searchParams) {

        Criteria criteria1 = new Criteria().andOperator(Criteria.where("address.country").regex(searchParams.getFirst("country"), "i"),
                                            Criteria.where("accomodates").is(searchParams.getFirst("pax")),
                                            Criteria.where("price").gte(searchParams.getFirst("min"))
                                            .andOperator(Criteria.where("price").lte("max")));


        Query query = Query.query(criteria1).with(Sort.by(Sort.Direction.DESC, "price"));
        query.fields()
            .exclude("_id")
            .include("name", "price", "images.price_url");

        List<Document> results = template.find(query, Document.class, "listings");

        return results;
    }
}
