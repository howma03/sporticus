package com.sporticus.services.rota;

import java.util.ArrayList;
import java.util.List;

public class Players extends ArrayList<Player> {
	Players() {
	}

	Players(List<Player> list) {
		super.addAll(list);
	}
}
