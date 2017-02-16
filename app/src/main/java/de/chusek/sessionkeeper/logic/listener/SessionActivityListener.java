package de.chusek.sessionkeeper.logic.listener;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.chusek.sessionkeeper.R;
import de.chusek.sessionkeeper.gui.PhotoActivity;
import de.chusek.sessionkeeper.logic.db.DbManager;
import de.chusek.sessionkeeper.logic.helper.DateHelper;
import de.chusek.sessionkeeper.model.Game;
import de.chusek.sessionkeeper.model.Player;
import de.chusek.sessionkeeper.model.Score;
import de.chusek.sessionkeeper.model.Session;

/**
 * Created by carsten on 15.02.2017.
 *
 * Well this is a rather primitive Activity, and to make it work we would have to spilt the thing
 * in several acivities, use stacking layouts like tabs or something like that
 * THIS listener implements all the basic stuff, permissions first
 * it might be improved by ListViews, but time constraints do not allow for detailed programing for
 * reuse of listViews
 *
 * since we would not have a good view of the result, the take a photo thing gets its own crutch-Activity
 *
 * also it would be nice to have listLiew to select old session (in fact listViews everywhere)
 *
 * basically this activity show what work would be needed to finish it
 * i daresay it would take about 30 more hrs
 *
 * The PERMISSIONS work, by way of the take a photo btn (pic is NOT saved)
 * and the PHONE button, right now you simply phone the player selected in the Spinner
 *
 * Three Button MANAGE a selected {@link Player} a score field in the selected games
 * add - player added as {@link de.chusek.sessionkeeper.model.Score}
 * delete - remove score if the selected player is in one
 * score - add points to the score of the selected player
 * ((idea score could also contain the color of the player --- another complication to the UI
 * that we dont need now))
 * once this is implemented we could turn to save Session etc.
 *
 */

public class SessionActivityListener implements View.OnClickListener,
												AdapterView.OnItemSelectedListener {
	// this adds the idea of starting points to Game
	private final int NO_POINTS = 0;
	//phone permission stuff
	private final int PERMISSIBLE = PackageManager.PERMISSION_GRANTED;
	private final int REQUEST_CODE = 1;
	private final String PHONE_PERMISSION = Manifest.permission.CALL_PHONE;

	private AppCompatActivity linkedActivity;

	// these dont need to be perivate, without declaration they are protected
	// and it's not like we will inherit from this class
	// make private or stupid linter or compiler will complain
	private Spinner  spGame;
	private TextView txtvSessionDate;
	private Spinner  spPlayer;
	private Button   btnScoreAdd;
	private Button   btnScoreDelete;
	private Button   btnScoreScore;
	private Button   btnScorePhone;
	private EditText txtScoreValue;
	private TextView txtvScores;
	private Button   btnSessionSave;
	private Button   btnCancelSesseion;
	private Button   btnSessionPhoto;
	//don't need the labels ...
	//TextView txtvGameSpinnerLabel;
	//TextView txtvPlayerSpinnerLabel;
	//TextView txtvDateLabel;

	//Data
	private Session      session;
	private List<Game>   games;
	private List<Player> players;
	private Game         selectedGame;
	private Player       selectedPlayer;


	//region constructor(s)

	public SessionActivityListener(AppCompatActivity linkedActivity) {
		this.linkedActivity = linkedActivity;
		generateWidgets();
		// these two could become generateData(), but not now
		session = new Session();
		session.setPlayers(new ArrayList<Score>());
		txtvSessionDate.setText(DateHelper.getCurrentTimeStamp());
		loadData();
		showData();
	}

	//endregion

	//region on click && on select
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnScorePhone:
				//select player (also in score to work) and phone
				//helper function
				phonesSelectedPlayer();
				break;
			case R.id.btnSessionPhoto:
				//start photoActivity
				Intent nextActivity = new Intent(linkedActivity, PhotoActivity.class);
				linkedActivity.startActivity(nextActivity);
				//and we're done here (as far as project requirements are concerned)
				break;
			case R.id.btnScoreAdd:
				addPlayerAsScore();
				break;
			case R.id.btnScoreDelete:
				deleteScore();
				break;
			case R.id.btnScoreScore:
				scoreScore();
				break;
		}
	}


	// run when an item in spinner is selected
	// not if you select the one already selected
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		switch (parent.getId()) {
			case R.id.spGame:
				selectedGame = games.get(position);
				session.setGame(selectedGame);
				//Toast.makeText(linkedActivity, selectedGame.toString(), Toast.LENGTH_SHORT).show();
				break;
			case R.id.spPlayer:
				selectedPlayer = players.get(position);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		//will we ever see this? no matter needs implementing
		txtToast("onNothingSelected");
	}
	//endregion

	//region helpers

	private void loadData() {
		games = DbManager.getInstance(linkedActivity).selectAllGames();
		players = DbManager.getInstance(linkedActivity).selectAllPlayers();
		// by spinner design the first value is always selected
		// NOT NEEDED spinner selects on setting the adapter
		// that works because the data is loaded before the adapters are attached to the spinners
		//selectedGame = games.get(0);
		//selectedPlayer = players.get(0);
	}

	private void showData() {
		// makes the adapters for the spinners
		// the adapters are String ArrayList, the most simple think possible
		//String[] sA = {"haha","lala", "dudu", "huhu"};
		ArrayList<String> glist = new ArrayList<String>(); //Arrays.asList(sA)
		ArrayList<String> plist = new ArrayList<String>(); //Arrays.asList(sA)
		// data to names list map
		for (Game g : games) {
			glist.add(g.getStrName());
		}
		for (Player p : players) {
			plist.add(p.getStrName());
		}
		//into spinner
		ArrayAdapter<String> gameNames = new ArrayAdapter<String>(linkedActivity,
															android.R.layout.simple_spinner_item,
															glist);
		ArrayAdapter<String> playerNames = new ArrayAdapter<String>(linkedActivity,
															android.R.layout.simple_spinner_item,
															plist);

		spGame.setAdapter(gameNames);
		spPlayer.setAdapter(playerNames);
		
		//also scores
		showScores();
	}

	private void generateWidgets() {

		//would need reflection to iterate
		spGame = (Spinner) linkedActivity.findViewById(R.id.spGame);
		spPlayer = (Spinner) linkedActivity.findViewById(R.id.spPlayer);
		txtvSessionDate = (TextView) linkedActivity.findViewById(R.id.txtvSessionDate);
		txtvScores = (TextView) linkedActivity.findViewById(R.id.txtvScores);
		txtScoreValue = (EditText) linkedActivity.findViewById(R.id.txtScoreValue);
		btnScoreAdd = (Button) linkedActivity.findViewById(R.id.btnScoreAdd);
		btnScoreDelete = (Button) linkedActivity.findViewById(R.id.btnScoreDelete);
		btnScoreScore = (Button) linkedActivity.findViewById(R.id.btnScoreScore);
		btnScorePhone = (Button) linkedActivity.findViewById(R.id.btnScorePhone);
		btnSessionSave = (Button) linkedActivity.findViewById(R.id.btnSessionSave);
		btnCancelSesseion = (Button) linkedActivity.findViewById(R.id.btnCancelSesseion);
		btnSessionPhoto = (Button) linkedActivity.findViewById(R.id.btnSessionPhoto);

		// set spinner call back
		spGame.setOnItemSelectedListener(this);
		spPlayer.setOnItemSelectedListener(this);

		txtScoreValue.setEnabled(false);

		//set
		btnScoreAdd.setOnClickListener(this);
		btnScoreDelete.setOnClickListener(this);
		btnScoreScore.setOnClickListener(this);
		btnScorePhone.setOnClickListener(this);
		btnSessionSave.setOnClickListener(this);
		btnCancelSesseion.setOnClickListener(this);
		btnSessionPhoto.setOnClickListener(this);

	}

	private void txtToast(String txt) {
		Toast.makeText(linkedActivity, txt, Toast.LENGTH_SHORT).show();
	}

	private void showScores(){
		String strOutput = "";
		for (Score sc : session.getPlayers()) {
			strOutput += sc.getPlayer().getStrName() + 
					     " --- " + Integer.valueOf(sc.getiScore()) +
						 "\n";
		}
		if (strOutput.equals("")) {strOutput = linkedActivity.getString(R.string.strNoPlayers);}
		txtvScores.setText(strOutput);
	}

	//endregion

	//region phone home

	private void phonesSelectedPlayer() {
		/**
		 * checking permision by a guard pattern
		 * (@link: https://en.wikipedia.org/wiki/Guard_(computer_science)#example)
		 */
		if (ActivityCompat.checkSelfPermission(linkedActivity, PHONE_PERMISSION) != PERMISSIBLE) {
			String[] detourDef = {PHONE_PERMISSION};
			linkedActivity.requestPermissions(detourDef, REQUEST_CODE);
			return;
		}
		// when past above guard
		String strNumber = linkedActivity.getString(R.string.strPhoneNumberURIprefix)
							+selectedPlayer.getStrPhoneNr();
		Intent call = new Intent(Intent.ACTION_CALL, Uri.parse(strNumber));
		//noinspection MissingPermission
		linkedActivity.startActivity(call); // we checked permission above
											// without permission its impossible to get here
											// no reason to program to the IDE =/
	}

	public void permissionReqResult(int requestCode, String[] permissions, int[] grantResults) {
		//@NunNull tests ran in overridden lnkActivity
	if(requestCode == REQUEST_CODE && grantResults[0] == PERMISSIBLE) {
			phonesSelectedPlayer();
		} else {
			Log.e("SessionActivityListener","!ERR: "+Integer.toString(requestCode)+";"+grantResults[0]);
		}
	}
	//endregion

	//region "player as score" buttons

	private void addPlayerAsScore() {
		//is selected player a score? Use guard, because dont add twice or more
		if (isSelectedPlayerAScore() == true) {
			txtToast(linkedActivity.getString(R.string.errAlreadyAdded));
			return;
		}
		Score sc = new Score(session.getId(),selectedPlayer,NO_POINTS);
		session.addPlayerViaScore(sc);
		showScores();
	}

	private void deleteScore(){
		//is selected player a score? Use guard
		if (isSelectedPlayerAScore() == false) {
			txtToast(linkedActivity.getString(R.string.errNotAdded));
			return;
		}
		//more later
		// a testament to the type system
		txtToast(Integer.valueOf(session.getPlayers().indexOf(scoreForSelectedPlayer())).toString());
		session.getPlayers().remove(scoreForSelectedPlayer());
		//updateView
		showScores();
	}

	private void scoreScore() {
		//is selected player a score? Use guard
		if (isSelectedPlayerAScore() == false) {
			txtToast(linkedActivity.getString(R.string.errNotAdded));
			return;
		}
		//first hit enables the editText(number)
		//second actually adds score
		//implement after add/delete
		txtToast("there wa no more time =(");
	}

	private boolean isSelectedPlayerAScore() {
		boolean bResult = false;
		//find Score
		Score sc = scoreForSelectedPlayer();
		if (sc != null) {bResult=true;};
		return bResult;
	}

	private Score scoreForSelectedPlayer() {
		Score found = null;
		//do stuff here
		for (Score sc : session.getPlayers()) {
			if (sc.getPlayer().getId() == selectedPlayer.getId()) {
				found = sc;
				break;
			}
		}
		return found;
	}
	//endregion

}
