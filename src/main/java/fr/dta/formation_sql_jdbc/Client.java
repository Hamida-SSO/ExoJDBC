package fr.dta.formation_sql_jdbc;

public class Client {

	private String lastName;
	private String firstName;
	private Gender gender;
	private Long id;

	public Client(String lastname, String firstName, Gender gender) {
		this.lastName = lastname;
		this.firstName = firstName;
		this.gender = gender;
		this.id = null;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

}
