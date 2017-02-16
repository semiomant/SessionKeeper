package de.chusek.sessionkeeper.logic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.List;

import de.chusek.sessionkeeper.gui.GameActivity;
import de.chusek.sessionkeeper.model.Game;

/**
 * Created by carsten on 14.02.2017.
 *
 * Listens for clicks on the and shoves the right object into the singleObject View
 */

public class GameListOnItemClickListener implements AdapterView.OnItemClickListener {

	//just to show I can do it =)
	private final String EXTRA_TAG = "game";

	private AppCompatActivity linkedActivity;
	private List<Game>        games;

	public GameListOnItemClickListener(AppCompatActivity linkedActivity, List<Game> glist) {
		this.linkedActivity = linkedActivity;
		games = glist;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// the same procedure as everywhere
		Intent nextActivity = new Intent(linkedActivity, GameActivity.class);
		//add extra to intent! with PUT , sigh
		// and u cant pass anything so again ContentValues seems our friend
		nextActivity.putExtra(EXTRA_TAG, games.get(position).toCVal());
		linkedActivity.startActivity(nextActivity);
	}
}
