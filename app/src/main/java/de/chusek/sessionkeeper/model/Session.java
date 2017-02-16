package de.chusek.sessionkeeper.model;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

import de.chusek.sessionkeeper.logic.db.tables.TblSession;

/**
 * Created by carsten on 13.02.2017.
 *
 * A session has no name,
 * it is identfied by the game and the strDate played
 *
 * it has a list of  * players
 * you can make *strNotes* about the session. this could be anything, so a string
 * how long you play is captured in fLength. it is a small floating point value, no dbl needed
 *
 */

public class Session extends ABaseModel {

	private final float DEFAULT_TIME = 0f;

	//fields here
	private Game        game;
	private String      strDate;
	private List<Score> players;
	private String      strNotes;
	private float       fLength; // don't expect a large values lol

	//region constructors

	public Session() {
		// no need to fill game and players with null-values
		super();
		players = new ArrayList<>();
		strDate = DEFAULT_STR;
		strNotes = DEFAULT_STR;
		fLength = DEFAULT_TIME;
	}

	public Session(int id, String created, String updated,
				   Game game, String strDate, List<Score> players, String strNotes, float fLength) {
		this.id = id;
		this.strCreatedDate = created;
		this.strUpdatedDate = updated;
		this.game = game;
		this.strDate = strDate;
		this.players = players;
		this.strNotes = strNotes;
		this.fLength = fLength;
	}

	//endregion

	//region getter/setter

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public String getStrDate() {
		return strDate;
	}

	public void setStrDate(String strDate) {
		this.strDate = strDate;
	}

	public List<Score> getPlayers() {
		return players;
	}

	public void setPlayers(List<Score> players) {
		this.players = players;
	}

	public String getStrNotes() {
		return strNotes;
	}

	public void setStrNotes(String strNotes) {
		this.strNotes = strNotes;
	}

	public float getfLength() {
		return fLength;
	}

	public void setfLength(float fLength) {
		this.fLength = fLength;
	}


	//endregion

	public void addPlayerViaScore(Score sc) {
		players.add(sc);
	}

	//region toString / toCVal / fromCVal

	@Override
	public String toString() {
		return "Session{" +
				super.toString() +
				"game=" + game +
				", strDate='" + strDate + '\'' +
				", players=" + players +
				", strNotes='" + strNotes + '\'' +
				", fLength=" + fLength +
				'}';
	}

	public ContentValues toCVal() {
		ContentValues cv = new ContentValues();
		//ID IS NOT PART OF CONTENT VALUES DONT FUCK AROUND WITH THAT
		cv.put(TblSession.COL_ID, id);
		cv.put(TblSession.COL_CREATED, strCreatedDate);
		cv.put(TblSession.COL_UPDATED, strUpdatedDate);
		cv.put(TblSession.COL_GAMEID, game.getId());
		cv.put(TblSession.COL_DATE, strDate);
		cv.put(TblSession.COL_NOTES, strNotes);
		cv.put(TblSession.COL_LENGTH, fLength);
		return cv;
	}

	static public Session fromCVal (ContentValues cv) {
		//cv.getAsInteger(TBlPlayer.KEY_ID)
		cv.getAsFloat("");
		//game = Game.getForId(cv.getAsInteger(TblSession.KEY_GAMEID));
		return new Session (0,
				"nie",
				"never",
				new Game(),
				"",
				new ArrayList<Score>(),
				"",
				0.1f);
	}

	//endregion


}
