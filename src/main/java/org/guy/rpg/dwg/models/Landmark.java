package org.guy.rpg.dwg.models;

public class Landmark {

	private String name;
	private String description;
	private String longitude;
	private String latitude;

	// Necessary for the CSVToBean conversion:
	public Landmark() {

	}

	public Landmark(String name, String description, String longitude, String latitude) {
		this.name = name;
		this.description = description;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		Landmark other = (Landmark) obj;
		
		if (getDescription().equals(other.getDescription()) &&
		    getLatitude().equals(other.getLatitude()) &&
			getLongitude().equals(other.getLongitude()) &&
			getName().equals(other.getName())) {
			return true;
		}
		
		return false;
	}

}