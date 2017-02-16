package de.chusek.sessionkeeper.logic.db.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;

import java.util.ArrayList;
import java.util.List;

import de.chusek.sessionkeeper.model.Game;
import de.chusek.sessionkeeper.model.Player;

/**
 * Created by carsten on 13.02.2017.
 *
 * hold COLUMN (COL) names for Player @{@link de.chusek.sessionkeeper.model.Player}
 *
 */

public class TblPlayer extends ABaseTable {

	public static final String NAME = "players";

	// for the fields follow link in above in Doc comment
	public static final String  COL_NAME = "name";
	public static final String  COL_AGE = "age";
	public static final String  COL_PHONENR = "phoneNr";
	public static final String  COL_NOOFGAMES = "noOfGames";

	//commands for DB
	public static final String  CMD_CREATE_TBL = CREATE_TBL + NAME + OPEN_BRACKET +
			COL_ID + PRIMARY_KEY_DEFINITION + //Autoincrement MORGEN
			COL_NAME + TYPE_TEXT + COMMA +
			COL_AGE + TYPE_INTEGER + COMMA +
			COL_PHONENR + TYPE_TEXT + COMMA +
			COL_NOOFGAMES + TYPE_INTEGER + COMMA +
			COL_CREATED + TYPE_TEXT+ COMMA +
			COL_UPDATED + TYPE_TEXT +
			");";

	public static final String  CMD_DROP_TBL = DROP_TBL + NAME + SEMICOLON;

	public static final String  UPDATE_WHERE_CLAUSE = COL_ID + EQUALS_OPERATOR_INC_PLACE_HOLDER;

	public static final String  DELETE_WHERE_ID_TBL_USER = DELETE_FROM_TBL
															+ NAME
															+ WHERE_CONDITION + COL_ID
															+ EQUALS_OPERATOR_INC_PLACE_HOLDER;


	//region delegates

	public static List<Player> listFromCursor(Cursor cur) {
		// we convert by an intermediate step
		//the Database Helper really helps us out
		// no need to get indices and we can even convert in loop with a nice delegation to model
		List<Player>    gameList = new ArrayList<>();
		Player          player;
		ContentValues cv;
		if(cur.moveToFirst()) {
			do {
				cv = new ContentValues();
				DatabaseUtils.cursorRowToContentValues(cur, cv);
				player = Player.fromCVal(cv);
				gameList.add(player);
			} while(cur.moveToNext());
		}
		return gameList;
	}

	//endregion

}
