package model;

public class Location {

	private static final long serialVersionUID = 1L;

	private String name;
	private String country;

	public Location() {
	}

	public Location(String name, String country) {
		this.name = name;
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
