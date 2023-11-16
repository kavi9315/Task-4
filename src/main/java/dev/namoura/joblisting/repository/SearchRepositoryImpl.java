package dev.namoura.joblisting.repository;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dev.namoura.joblisting.model.Post;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



@Component
public class SearchRepositoryImpl implements SearchRespository{
    @Autowired
    MongoClient client;

    // Converts the result(Doc) into a Java Post
    @Autowired
    MongoConverter converter;

    @Override
    public List<Post> findByText(String text){
         final List<Post> posts = new ArrayList<>();

        //This code was extracted from mongoDBAtlas(Collections/Aggregation)
        MongoDatabase database = client.getDatabase("rsn00b");
        MongoCollection<Document> collection = database.getCollection("JobPost");
        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search",
                new Document("text",
                new Document("query", text)
                .append("path", Arrays.asList("techs", "desc", "profile")))),
                new Document("$sort",
                new Document("field1", 1L))));

        result.forEach(doc -> posts.add(converter.read(Post.class, doc)));

        return posts;
    }
}
