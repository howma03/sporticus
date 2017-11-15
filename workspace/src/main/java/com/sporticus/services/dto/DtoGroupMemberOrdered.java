package com.sporticus.services.dto;

import com.sporticus.domain.interfaces.IGroupMember;

public class DtoGroupMemberOrdered extends DtoGroupMember {

	private int position = 0;

	public DtoGroupMemberOrdered() {

	}
	public DtoGroupMemberOrdered(final IGroupMember from) {
		IGroupMember.COPY (from, this);
	}

	public DtoGroupMemberOrdered setPosition (int position) {
		this.position = position;
		return this;
	}
	public int getPosition () {
		return position;
	}

}
