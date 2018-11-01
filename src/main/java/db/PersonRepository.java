package db;

import model.Person;

import java.util.List;

/**
 * Repository for {@link Person} class.
 *
 * Created by Lakhno Anton
 * at 16:53 27.10.2018.
 *
 * @author Lakhno Anton
 */
public interface PersonRepository {

	void insert(Person person);
	void insertMany(List<Person> persons);
	List<Person> getAll();
	List<Person> getByName(String name);
	Long count();
	void clearAll();

}
