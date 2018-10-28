import com.mongodb.MongoClientSettings;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import db.PersonRepository;
import db.PersonRepositoryImpl;
import model.Person;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * Just some testing.
 *
 * Created by Lakhno Anton
 * at 23:48 28.10.2018.
 *
 * @author Lakhno Anton
 */
public class MainTest {

	PersonRepository rep;

	@Before
	public void setUp() throws Exception {
		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		MongoClientSettings settings = MongoClientSettings.builder()
				.codecRegistry(pojoCodecRegistry)
				.build();
		MongoClient mongoClient = MongoClients.create(settings);
		MongoDatabase test = mongoClient.getDatabase("test");
		test.getCollection("Person").drop();
		rep = new PersonRepositoryImpl(test);
	}

	@Test
	public void testInteraction() throws Exception {
		// given
		Person vasia = new Person("Vasiliy", "Pupkin", 26, "M");
		Person vanya = new Person("Ivan", "Ivanov", 25, "M");
		Person petya = new Person("Petr", "Semenov", 27, "M");
		Person fedya = new Person("Fedor", "Smolov", 29, "M");
		// clear all.
		rep.clearAll();

		// when
		// checking by 'get all'.
		List<Person> all = rep.getAll();
		System.out.println("Empty collection: " + all);
		// insert all
		rep.insertMany(Arrays.asList(vasia, vanya, petya, fedya));
		// check again
		all = rep.getAll();
		System.out.println("Collection after inserting many: " + all);
		// Get by name
		List<Person> vasiliys = rep.getByName("Vasiliy");
		System.out.println("Found with name 'Vasiliy': " + vasiliys);
		// get count
		Long count = rep.count();
		System.out.println("Count of entities: " + count);
		// Try to check uniqueness
		try {
			rep.insert(vasia);
		} catch (MongoWriteException e) {
			System.out.println("Error occurred while trying to insert a person with duplicated unique key. Cause: "
					+ e.getMessage());
		}
		count = rep.count();
		System.out.println("Count after repeating insert operation with same entity: " + count);
		// then
		Assert.assertEquals(1, vasiliys.size());
		Assert.assertEquals(4L, count.longValue());
	}

}
