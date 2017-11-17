package com.sporticus.types;

public enum EventType {

	CHALLENGE("Challenge");

	private String displayName = "";
	EventType(String displayName){
		this.displayName = displayName;
	}

	public String getDisplayName(){
		return displayName;
	}
}
