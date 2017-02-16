package de.chusek.sessionkeeper.logic.db.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;

import java.util.ArrayList;
import java.util.List;

import de.chusek.sessionkeeper.model.Game;

/**
 * Created by carsten on 13.02.2017.
 *
 * hold COLUMN (COL) names for Game {@link de.chusek.sessionkeeper.model.Game}
 *
 * we don know if this will be instantiated, i can make this always abstract later
 *
 * creating / dropping tables only runs in Db.create/upgrade, so delegating that here makes no sense
 */

public class TblGame extends ABaseTable {

	public static final String NAME = "games";

	// for the fields follow link in above in Doc comment

	public static final String  COL_NAME = "name";
	public static final String  COL_PUBLISHER = "publisher";
	public static final String  COL_AUTHOR = "author";
	public static final String  COL_YEAR = "year";

	//commands & more for DB done in JCQL java constant query language =)
	public static final String  CMD_CREATE_TBL = CREATE_TBL + NAME + OPEN_BRACKET +
			COL_ID + PRIMARY_KEY_DEFINITION + //Autoincrement MORGEN
			COL_NAME + TYPE_TEXT + COMMA +
			COL_PUBLISHER + TYPE_TEXT + COMMA +
			COL_AUTHOR + TYPE_TEXT + COMMA +
			COL_YEAR + TYPE_INTEGER + COMMA +
	        COL_CREATED + TYPE_TEXT+ COMMA +
	        COL_UPDATED + TYPE_TEXT +
			CLOSE_BRACKET_SEMICOLON;
	public static final String  CMD_DROP_TBL = DROP_TBL + NAME + SEMICOLON;

	public static final String  UPDATE_WHERE_CLAUSE = COL_ID + EQUALS_OPERATOR_INC_PLACE_HOLDER;

	public static final String  DELETE_WHERE_ID_TBL_USER = DELETE_FROM_TBL
			+ NAME
			+ WHERE_CONDITION + COL_ID
			+ EQUALS_OPERATOR_INC_PLACE_HOLDER;

	//region deletages

	public static List<Game> listFromCursor(Cursor cur) {
		// we convert by an intermediate step
		List<Game> gameList = new ArrayList<>();
		Game game;
		ContentValues            cv;
		if(cur.moveToFirst()) {
			do {
				cv = new ContentValues();
				DatabaseUtils.cursorRowToContentValues(cur, cv);
				game = Game.fromCVal(cv);
				gameList.add(game);
			} while(cur.moveToNext());
		}
		return gameList;
	}

	//endregion
}
