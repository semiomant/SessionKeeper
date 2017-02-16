package de.chusek.sessionkeeper.logic.db.tables;

/**
 * Created by carste on 13.02.2017.
 *
 * this the base Table holding all the COLUMN NAMES used in all Table classes;
 * these names repeat, so it is useful to define them here
 *
 * TableClasses Should not be instantiated, so they are abstract with static fields and methods
 *
 * again the repeated concrete method TblX.create & TblX.drop have no content here and this are not
 * implemented. If we really really wanted this as a constraint make an interface ITblMehods (or
 * something like that, but again: KISS)
 */

public abstract class ABaseTable extends ATblSQLiteKeyWords {

	public static final String  COL_ID = "_id";
	public static final String  COL_CREATED = "created";
	public static final String  COL_UPDATED = "updated";

}
