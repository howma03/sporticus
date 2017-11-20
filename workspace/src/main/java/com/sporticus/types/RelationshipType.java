package com.sporticus.types;

public enum RelationshipType {

	CHALLENGE("Challenge");

	private String displayName = "";
	RelationshipType(String displayName){
		this.displayName = displayName;
	}

	public String getDisplayName(){
		return displayName;
	}
}
