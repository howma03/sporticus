package com.sporticus.services.dto;

import com.sporticus.domain.interfaces.IEvent;
import com.sporticus.domain.interfaces.IUser;

import java.util.ArrayList;
import java.util.List;

public class DtoEventRota extends DtoEvent {

	private final List<IUser> players = new ArrayList<>();

	public DtoEventRota(){
	}

	public DtoEventRota(IEvent e){
		super(e);
	}

	public List<IUser> getPlayers() {
		return players;
	}

	public void setPlayers(List<IUser> players) {
		this.players.clear();
		this.players.addAll( players);
	}

	@Override
	public String toString() {
		return String.format("DtoEventLadder - Date {%s] Players [%d]",
				this.getDateTimeString(), players.size());
	}

}
