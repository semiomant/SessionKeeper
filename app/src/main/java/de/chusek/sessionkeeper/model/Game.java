package de.chusek.sessionkeeper.model;

import android.content.ContentValues;
import android.util.Log;

import de.chusek.sessionkeeper.logic.db.tables.TblGame;

/**
 * Created by carsten on 13.02.2017.
 *
 * The game has some value that might be of interest to boardgamers
 * strName (well, the strName of the game)
 * strPublisher (who put the game out)
 * strAuthor (modern borad game geek like to know)
 * iYear (the iYear of publication is interesting but not the specific date)

 * the number of players (min&max) should be here too
 *
 * interesting might be to include a link to the pubisher or the game on the publishers site
 * this is extended functionality
 *
 * another not very simple idea is to include expansions
 */

public class Game extends ABaseModel {

	private String strName;
	private String strPublisher;
	private String strAuthor;
	private int    iYear;

	//region constructors

	public Game() {
		super();
		strName = DEFAULT_STR;
		strPublisher = DEFAULT_STR;
		strAuthor = DEFAULT_STR;
		iYear = DEFAULT_INT;
	}

	public Game(int id, String created, String updated,
				String strName, String strPublisher, String strAuthor, int iYear) {
		this.id = id;
		this.strCreatedDate = created;
		this.strUpdatedDate = updated;
		this.strName = strName;
		this.strPublisher = strPublisher;
		this.strAuthor = strAuthor;
		this.iYear = iYear;
	}

	//endregion

	//region getter/setter

	public String getStrName() {
		return strName;
	}

	public void setStrName(String strName) {
		this.strName = strName;
	}

	public String getStrPublisher() {
		return strPublisher;
	}

	public void setStrPublisher(String strPublisher) {
		this.strPublisher = strPublisher;
	}

	public String getStrAuthor() {
		return strAuthor;
	}

	public void setStrAuthor(String strAuthor) {
		this.strAuthor = strAuthor;
	}

	public int getiYear() {
		return iYear;
	}

	public void setiYear(int iYear) {
		this.iYear = iYear;
	}


	//endregion

	//region toString / toCVal / fromCVal

	@Override
	public String toString() {
		return "Game{" +
				super.toString() +
				"strName='" + strName + '\'' +
				", strPublisher='" + strPublisher + '\'' +
				", strAuthor='" + strAuthor + '\'' +
				", iYear=" + iYear +
				'}';
	}

	public ContentValues toCVal() {
		ContentValues cv = new ContentValues();
		//ID IS NOT PART OF CONTENT VALUES DONT FUCK AROUND WITH THAT
		cv.put(TblGame.COL_ID, id);
		cv.put(TblGame.COL_CREATED, strCreatedDate);
		cv.put(TblGame.COL_UPDATED, strUpdatedDate);
		cv.put(TblGame.COL_NAME, strName);
		cv.put(TblGame.COL_PUBLISHER, strPublisher);
		cv.put(TblGame.COL_AUTHOR, strAuthor);
		cv.put(TblGame.COL_YEAR, iYear);
		return cv;
	}

	static public Game fromCVal (ContentValues cv) {
		//cv.getAsInteger(TBlPlayer.KEY_ID)
		// pattern : cv.getAsFloat("");
		return new Game (cv.getAsInteger(TblGame.COL_ID),
				cv.getAsString(TblGame.COL_CREATED),
				cv.getAsString(TblGame.COL_UPDATED),
				cv.getAsString(TblGame.COL_NAME),
				cv.getAsString(TblGame.COL_PUBLISHER),
				cv.getAsString(TblGame.COL_AUTHOR),
				cv.getAsInteger(TblGame.COL_YEAR));
	}


	//endregion

}
