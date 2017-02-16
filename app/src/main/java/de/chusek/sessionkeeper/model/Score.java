package de.chusek.sessionkeeper.model;

import android.content.ContentValues;

import de.chusek.sessionkeeper.logic.db.tables.TblScore;

/**
 * Created by carsten on 13.02.2017.
 *
 * this cute lil helper will mediate between iSessionId and player
 *
 * we can probably also delegate here sometimes for less code clutter elsewhere
 *
 * Since we need the association in the database anyway, making also explicit in the Java side will
 * help, since all communication with  the database will be [on Foot / by Hand / without ORM help]
 * anyways
 */

public class Score extends ABaseModel {

	private int    iSessionId;
	private Player player;
	private int    iScore;

	//region constructors

	public Score() {
		super();
		//no need to initialize iSessionId and player to null
		iScore = DEFAULT_INT;
	}

	public Score(int id, String created, String updated, int iSessionId, Player player, int iScore) {
		this.id = id;
		this.strCreatedDate = created;
		this.strUpdatedDate = updated;
		this.iSessionId = iSessionId;
		this.player = player;
		this.iScore = iScore;
	}

	public Score(int sId, Player p, int score) {
		this.iSessionId = sId;
		this.player = p;
		this.iScore = score;
	}

	//endregion

	//region getter/setter

	public int getiSessionId() {
		return iSessionId;
	}

	public void setiSessionId(int iSessionId) {
		this.iSessionId = iSessionId;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getiScore() {
		return iScore;
	}

	public void setiScore(int iScore) {
		this.iScore = iScore;
	}


	//endregion

	//region toString et al

	@Override
	public String toString() {
		return "Score{" +
				"iSessionId=" + iSessionId +
				", player=" + player +
				", iScore=" + iScore +
				'}';
	}

	public ContentValues toCVal() {
		ContentValues cv = new ContentValues();
		//ID IS NOT PART OF CONTENT VALUES DONT FUCK AROUND WITH THAT
		cv.put(TblScore.COL_ID, id);
		cv.put(TblScore.COL_CREATED, strCreatedDate);
		cv.put(TblScore.COL_UPDATED, strUpdatedDate);
		cv.put(TblScore.COL_SESSIONID, iSessionId);
		cv.put(TblScore.COL_PLAYERID, player.getId());
		cv.put(TblScore.COL_SCORE, iScore);
		return cv;
	}


	//endregion
}
