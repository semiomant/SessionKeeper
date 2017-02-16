package de.chusek.sessionkeeper.model;

import android.content.ContentValues;

import de.chusek.sessionkeeper.logic.db.tables.TblPlayer;

/**
 * Created by carsten on 13.02.2017.
 *
 * here is the player
 * scores are a an association between a player and a session, so they have no place here
 * the session contains the game played
 *
 * To be nice guys we only record a needed minimum of Data about the player:
 *  - Name
 * (an extra last strName is not needed in the private context of board games no need for formality)
 * - Age
 * (kid or game vetrean is interesting)
 * - strPhoneNr
 * (for the convenience of calling from the SessionAcivity)
 * - iNoOfGames
 * (this is a derrived value, but we microoptimize that compliced SQL away into this value
 *
 */

public class Player extends ABaseModel {

	private String strName;
	private int    iAge;
	private String strPhoneNr;
	private int    iNoOfGames;

	//region constructors


	public Player() {
		super();
		strName = DEFAULT_STR;
		iAge = DEFAULT_INT;
		strPhoneNr = DEFAULT_STR;
		iNoOfGames = DEFAULT_INT;
	}

	public Player(int id, String created, String updated,
				  String strName, int iAge, String strPhoneNr, int noOfGAmes) {
		this.id = id;
		this.strCreatedDate = created;
		this.strUpdatedDate = updated;
		this.strName = strName;
		this.iAge = iAge;
		this.strPhoneNr = strPhoneNr;
		this.iNoOfGames = noOfGAmes;
	}


	//endregion

	//region getter / setter

	public String getStrName() {
		return strName;
	}

	public void setStrName(String strName) {
		this.strName = strName;
	}

	public int getiAge() {
		return iAge;
	}

	public void setiAge(int iAge) {
		this.iAge = iAge;
	}

	public String getStrPhoneNr() {
		return strPhoneNr;
	}

	public void setStrPhoneNr(String strPhoneNr) {
		this.strPhoneNr = strPhoneNr;
	}

	public int getiNoOfGames() {
		return iNoOfGames;
	}

	public void setiNoOfGames(int iNoOfGames) {
		this.iNoOfGames = iNoOfGames;
	}


	//endregion

	//region toString / toCVal / fromCVal

	@Override
	public String toString() {
		return "Player{" +
				super.toString()+
				"strName='" + strName + '\'' +
				", iAge=" + iAge +
				", strPhoneNr='" + strPhoneNr + '\'' +
				", iNoOfGames=" + iNoOfGames +
				'}';
	}

	public ContentValues toCVal() {
		ContentValues cv = new ContentValues();
		//ID IS NOT PART OF CONTENT VALUES DONT FUCK AROUND WITH THAT
		cv.put(TblPlayer.COL_ID, id);
		cv.put(TblPlayer.COL_CREATED, strCreatedDate);
		cv.put(TblPlayer.COL_UPDATED, strUpdatedDate);
		cv.put(TblPlayer.COL_NAME, strName);
		cv.put(TblPlayer.COL_AGE, iAge);
		cv.put(TblPlayer.COL_PHONENR, strPhoneNr);
		cv.put(TblPlayer.COL_NOOFGAMES, iNoOfGames);
		return cv;
	}

	static public Player fromCVal (ContentValues cv) {
		//pattern : cv.getAsInteger(TBlPlayer.KEY_ID)
		return new Player (cv.getAsInteger(TblPlayer.COL_ID),
				cv.getAsString(TblPlayer.COL_CREATED),
				cv.getAsString(TblPlayer.COL_UPDATED),
				cv.getAsString(TblPlayer.COL_NAME),
				cv.getAsInteger(TblPlayer.COL_AGE),
				cv.getAsString(TblPlayer.COL_PHONENR),
				cv.getAsInteger(TblPlayer.COL_NOOFGAMES));
	}


	//endregion
}
