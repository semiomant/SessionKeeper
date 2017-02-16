package de.chusek.sessionkeeper.logic.listener;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import de.chusek.sessionkeeper.R;
import de.chusek.sessionkeeper.logic.db.DbManager;
import de.chusek.sessionkeeper.model.Game;
import de.chusek.sessionkeeper.model.Player;

/**
 * Created by Alfa on 15.02.2017.
 */

public class PlayerActivityListener implements View.OnClickListener {

	private final String NEW = "new";

	private AppCompatActivity linkedActivity;

	//widgets
	private EditText txtPlayerName;
	private EditText txtAge;
	private EditText txtPhoneNr;
	private TextView txtNoGames;
	private Button	 btnDeletePlayer; // KISS: no edit mode wankery
	private Button   btnSavePlayer;

	private Player currentPlayer;

	//region constructor(s)

	public PlayerActivityListener(AppCompatActivity linkedActivity, ContentValues extraData) {
		this.linkedActivity = linkedActivity;
		generateWidgets();
		generatePlayer(extraData);
	}

	//endregion

	//region on click
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnSavePlayer:
				updatePlayerObject();
				DbManager.getInstance(linkedActivity).savePlayer(currentPlayer);
				break;
			case R.id.btnDeletePlayer:
				DbManager.getInstance(linkedActivity).delete(currentPlayer);
				//go back to list but not by Intent tat adds to stack!
				linkedActivity.finish();
				break;
		}
	}

	//endregion

	//region helpers
	//generate Objects to access the Widgets
	private void generateWidgets() {
		txtPlayerName = (EditText) linkedActivity.findViewById(R.id.txtPlayerName);
		txtAge = (EditText) linkedActivity.findViewById(R.id.txtAge);
		txtPhoneNr = (EditText) linkedActivity.findViewById(R.id.txtPhoneNr);
		txtNoGames = (EditText) linkedActivity.findViewById(R.id.txtNoGames);
		btnDeletePlayer = (Button)	linkedActivity.findViewById(R.id.btnDeletePlayer);
		btnSavePlayer = (Button)	linkedActivity.findViewById(R.id.btnSavePlayer);

		// set listener
		btnDeletePlayer.setOnClickListener(this);
		btnSavePlayer.setOnClickListener(this);
	}

	private void generatePlayer(ContentValues extraData) {
		//is this extraData really a player? or an indicator for creating a new player?
		// the type system fucks with us, we cannot append a boolean primitive to the key,
		//comparing null and false does not work, we simply take the existence to a value to the
		//key as truthy
		if (extraData.get(NEW) != null) {
			currentPlayer = new Player();
			Toast.makeText(linkedActivity, R.string.strNewPlayerMsg, Toast.LENGTH_SHORT).show();

		} else {
			currentPlayer = Player.fromCVal(extraData);
			showPlayer();
		}
	}

	private void showPlayer() {
		txtPlayerName.setText(currentPlayer.getStrName());
		txtAge.setText(String.valueOf(currentPlayer.getiAge()));
		txtPhoneNr.setText(currentPlayer.getStrPhoneNr());
		txtNoGames.setText(String.valueOf(currentPlayer.getiNoOfGames()));
	}

	private void updatePlayerObject() {
		currentPlayer.setStrName(txtPlayerName.getText().toString());
		currentPlayer.setiAge(Integer.valueOf(txtAge.getText().toString()));
		currentPlayer.setStrPhoneNr(txtPhoneNr.getText().toString());
		currentPlayer.setiNoOfGames(Integer.valueOf(txtNoGames.getText().toString()));
	}


	//endregion
}
