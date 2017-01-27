package org.guy.rpg.dwg.models;

public class KeyValuePair {

	private String key;
	private String value;

	public KeyValuePair() {

	}

	public KeyValuePair(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj && obj != null) {
			return true;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		KeyValuePair other = (KeyValuePair) obj;
		if (this.getKey().equals(other.getKey()) && 
			this.getValue().equals(other.getValue())) {
			return true;
		}
		
		return false;
	}

}
