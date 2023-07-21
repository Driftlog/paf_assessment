package sg.edu.nus.iss.paf_assessment.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AddFieldsOperation;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import sg.edu.nus.iss.paf_assessment.model.FormFields;

@Repository
public class ListingsRepository {
    
    @Autowired
    private MongoTemplate template;


//     Query: db.listings.aggregate([
//         {$match: { "address.street": {$regex: 'Australia', $options: 'i'}, 'accommodates': 2, 
//             price : {$gte: 0, $lte: 1000}}},
//             {
//           $addFields: {address: "$address.street", image: "$images.picture_url"}  
//         },
//         {
//             $project: {address: 1, price: 1, image:1 }
//         },
//         {
//             $sort: {price: 1}
//         }           
// ]);
    public List<Document> searchListings(FormFields searchParams) {

        Criteria criteria1 = new Criteria().andOperator(Criteria.where("address.street").regex(searchParams.getCountry(), "i")
                                .andOperator(Criteria.where("accommodates").is(searchParams.getPax())),
                                Criteria.where("price").gte(searchParams.getMin()).lte(searchParams.getMax()));

       MatchOperation match = Aggregation.match(criteria1);
        
        AddFieldsOperation addFields = Aggregation.addFields()
                                        .addFieldWithValue("address", "$address.street")
                                        .addFieldWithValue("image", "$images.picture_url")
                                        .addFieldWithValue("id", "$_id")
                                        .build();
        
        ProjectionOperation projection = Aggregation.project("id", "address", "price", "image");
       
        SortOperation sort = Aggregation.sort(Sort.by(Direction.DESC, "price"));

        Aggregation pipeline = Aggregation.newAggregation(match, addFields, projection, sort);
        
        AggregationResults<Document> results = template.aggregate(pipeline, "listings", Document.class);


       return results.getMappedResults();
        
    }

    //query db.listings.distinct("address.country");
    public List<String> countriesList() {

       List<String> countries = template.findDistinct(new Query(), "address.country", "listings", String.class);
       return countries;
    }

    //Query db.listings.aggregate([{$match : {"_id": listingId}},
                                //  {
//           $addFields: {image: "$images.picture_url", id: "$_id"}  
//         },
//         {
//             $project: {id, description, address: 1, price: 1, image:1, amenities:1 }
//         }])
    public Optional<Document> getListingById(String listingId) {
       
        MatchOperation match = Aggregation.match(Criteria.where("_id").is(listingId));
        AddFieldsOperation addFields = Aggregation.addFields()
                                        .addFieldWithValue("image", "$images.picture_url")
                                        .addFieldWithValue("id", "$_id")
                                        .build();

        ProjectionOperation projection = Aggregation.project("id", "description", "address", "image", "price", "amenities");

        Aggregation pipeline = Aggregation.newAggregation(match, addFields, projection);

        AggregationResults<Document> results = template.aggregate(pipeline, "listings", Document.class);

        System.out.println(results.getMappedResults().isEmpty());

        if (results.getMappedResults().isEmpty()) {
            return Optional.empty();
        }
        
        return Optional.of(results.getMappedResults().get(0));
    }
}
