package model;


import lombok.Data;
import lombok.ToString;

/**
 * Simple person model.
 *
 * Created by Lakhno Anton
 * at 15:57 27.10.2018.
 *
 * @author Lakhno Anton
 */
@ToString
@Data
public class Person {

	private String name;
	private String surname;
	private Integer age;
	private String sex;

	public Person() {}

	public Person(String name, String surname, Integer age, String sex) {
		this.name = name;
		this.surname = surname;
		this.age = age;
		this.sex = sex;
	}

}
