package de.chusek.sessionkeeper.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import de.chusek.sessionkeeper.R;
import de.chusek.sessionkeeper.gui.listVews.GameListActivity;
import de.chusek.sessionkeeper.gui.listVews.PlayerListActivity;

/**
 *  I did this =)
 *
 *  this is the main activity
 *
 *  everything starts here and comes back to here (if you dnt kill the app another way)
 *
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	private Button btnGameList;
	private Button btnPlayerList;
	private Button btnStartSession;


	//region constructor & lifecycle
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity_layout);

		btnGameList = (Button) findViewById(R.id.btnGameList);
		btnPlayerList = (Button) findViewById(R.id.btnPlayerList);
		btnStartSession = (Button) findViewById(R.id.btnStartSession);

		btnGameList.setOnClickListener(this);
		btnPlayerList.setOnClickListener(this);
		btnStartSession.setOnClickListener(this);

	}
	//endregion

	//region on Click

	@Override
	public void onClick(View v) {

		Class nextActivityClass = null;

		switch (v.getId()) {
			case R.id.btnGameList:
				nextActivityClass = GameListActivity.class;
				break;
			case R.id.btnPlayerList:
				nextActivityClass = PlayerListActivity.class;
				break;
			case R.id.btnStartSession:
				nextActivityClass = SessionActivity.class;
				break;
		}
		Intent nextActivity = new Intent(this, nextActivityClass);
		startActivity(nextActivity);
	}


	//endregion

}
