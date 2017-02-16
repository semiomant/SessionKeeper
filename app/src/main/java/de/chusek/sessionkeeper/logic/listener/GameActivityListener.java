package de.chusek.sessionkeeper.logic.listener;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import de.chusek.sessionkeeper.R;
import de.chusek.sessionkeeper.gui.listVews.GameListActivity;
import de.chusek.sessionkeeper.logic.db.DbManager;
import de.chusek.sessionkeeper.model.Game;

/**
 * Created by carsten on 15.02.2017.
 *
 */

public class GameActivityListener implements View.OnClickListener {

	private final String NEW = "new";

	private AppCompatActivity linkedActivity;

	//widgets
	private EditText txtGameName;
	private EditText txtPublisher;
	private EditText txtAuthor;
	private TextView txtYear;
	private Button	 btnDeleteGame;
	private Button	 btnSaveGame;

	private Game currentGame;

	//region constructor(s)

	public GameActivityListener(AppCompatActivity linkedActivity, ContentValues extraData) {
		this.linkedActivity = linkedActivity;
		generateWidgets();
		generateGame(extraData);
	}

	//endregion

	//region on click
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnSaveGame:
				updateGameObject();
				DbManager.getInstance(linkedActivity).saveGame(currentGame); // =) lol
				break;
			case R.id.btnDeleteGame:
				DbManager.getInstance(linkedActivity).delete(currentGame);
				//go back to List
				linkedActivity.finish();
				break;
		}
	}

	//endregion

	//region helpers
	//generate Objects to access the Widgets
	private void generateWidgets() {
		 txtGameName = (EditText) linkedActivity.findViewById(R.id.txtGameName);
		 txtPublisher = (EditText) linkedActivity.findViewById(R.id.txtPublisher);
		 txtAuthor = (EditText) linkedActivity.findViewById(R.id.txtAuthor);
		 txtYear = (EditText) linkedActivity.findViewById(R.id.txtYear);
		 btnDeleteGame = (Button)	linkedActivity.findViewById(R.id.btnDeleteGame);
		 btnSaveGame = (Button)	linkedActivity.findViewById(R.id.btnSaveGame);

		// set listener
		btnDeleteGame.setOnClickListener(this);
		btnSaveGame.setOnClickListener(this);
	}

	private void generateGame(ContentValues extraData) {
		//is this extraData really a game? or an indicator for creating a new game?
		// the type system fucks with us, we cannot append a boolean primitive to the key,
		//comparing null and false does not work, we simply take the existence to a value to the
		//key as truthy
		if (extraData.get(NEW) != null) {
			currentGame = new Game();
			Toast.makeText(linkedActivity, R.string.strNewGameMsg, Toast.LENGTH_SHORT).show();
		} else {
			currentGame = Game.fromCVal(extraData);
			showPlayer();
		}
	}

	private void showPlayer() {
		txtGameName.setText(currentGame.getStrName());
		txtPublisher.setText(currentGame.getStrPublisher());
		txtAuthor.setText(currentGame.getStrAuthor());
		txtYear.setText(String.valueOf(currentGame.getiYear()));
	}

	private void enterEditMode() {
		//activate stuff and buttons and so forth
		// this stays here as an artefact
		// or for the future
		// i am making this just difficult for myself if i check userInputs and make edit mode etc
		// at most do dirktyChecking (did data change?)
	}

	private void updateGameObject() {
		// normally you do something like onChange, but not enough time ...
		//this is not surgical precision but a large wooden hammer ;)
		currentGame.setStrName(txtGameName.getText().toString());
		currentGame.setStrPublisher(txtPublisher.getText().toString());
		currentGame.setStrAuthor(txtAuthor.getText().toString());
		// the programming line of type madness
		currentGame.setiYear(Integer.valueOf(txtYear.getText().toString()));
	}

	//endregion


}
