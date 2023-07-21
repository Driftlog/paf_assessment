package sg.edu.nus.iss.paf_assessment.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

public class ListingUtil {

    public static List<Document> toShortDocList(List<Document> documents) {
           List<Document> documentList = documents.stream()
                                .map(doc -> toShortDoc(doc))
                                .collect(Collectors.toList());

                return documentList;
            
            
            
    }
    
    public static Document toShortDoc(Document document) {
        Document addressDoc = document.get("address", Document.class);
        Document pictureDoc = document.get("images", Document.class);
        Document shortJson = new Document()
                                .append("id", document.get("_id", int.class))
                                .append("address", addressDoc.get("street", String.class))
                                .append("price", document.get("price", Float.class))
                                .append("image", pictureDoc.get("picture_url", String.class));
        
        return shortJson;
    }

    // public static JsonObject toLongJson(Document document) {

    // }
}
