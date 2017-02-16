package de.chusek.sessionkeeper.logic.db.tables;

/**
 * Created by carsten on 13.02.2017.
 *
 * hold COLUMN (COL) names for Session {@link de.chusek.sessionkeeper.model.Session}
 *
 * A Score object would be conenient, especially now that we dont have an ORM to save associations
 * automatically (added? yes)
 *
 * so, scores aren't here, they will be retrieved by session id
 */

public class TblSession extends ABaseTable {

	public static final String NAME = "sessions";

	// for the fields follow link in above in Doc comment
	public static final String  COL_GAMEID = "game_id";
	public static final String  COL_DATE = "date";
	public static final String  COL_NOTES  = "notes";
	public static final String  COL_LENGTH = "length";

	//commands for DB
	public static final String  CMD_CREATE_TBL = CREATE_TBL + NAME + OPEN_BRACKET +
			COL_ID + PRIMARY_KEY_DEFINITION + //Autoincrement MORGEN
			COL_GAMEID + TYPE_INTEGER + COMMA +
			COL_DATE + TYPE_TEXT + COMMA +
			COL_NOTES + TYPE_TEXT + COMMA +
			COL_LENGTH + TYPE_REAL + COMMA +
			COL_CREATED + TYPE_TEXT+ COMMA +
			COL_UPDATED + TYPE_TEXT +
			CLOSE_BRACKET_SEMICOLON;
	public static final String  CMD_DROP_TBL = DROP_TBL + NAME + SEMICOLON;

	//region create table /drop

	//endregion

	// don't make instances
	private TblSession() {}
}
