package db;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.InsertManyOptions;
import com.mongodb.client.model.InsertOneOptions;
import model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by Lakhno Anton
 * at 18:39 27.10.2018.
 *
 * @author Lakhno Anton
 */
public class PersonRepositoryImpl implements PersonRepository {

	private final MongoDatabase database;
	private MongoCollection<Person> collection;

	public PersonRepositoryImpl(MongoDatabase database) {
		this.database = database;
		initCollection();
	}

	private void initCollection() {
		this.collection = database.getCollection("Person", Person.class);
		collection.createIndex(
				Indexes.ascending("name", "surname"),
				new IndexOptions().unique(true)
		);
	}

	@Override
	public void insert(Person person) {
		collection.insertOne(person);
	}

	@Override
	public void insertMany(List<Person> persons) {
		collection.insertMany(persons);
	}

	@Override
	public List<Person> getAll() {
		List<Person> all = new ArrayList<>();
		collection.find().forEach(toList(all));
		return all;
	}

	@Override
	public List<Person> getByName(String name) {
		List<Person> result = new ArrayList<>();
		collection.find(eq("name", name)).forEach(toList(result));
		return result;
	}

	@Override
	public Long count() {
		return collection.countDocuments();
	}

	@Override
	public void clearAll() {
		collection.drop(); //TODO (AntonLakhno) Use something else instead .drop().
		initCollection();
	}

	private Consumer<Person> toList(List<Person> list) {
		return list::add;
	}

}
