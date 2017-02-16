package de.chusek.sessionkeeper.logic.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import de.chusek.sessionkeeper.logic.db.tables.TblGame;
import de.chusek.sessionkeeper.logic.db.tables.TblPlayer;
import de.chusek.sessionkeeper.logic.db.tables.TblScore;
import de.chusek.sessionkeeper.logic.db.tables.TblSession;
import de.chusek.sessionkeeper.model.Game;
import de.chusek.sessionkeeper.model.Player;
import de.chusek.sessionkeeper.model.Score;
import de.chusek.sessionkeeper.model.Session;

/**
 * Created by carsten on 13.02.2017.
 *
 * this singleton handles reading and writing to the database
 * it is a singleton for synchronized (threadsafe) access to DB
 *
 * We can overload the methods insert, update, delete and have much shorter methods name
 * the intent of ""DB.insert(Player)"" (or whatever model) is still explicit enough
 */

public class DbManager extends SQLiteOpenHelper {

	private static final String DB_NAME    = "SessionKeeper.db";
	private static final int    DB_VERSION = 1;
	private static final int    NEW_ID = -1;

	private static final String TAG = DbManager.class.getSimpleName();

	//region singelton
	private static DbManager instance;

	private DbManager (Context c) {
		super(c, DB_NAME, null, DB_VERSION);
		//what we do now is to force a call to on onCreate (see below);
		getWritableDatabase();
	}

	public static synchronized DbManager getInstance(Context c) {
		if (instance == null) {
			instance = new DbManager(c);
		}
		return instance;
	}
	//endregion

	//region DB.onCreate, DB.onUpgrade
	@Override
	public void onCreate(SQLiteDatabase db) {
		//create all tables, as forced in constructor
		//do in try so app doesn't crash
		try {
			db.execSQL(TblGame.CMD_CREATE_TBL);
			db.execSQL(TblPlayer.CMD_CREATE_TBL);
			db.execSQL(TblScore.CMD_CREATE_TBL);
			db.execSQL(TblSession.CMD_CREATE_TBL);
		} catch (SQLException ex) {
			// no crash and logging
			Log.e(TAG, ex.getMessage() + "\n\n" + ex.getStackTrace());
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try {
			db.execSQL(TblGame.CMD_DROP_TBL);
			db.execSQL(TblPlayer.CMD_DROP_TBL);
			db.execSQL(TblScore.CMD_DROP_TBL);
			db.execSQL(TblSession.CMD_DROP_TBL);

			onCreate(db); // DRY
		} catch (SQLException ex) {
			// no crash and logging
			Log.e(TAG, ex.getMessage() + "\n\n" + ex.getStackTrace());
		}
	}
	//endregion

	//region selectAll (no overloads, because no parameters)

	public List<Game> selectAllGames() {
		List<Game> games = null;
		try {
			//get DB
			SQLiteDatabase db = getReadableDatabase();
			//run query
			Cursor cursor = db.query(TblGame.NAME,null,null,null,null,null,null);
			// convert Cursor to List
			games = TblGame.listFromCursor(cursor);
			//close db
			db.close();
		} catch (SQLException ex) {
			// no crash and logging
			Log.e(TAG, ex.getMessage() + "\n\n" + ex.getStackTrace());
		}
		// we're done here
		return games;
	}

	public List<Player> selectAllPlayers() {
		List<Player> players = null;
		try {
			//get DB
			SQLiteDatabase db = getReadableDatabase();
			//run query
			Cursor cursor = db.query(TblPlayer.NAME,null,null,null,null,null,null);
			// convert Cursor to List
			players = TblPlayer.listFromCursor(cursor);
			//close db
			db.close();
		} catch (SQLException ex) {
			// no crash and logging
			Log.e(TAG, ex.getMessage() + "\n\n" + ex.getStackTrace());
		}
		// we're done here
		return players;
	}

	public List<Session> selectAllSessions() {
		List<Session> sessions = null;
		//this does nothing, for demonstrating  the requirements we don't have to implement this ....
		//otherwise it's not hard, but I really don't wanna do a third listView
		// we're done here
		return sessions;
	}

	//endregion

	//save decide between insert and update
	// a more general method would need some kind of interface ISaveable or whatever
	public void saveGame(Game currentGame) {
		Log.d(TAG, "=================="+String.valueOf(currentGame.getId()));

		if (currentGame.getId() == NEW_ID) {
			insert(currentGame);
			Log.d(TAG, "#######################################");
		} else {
			update(currentGame);
			Log.d(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		}
	}

	public void savePlayer(Player currentPlayer) {
		if (currentPlayer.getId() == NEW_ID) {
			insert(currentPlayer);
		} else {
			update(currentPlayer);
		}
	}

	//region saveX(x) (overloaded methods)


	//endregion

	//region insert (overloaded Methods)

	public long insert(Game game) {
		//all method follow the same schema, so comments are only in this method
		// we could abstract here, but duplication is faster
		long lngRowId = -1;
		try {
			//get database
			SQLiteDatabase db = getWritableDatabase();
			// we convert to ContenValues in the Model, so move on to insert
			// but alas if you pass id, too there will be exceptions
			//return type void of remove forces verbosity -- bad api design =(
			ContentValues cv = game.toCVal();
			cv.remove(TblGame.COL_ID);
			lngRowId = db.insert(TblGame.NAME, null, cv);
			db.close();
		} catch (SQLException ex) {
			// no crash and logging
			Log.e(TAG, ex.getMessage() + "\n\n" + ex.getStackTrace());
		}
		return lngRowId;
	}

	//all comments in Game overload above
	public long insert(Player player) {
		long lngRowId = -1;
		try {
			SQLiteDatabase db = getWritableDatabase();
			ContentValues cv = player.toCVal();
			cv.remove(TblPlayer.COL_ID);
			lngRowId = db.insert(TblPlayer.NAME, null, cv);
			db.close();
		} catch (SQLException ex) {
			Log.e(TAG, ex.getMessage() + "\n\n" + ex.getStackTrace());
		}
		return lngRowId;
	}

	//score contains player, but don't save that here
	//player doesn't get modified in Session
	// toCVal takes care of converting to id
	public long insert(Score score) {
		long lngRowId = -1;
		try {
			SQLiteDatabase db = getWritableDatabase();
			// test here if score already exists!
			//if exist
			lngRowId = db.insert(TblScore.NAME, null, score.toCVal() );
			//else
			//....
			db.close();
		} catch (SQLException ex) {
			Log.e(TAG, ex.getMessage() + "\n\n" + ex.getStackTrace());
		}
		return lngRowId;
	}

	//score contains game, but don't save that here
	//game doesn't get modified in Session
	//toCVal takes care of converting to id
	public long insert(Session session) {
		long lngRowId = -1;
		try {
			SQLiteDatabase db = getWritableDatabase();
			lngRowId = db.insert(TblSession.NAME, null, session.toCVal() );
			for (Score sc : session.getPlayers()) {
				insert(sc);
			}
			db.close();
		} catch (SQLException ex) {
			Log.e(TAG, ex.getMessage() + "\n\n" + ex.getStackTrace());
		}
		return lngRowId;
	}


	//endregion

	//region update (overloaded Methods)
	//to make a method for updating
	private int update(Game game) {
		int iRowCount = -1;
		try {
			//need DB
			SQLiteDatabase db = getWritableDatabase(); //update is write
			//build Args, because of  delegation we need only whereArgs
			String[] strWhereArgs = {String.valueOf(game.getId())};
			//execute
			iRowCount = db.update(TblGame.NAME, game.toCVal(), TblGame.UPDATE_WHERE_CLAUSE, strWhereArgs);
			//cleanup
			db.close();
		}  catch (SQLException ex) {
			// no crash and logging
			Log.e(TAG, ex.getMessage() + "\n\n" + ex.getStackTrace());
		}
		return iRowCount;
	}

	//comments in game variant
	private int update(Player player) {
		int iRowCount = -1;
		try {
			SQLiteDatabase db = getWritableDatabase(); //update is write
			String[] strWhereArgs = {String.valueOf(player.getId())};
			iRowCount = db.update(TblPlayer.NAME, player.toCVal(), TblPlayer.UPDATE_WHERE_CLAUSE, strWhereArgs);
			db.close();
		}  catch (SQLException ex) {
			Log.e(TAG, ex.getMessage() + "\n\n" + ex.getStackTrace());
		}
		return iRowCount;
	}
	//endregion

	//region delete (overloaded Methods)
	public int delete(Game game) {
		int iRowCount = -1;

		try {
			//need db
			SQLiteDatabase db = getWritableDatabase();
			//this tome by complie statement SQLiteStatement
			SQLiteStatement stmnt = db.compileStatement(TblGame.DELETE_WHERE_ID_TBL_USER);
			//bind Args
			String[] strWhereArgs = {String.valueOf(game.getId())};
			stmnt.bindAllArgsAsStrings(strWhereArgs);
			//do it
			iRowCount = stmnt.executeUpdateDelete();
			//cleanup
			stmnt.close();
			db.close();
		} catch (SQLiteException sqlEx) {
			Log.e(TAG, sqlEx.getMessage() + "\n\n" + sqlEx.getStackTrace());
		}
		return iRowCount;
	}

	//comments in game overload
	public int delete(Player player) {
		int iRowCount = -1;
		try {
			SQLiteDatabase db = getWritableDatabase();
			SQLiteStatement stmnt = db.compileStatement(TblPlayer.DELETE_WHERE_ID_TBL_USER);
			String[] strWhereArgs = {String.valueOf(player.getId())};
			stmnt.bindAllArgsAsStrings(strWhereArgs);
			iRowCount = stmnt.executeUpdateDelete();
			stmnt.close();
			db.close();
		} catch (SQLiteException sqlEx) {
			Log.e(TAG, sqlEx.getMessage() + "\n\n" + sqlEx.getStackTrace());
		}
		return iRowCount;
	}
	//endregion


}
