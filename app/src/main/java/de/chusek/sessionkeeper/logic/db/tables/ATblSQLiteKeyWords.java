package de.chusek.sessionkeeper.logic.db.tables;

/**
 * Diese Klasse dient dazu alle
 * benoetigten SQL-Schluesselwoerter
 * gekapselt und zentral zur Verfuegung zu
 * stellen
 * Created by Alfa-Dozent on 10.02.2017.
 */

public abstract class ATblSQLiteKeyWords {

	//region Datatypes
	public static final String TYPE_INTEGER           = " INTEGER ";
	public static final String TYPE_INTEGER_INC_COMMA = " INTEGER, ";

	public static final String TYPE_REAL           = " REAL ";
	public static final String TYPE_REAL_INC_COMMA = " REAL, ";

	public static final String TYPE_TEXT_INC_COMMA = " TEXT, ";
	public static final String TYPE_TEXT           = " TEXT ";
	//endregion

	//region table actions
	protected static final String CREATE_TBL      = "CREATE TABLE ";
	protected static final String SELECT_TBL      = "SELECT * FROM ";
	protected static final String DROP_TBL        = "DROP TABLE IF EXISTS ";
	protected static final String UPDATE_TBL      = "UPDATE ";
	protected static final String DELETE_FROM_TBL = "DELETE FROM ";
	//endregion

	//region keys
	protected static final String PRIMARY_KEY_DEFINITION = TYPE_INTEGER + "PRIMARY KEY AUTOINCREMENT, ";
	//endregion

	//region operators
	public static final String SET_OPERATOR                     = " SET ";
	public static final String AND_OPERATOR                     = " AND ";
	public static final String EQUALS_OPERATOR_INC_PLACE_HOLDER = " =?";
	//endregion

	//region conditions
	public static final String WHERE_CONDITION = " WHERE ";

	//endregion

	//region chars
	public static final String COMMA                   = ", ";
	public static final String OPEN_BRACKET            = "(";
	public static final String CLOSE_BRACKET		   = ")";
	public static final String CLOSE_BRACKET_SEMICOLON = ");";
	public static final String SEMICOLON 			   = ";";

	//endregion
}
