package com.sporticus.services.rota;

import com.sporticus.domain.entities.User;

import java.util.Calendar;
import java.util.Date;

public class Test {
	public static void main(String[] args) {
		ServiceRotaImplRepository service = new ServiceRotaImplRepository();

		Players players = new Players();
		for (int count = 0; count < 20; count++) {
			players.add(new Player(new User()
					.setEmail(String.format("me%d@me.com", count))
					.setFirstName(String.format("FirstName%d", count))));
		}

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 20);

		Date start = new Date();
		Date end = cal.getTime();

		Rota rota = service.constructRota(start,
				end,
				players,
				dateTime -> {
					Slots slots = new Slots();
					slots.add(new Slot(dateTime));
					Calendar my = Calendar.getInstance();
					my.setTime(dateTime);
					if (my.get(Calendar.DAY_OF_WEEK) == 3) {
						slots.add(new Slot(dateTime));
					}
					return slots;
				},
				dateTime -> true);

		service.calcRota(rota);

		System.out.println("------------------------");
		rota.dump();
		System.out.println("------------------------");

		players.forEach(p -> {
			System.out.println(p);
			p.getSlots().forEach(s -> {
				System.out.println(String.format("\t %s", s));
			});
		});
		System.out.println("------------------------");
	}
}
