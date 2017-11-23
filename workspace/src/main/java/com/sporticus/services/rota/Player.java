package com.sporticus.services.rota;

import com.sporticus.domain.interfaces.IUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Player implements Comparable {

	private static Random random = new Random();
	private final IUser user;
	private final List<Date> unavailable = new ArrayList<>();
	private final Slots slots = new Slots();
	private Float probability = nextFloat(0, 10);

	public Player(IUser user) {
		this.user = user;
	}

	private static float nextFloat(float min, float max) {
		return min + random.nextFloat() * (max - min);
	}

	public void addSlot(Slot slot) {
		this.slots.add(slot);
	}

	@Override
	public int compareTo(Object o) {
		return ((Player) o).probability.compareTo(this.probability);
	}

	public Float getProbability() {
		return probability;
	}

	public void setProbability(float probability) {
		this.probability = probability;
	}

	public Slots getSlots() {
		Slots result = new Slots();
		result.addAll(slots);
		return result;
	}

	public IUser getUser() {
		return user;
	}

	public void halveProbability() {
		probability = probability / 2f;
	}

	public boolean isAvailable(Date date) {
		return !unavailable.contains(date);
	}

	@Override
	public String toString() {
		return String.format("Player [%s] Probability [%f] Slots [%d]", user, probability, slots.size());
	}

}