package de.chusek.sessionkeeper.gui.listVews;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import de.chusek.sessionkeeper.R;
import de.chusek.sessionkeeper.gui.GameActivity;
import de.chusek.sessionkeeper.logic.listener.GameListOnItemClickListener;

/** As reference implementation for the other ListView I put all commentary here
 *
 * if I do both adapters manually I might see a way to an ListView with parameters, used everywhere
 * but for the moment duplication and doing it two times is my learning friend =)
 */

public class GameListActivity extends AppCompatActivity {

	private final String EXTRA_TAG = "game";
	private final String NEW = "new";


	//hold refernces for all important players in the Listview
	private static ListView                    lvGameList;
	private static GameAdapter                 gameAdapter;
	private static GameListOnItemClickListener clickListener;

	//region constructor & life cycle
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_list_activity_layout);

		// get Widget
		lvGameList = (ListView) findViewById(R.id.lvGameList);
		wireUpListView();
	}

	//when return to list redraw by forcing new listView
	@Override
	protected void onResume() {
		super.onResume();
		wireUpListView();
	}

	private void wireUpListView() {
		// generate Adapter & Listener & add to listview
		gameAdapter = new GameAdapter(this);
		// if we dont have a reference to the item in the List in the SingleObjActivity
		//we try this -- and it works !
		clickListener =  new GameListOnItemClickListener(this, gameAdapter.getList());
		lvGameList.setAdapter(gameAdapter);
		lvGameList.setOnItemClickListener(clickListener);
	}

	//endregion

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
		Intent nextActivity = new Intent(this, GameActivity.class);
		//create extra
		ContentValues cv = new ContentValues();
		cv.put(NEW, new Boolean(true));
		nextActivity.putExtra(EXTRA_TAG, cv);

		startActivity(nextActivity);

		return true;
	}

	//endregion


}
