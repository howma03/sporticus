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
		parseMetaData();
	}

	@Override
	public IEvent setMetaData(String data){
		// TODO: validate the meta data score:%d:%d
		super.setMetaData(data);
		return this;
	}

	private void generateMetaData(){
		this.setMetaDataType("text");
		this.setMetaData(String.format("score:%d:%d", this.getScoreChallenger(), this.getScoreChallenged()));
	}

	private void parseMetaData() {
		// Extract scores from the event meta data
		try {
			if (getMetaDataType().equalsIgnoreCase("text")) {
				String metaData = getMetaData();
				if (metaData.length() > 0) {
					String[] elements = metaData.split(":");
					this.setScoreChallenger(Integer.valueOf(elements[1]));
					this.setScoreChallenged(Integer.valueOf(elements[2]));
				}
			}
		} catch (Exception ex) {
			System.err.println("Error when parsing meta data");
			ex.printStackTrace();
		}
	}

	public Long getChallengerId() {
		return challengerId;
	}

	public DtoEventLadder setChallengerId(Long challengerId) {
		this.challengerId = challengerId;
		return this;
	}

	public Long getChallengedId() {
		return challengedId;
	}

	public DtoEventLadder setChallengedId(Long challengedId) {
		this.challengedId = challengedId;
		return this;
	}

	public int getScoreChallenger() {
		return scoreChallenger;
	}

	public DtoEventLadder setScoreChallenger(int scoreChallenger) {
		this.scoreChallenger = scoreChallenger;
		this.generateMetaData();
		return this;
	}

	public int getScoreChallenged() {
		return scoreChallenged;
	}

	public DtoEventLadder setScoreChallenged(int scoreChallenged) {
		this.scoreChallenged = scoreChallenged;
		this.generateMetaData();
		return this;
	}

	@Override
	public String toString() {
		return String.format("DtoEventLadder - Date {%s] Challenger [%s] Challenged [%s]",
				this.getDateTimeString(), challengerId, challengedId);
	}

}
