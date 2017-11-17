package com.sporticus.types;

public enum GroupType {

	LADDER("Ladder");

	private String displayName = "";
	GroupType(String displayName){
		this.displayName = displayName;
	}

	public String getDisplayName(){
		return displayName;
	}
}
