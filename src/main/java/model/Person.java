package model;


/**
 * Simple person model.
 *
 * Created by Lakhno Anton
 * at 15:57 27.10.2018.
 *
 * @author Lakhno Anton
 */
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

	//region Properties accessors
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	//endregion

	@Override
	public String toString() {
		return "Person{" +
				"name='" + name + '\'' +
				", surname='" + surname + '\'' +
				", age=" + age +
				", sex='" + sex + '\'' +
				'}';
	}

}
