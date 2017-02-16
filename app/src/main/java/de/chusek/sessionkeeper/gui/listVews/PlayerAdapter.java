package de.chusek.sessionkeeper.gui.listVews;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import de.chusek.sessionkeeper.R;
import de.chusek.sessionkeeper.logic.db.DbManager;
import de.chusek.sessionkeeper.model.Game;
import de.chusek.sessionkeeper.model.Player;

/**
 * Created by Alfa on 14.02.2017.
 */

public class PlayerAdapter extends BaseAdapter {

	// to make things work, gather some stuff
	//Tag is for logging so useful to have but not at present
	private static final String TAG = PlayerAdapter.class.getSimpleName();
	private Context        context;
	private LayoutInflater layoutInflater;

	//the data we adapt in the adapter
	private List<Player> players;

	//region constructor

	public PlayerAdapter(Context context) {
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
		//get all the data
		players = DbManager.getInstance(context).selectAllPlayers();
	}

	//endregion

	//implement everything to schema
	//region adapter
	// size of List
	@Override
	public int getCount() {
		return players.size();
	}

	// it's true id is the index, what a happy coincidence ;)
	@Override
	public long getItemId(int position) {
		return position;
	}

	//this needs to be here, sigh
	@Override
	public Object getItem(int position) {return players.get(position);}

	// this  build ech individual item from a helper construct ItemView
	@Override
	public View getView(int position, View listItemView, ViewGroup parent) {
		// we need  sth to hold thew item View schema
		ItemView itemView = null;

		// is it availble
		if (listItemView == null) {
			//if not generate it
			listItemView = layoutInflater.inflate(R.layout.list_view_item_layout, parent, false	);
			//add widgets
			TextView txtvName = (TextView) listItemView.findViewById(R.id.txtvListName);
			//make itemView for listItemView
			itemView = new ItemView(txtvName);
			listItemView.setTag(itemView);
		} else {
			// we leave logging out put back in if Exception
			itemView = (ItemView) listItemView.getTag();
		}

		//from model to view
		Player p = (Player) getItem(position);
		itemView.getTextvName().setText(p.getStrName());

		// and we are done yay
		return listItemView;
	}
	//endregion

	public List<Player> getList() {return players;}
}
