package de.chusek.sessionkeeper.logic.db.tables;

/**
 * Created by carsten on 13.02.2017.
 *
 * hold COLUMNs (COL) names for Score {@link de.chusek.sessionkeeper.model.Score}
 *
 * strictly speaking an association table does not need id, created & updated
 * but it will be included here because it does not hurt and might make things easier
 *
 * also we could later find some use for scores alone
 */

public class TblScore extends ABaseTable {

	public static final String NAME = "scores";

	public static final String  COL_PLAYERID = "player_id";
	public static final String  COL_SESSIONID = "session_id";
	public static final String  COL_SCORE = "score";

	//commands for DB
	public static final String  CMD_CREATE_TBL = CREATE_TBL + NAME + OPEN_BRACKET +
			COL_ID + PRIMARY_KEY_DEFINITION + //Autoincrement MORGEN
			COL_PLAYERID + TYPE_INTEGER + COMMA +
			COL_SESSIONID + TYPE_INTEGER + COMMA +
			COL_SCORE + TYPE_INTEGER + COMMA +
			COL_CREATED + TYPE_TEXT+ COMMA +
			COL_UPDATED + TYPE_TEXT +
			CLOSE_BRACKET_SEMICOLON;
	public static final String  CMD_DROP_TBL = DROP_TBL + NAME + SEMICOLON;

	//region create table /drop
	//endregion

}
