import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Person;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.function.Consumer;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * Main entry point.
 *
 * Created by Lakhno Anton
 * at 15:48 27.10.2018.
 *
 * @author Lakhno Anton
 */
public class Main {

	public static void main(String[] args) {
		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		MongoClientSettings settings = MongoClientSettings.builder()
				.codecRegistry(pojoCodecRegistry)
				.build();
		MongoClient mongoClient = MongoClients.create(settings);
		MongoDatabase test = mongoClient.getDatabase("test");
				//.withCodecRegistry(pojoCodecRegistry);
		MongoCollection<Person> person = test.getCollection("Person", Person.class);
				//.withCodecRegistry(pojoCodecRegistry);
		person.insertOne(new Person("Anton", "Lakhno", 30, "Male"));


		person.find().forEach((Consumer) System.out::println);
	}

}
