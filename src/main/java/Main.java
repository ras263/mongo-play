import com.mongodb.MongoClientSettings;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import db.PersonRepositoryImpl;
import model.Person;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.Arrays;
import java.util.List;

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

	/**
	 * Contains some drafts.
	 * @param args Default program arguments.
	 */
	public static void main(String[] args) {
		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		MongoClientSettings settings = MongoClientSettings.builder()
				.codecRegistry(pojoCodecRegistry)
				.build();
		MongoClient mongoClient = MongoClients.create(settings);
		MongoDatabase test = mongoClient.getDatabase("test");
		test.getCollection("Person").drop();

		Person vasia = new Person("Vasiliy", "Pupkin", 26, "M");
		vasia.setAge(26); // just testing the 'providedCompile' configuration
		Person vanya = new Person("Ivan", "Ivanov", 25, "M");
		Person petya = new Person("Petr", "Semenov", 27, "M");
		Person fedya = new Person("Fedor", "Smolov", 29, "M");

		PersonRepositoryImpl personRepository = new PersonRepositoryImpl(test);
		// clear all.
		personRepository.clearAll();
		// checking by 'get all'.
		List<Person> all = personRepository.getAll();
		System.out.println("Empty collection: " + all);
		// insert all
		personRepository.insertMany(Arrays.asList(vasia, vanya, petya, fedya));
		// check again
		all = personRepository.getAll();
		System.out.println("Collection after inserting many: " + all);
		// Get by name
		List<Person> nikitkas = personRepository.getByName("Nikitka");
		System.out.println("Found with name 'Nikitka': " + nikitkas);
		// get count
		Long count = personRepository.count();
		System.out.println("Count of entities: " + count);
		// Try to check uniqueness
		try {
			personRepository.insert(vasia);
		} catch (MongoWriteException e) {
			System.out.println("Error occurred while trying to insert a person with duplicated unique key. Cause: "
					+ e.getMessage());
		}
		count = personRepository.count();
		System.out.println("Count after repeating insert operation with same entity: " + count);

		PersonRepositoryImpl personRepository1 = new PersonRepositoryImpl(test);
		System.out.println(personRepository1.getAll());

		System.out.println("All count again: " + personRepository.count());
	}

}
