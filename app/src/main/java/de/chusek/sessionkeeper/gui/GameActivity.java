package de.chusek.sessionkeeper.gui;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import de.chusek.sessionkeeper.R;
import de.chusek.sessionkeeper.logic.listener.GameActivityListener;

/**
 *  created my Me, Moi, meine Wenigkeit
 *
 *  edit selected item here
 *  save persists to db (no dirty checking)
 *  delete throws selected item away
 *
 *  after delete you are sent back to ListView
 *  but don#t start as new intent (that would add to the stack) , do .finish()
 */

public class GameActivity extends AppCompatActivity {

	//in player this is a magic string, where Java Constant
	private static final String EXTRA_TAG = "game";

	private GameActivityListener linkedListener;
	//private Game currentGame;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_activity_layout);

		ContentValues extraCV = (ContentValues) getIntent().getExtras().get(EXTRA_TAG);
		linkedListener = new GameActivityListener(this, extraCV);

	}
}
