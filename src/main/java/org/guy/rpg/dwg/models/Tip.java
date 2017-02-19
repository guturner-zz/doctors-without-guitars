package org.guy.rpg.dwg.models;

/**
 * Used for mapping 'Tips' CSV rows to POJOs.
 * 
 * @author Guy
 */
public class Tip {

	private String key;
	private String traitName;
	private String traitDescription;

	// Necessary for the CSVToBean conversion:
	public Tip() {

	}

	public Tip(String className, String traitName, String traitDescription) {
		this.key = className;
		this.traitName = traitName;
		this.traitDescription = traitDescription;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTraitName() {
		return traitName;
	}

	public void setTraitName(String traitName) {
		this.traitName = traitName;
	}

	public String getTraitDescription() {
		return traitDescription;
	}

	public void setTraitDescription(String traitDescription) {
		this.traitDescription = traitDescription;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((traitDescription == null) ? 0 : traitDescription.hashCode());
		result = prime * result + ((traitName == null) ? 0 : traitName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tip other = (Tip) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (traitDescription == null) {
			if (other.traitDescription != null)
				return false;
		} else if (!traitDescription.equals(other.traitDescription))
			return false;
		if (traitName == null) {
			if (other.traitName != null)
				return false;
		} else if (!traitName.equals(other.traitName))
			return false;
		return true;
	}

}