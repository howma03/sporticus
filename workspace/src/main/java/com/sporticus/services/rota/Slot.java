package com.sporticus.services.rota;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Slot {

	private Date date;
	private List<Player> availablePlayers = new ArrayList<>();
	private List<Player> selectedPlayers = new ArrayList<>();

	public Slot(Date date) {
		this.date = date;
	}

	public void addAvailablePlayer(Player p) {
		this.availablePlayers.add(p);
	}

	public void addPlayer(Player p) {
		selectedPlayers.add(p);
	}

	public List<Player> getAvailablePlayers() {
		List<Player> list = new ArrayList<>();
		list.addAll((availablePlayers));
		return list;
	}

	public Date getDate() {
		return date;
	}

	public List<Player> getPlayers() {
		List<Player> list = new ArrayList<>();
		list.addAll(selectedPlayers);
		return list;
	}

	public boolean isFull() {
		return selectedPlayers.size() == 4;
	}

	@Override
	public String toString() {
		return String.format("Date [%s] Available Players [%d] Selected Players [%d]",
				date, availablePlayers.size(), selectedPlayers.size());
	}

}