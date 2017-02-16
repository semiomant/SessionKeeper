package de.chusek.sessionkeeper.logic.listener;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.List;

import de.chusek.sessionkeeper.gui.PlayerActivity;
import de.chusek.sessionkeeper.model.Player;

/**
 * Created by carsten on 14.02.2017.
 */

public class PlayerListOnItemClickListener implements AdapterView.OnItemClickListener {

	private AppCompatActivity linkedActivity;
	private List<Player> players;

	public PlayerListOnItemClickListener(AppCompatActivity linkedActivity, List<Player> plist) {
		this.linkedActivity = linkedActivity;
		players = plist;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// the same procedure as everywhere
		Intent nextActivity = new Intent(linkedActivity, PlayerActivity.class);
		//add extra to intent! with PUT , sigh
		// and u cant pass anything so again ContentValues seems our friend
		nextActivity.putExtra("player", players.get(position).toCVal());
		linkedActivity.startActivity(nextActivity);

	}
}
