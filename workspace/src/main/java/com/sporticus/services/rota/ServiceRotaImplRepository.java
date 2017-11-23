package com.sporticus.services.rota;

import com.sporticus.domain.interfaces.IGroup;
import com.sporticus.interfaces.IServiceEvent;
import com.sporticus.interfaces.IServiceGroup;
import com.sporticus.interfaces.IServiceRota;
import com.sporticus.services.dto.DtoEventRota;
import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceRotaImplRepository implements IServiceRota {

	private static final Logger LOGGER = LogFactory.getLogger(ServiceRotaImplRepository.class.getName());

	@Autowired
	private IServiceGroup serviceGroup;

	@Autowired
	private IServiceEvent serviceEvent;

	public ServiceRotaImplRepository() {
	}

	@Override
	public List<DtoEventRota> createSchedule(long groupId, Date start, Date end) {

		// When determining the schedule we collect:-
		// 1. the rota
		// 2. the players
		// 3. the player availabilities

		// First determine the rota - we will assume 1 every day of the week
		// MF require 1 for each day (Mon-Thu) with 2 rota of Tuesday

		// TODO: obtain players from repository
		Optional<IGroup> group = serviceGroup.readGroup(groupId);

		Players players = new Players(serviceGroup.getMembershipUsersForGroup(groupId, null)
				.stream().map(u -> new Player(u)).collect(Collectors.toList()));


		Rota rota = constructRota(start, end, players, dateTime -> new Slots(new Slot(dateTime)),
				dateTime -> true);

		calcRota(rota);

		// Convert the rota into individual Events

		List<DtoEventRota> list = new ArrayList<>();
		rota.forEach((k, v) -> {
			v.forEach(s -> {
				DtoEventRota e = new DtoEventRota();
				e.setDateTime(s.getDate());
				e.setPlayers(s.getPlayers().stream().map(p -> p.getUser()).collect(Collectors.toList()));
				list.add(e);
			});
		});
		return list;
	}

	protected Rota constructRota(Date start, Date end, Players players, ISlots fncSlots, IPlayerAvailable playerAvailable) {
		Rota rota = new Rota();

		Calendar cal = Calendar.getInstance();
		cal.setTime(start);

		while (cal.getTime().before(end)) {
			// TODO: add multiple slots for certain days
			Slots slots = new Slots();

			if (fncSlots != null) {
				slots.addAll(fncSlots.getSlots(cal.getTime()));
			} else {
				slots.add(new Slot(cal.getTime()));
			}

			slots.forEach(slot -> {
				if (!rota.containsKey(slot.getDate())) {
					rota.put(slot.getDate(), new Slots());
				}
				rota.get(slot.getDate()).add(slot);

				players.stream().forEach(p -> {
					if ((playerAvailable != null && playerAvailable.isAvailable(cal.getTime())) ||
							p.isAvailable(cal.getTime())) {
						slot.addAvailablePlayer(p);
					}
				});
			});
			cal.add(Calendar.DAY_OF_YEAR, 1);
		}

		return rota;
	}

	protected void calcRota(Rota rota) {

		// Each player will have a random probability

		rota.forEach((date, listSlots) -> {
			listSlots.forEach(slot -> {
				// loop:-
				// we select the highest probability player - after they have been assigned to the slot we halve their probability
				slot.getAvailablePlayers().stream().sorted().forEach(player -> {
					if (!slot.isFull()) {
						{
							slot.addPlayer(player);
							player.halveProbability();
							player.addSlot(slot);
						}
						System.out.println(String.format("Slot [%s] %s", slot, player));
					}
				});
			});
		});
	}

	@Override
	public List<DtoEventRota> readSchedule(long groupId) {
		return new ArrayList<>();
	}

	@Override
	public List<DtoEventRota> readSchedule(long groupId, Date begin, Date end) {
		return new ArrayList<>();
	}

	@Override
	public List<DtoEventRota> deleteSchedule(long groupId, Date begin, Date end) {
		return new ArrayList<>();
	}

	@Override
	public void deleteSchedule(long rotaEventId) {

	}

	@Override
	public void updateRotaEvent(DtoEventRota event) {

	}

	interface ISlots {
		Slots getSlots(Date dateTime);
	}

	interface IPlayerAvailable {
		boolean isAvailable(Date dateTime);
	}


}
