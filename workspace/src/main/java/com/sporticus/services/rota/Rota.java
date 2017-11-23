package com.sporticus.services.rota;

import java.util.Date;
import java.util.HashMap;

public class Rota extends HashMap<Date, Slots> {

	public void dump() {
		for (Date date : this.keySet()) {
			Slots slots = this.get(date);
			System.out.println(String.format("Date [%s] Slots [%d]", date, slots.size()));
			slots.stream().forEach(l -> {
				System.out.println(String.format("\tSlot [%d] %s", slots.indexOf(l), l));
				l.getPlayers().forEach(p -> {
					System.out.println(String.format("\t\tPlayer [%s", p));
				});
			});
		}
	}
}
