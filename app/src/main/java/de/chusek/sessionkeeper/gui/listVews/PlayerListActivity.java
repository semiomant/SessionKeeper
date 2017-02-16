package de.chusek.sessionkeeper.gui.listVews;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import de.chusek.sessionkeeper.R;
import de.chusek.sessionkeeper.gui.PlayerActivity;
import de.chusek.sessionkeeper.logic.listener.PlayerListOnItemClickListener;

public class PlayerListActivity extends AppCompatActivity {

	private final String EXTRA_TAG = "player";
	private final String NEW = "new";

	//hold refernces for all important players in the Listview
	private static ListView                      lvPlayerList;
	private static PlayerAdapter                 playerAdapter;
	private static PlayerListOnItemClickListener clickListener;

	// more than one Instance needs to know about that list, better than requery it everywhere
	//List<Player> players; MAYBE not

	//region constructor & life cycle
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player_list_activity_layout);

		// get Widget
		lvPlayerList = (ListView) findViewById(R.id.lvPlayerList);
		wireUpListView();
	}

	//when return to list redraw by forcing new listView
	@Override
	protected void onResume() {
		super.onResume();
		wireUpListView();
	}

	private void wireUpListView(){
		// generate Adapter & Listener & add to listview
		playerAdapter = new PlayerAdapter(this);
		// we use this stuff and dont load from DB in the singleObjActivity update listener as well
		clickListener =  new PlayerListOnItemClickListener(this, playerAdapter.getList());
		lvPlayerList.setAdapter(playerAdapter);
		lvPlayerList.setOnItemClickListener(clickListener);
	}

	//endregion

	//region add new menu

	//region Menu for new item
	// this method generates a menu, curiously enough
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.new_item_menu, menu);

		return true; // a magic value ;)
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//start GameActivity here and add extra new!
		Intent nextActivity = new Intent(this, PlayerActivity.class);
		//create extra
		ContentValues cv = new ContentValues();
		cv.put(NEW, new Boolean(true));
		nextActivity.putExtra(EXTRA_TAG, cv);

		startActivity(nextActivity);

		return true;
	}

	//endregion
}
