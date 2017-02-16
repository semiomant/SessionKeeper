package de.chusek.sessionkeeper.gui;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import de.chusek.sessionkeeper.R;
import de.chusek.sessionkeeper.logic.listener.PlayerActivityListener;
import de.chusek.sessionkeeper.model.Player;

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

public class PlayerActivity extends AppCompatActivity {

	private PlayerActivityListener linkedListener;

	private Player   currentPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player_activity_layout);

		ContentValues extraCV = (ContentValues) getIntent().getExtras().get("player");
		linkedListener = new PlayerActivityListener(this, extraCV);

	}
}
