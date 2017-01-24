package org.guy.rpg.dwg.models.apiai;

public class AIResponseObject {
	private String speech;
	private String displayText;
	private String source = "doctors-without-guitars";

	public String getSpeech() {
		return speech;
	}

	public void setSpeech(String speech) {
		this.speech = speech;
	}

	public String getDisplayText() {
		return displayText;
	}

	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}

	public String getSource() {
		return source;
	}
}