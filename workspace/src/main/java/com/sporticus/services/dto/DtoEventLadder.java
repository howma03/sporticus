package com.sporticus.services.dto;

import com.sporticus.domain.interfaces.IEvent;

public class DtoEventLadder extends DtoEvent {

	private Long challengerId;
	private Long challengedId;
	private int scoreChallenger = 0;
	private int scoreChallenged = 0;

	public DtoEventLadder(){
	}

	public DtoEventLadder(IEvent e){
		super(e);

		// Extract scores from the event meta data
		if(e.getMetaDataType().equalsIgnoreCase("text")){
			String metaData = e.getMetaData();
			if(metaData.length()>0) {
				String[] elements = metaData.split(":");
				this.setScoreChallenger(Integer.valueOf(elements[1]));
				this.setScoreChallenged(Integer.valueOf(elements[2]));
			}
		}
	}

	@Override
	public IEvent setMetaData(String data){
		// TODO: validate the meta data score:%d:%d
		super.setMetaData(data);
		return this;
	}

	public Long getChallengerId() {
		return challengerId;
	}

	public void setChallengerId(Long challengerId) {
		this.challengerId = challengerId;
	}

	public Long getChallengedId() {
		return challengedId;
	}

	public void setChallengedId(Long challengedId) {
		this.challengedId = challengedId;
	}

	public int getScoreChallenger() {
		return scoreChallenger;
	}

	public void setScoreChallenger(int scoreChallenger) {
		this.scoreChallenger = scoreChallenger;
	}

	public int getScoreChallenged() {
		return scoreChallenged;
	}

	public void setScoreChallenged(int scoreChallenged) {
		this.scoreChallenged = scoreChallenged;
	}

	@Override
	public String toString() {
		return String.format("DtoEventLadder - Date {%s] Challenger [%s] Challenged [%s]",
				this.getDateTimeString(), challengerId, challengedId);
	}

}
