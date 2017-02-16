package de.chusek.sessionkeeper.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import de.chusek.sessionkeeper.R;
import de.chusek.sessionkeeper.logic.db.DbManager;
import de.chusek.sessionkeeper.model.Game;
import de.chusek.sessionkeeper.model.Player;
import de.chusek.sessionkeeper.model.Score;
import de.chusek.sessionkeeper.model.Session;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

	private Button button;
	private Button button2;
	private Button button3;
	private TextView textView;
	private TextView textView2;
	private TextView textView3;

	private Game   g;
	private Player p;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_activity_layout);

		button = (Button) findViewById(R.id.button);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		textView = (TextView) findViewById(R.id.textView);
		textView2 = (TextView) findViewById(R.id.textView2);
		textView3 = (TextView) findViewById(R.id.textView3);

		button.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.button:
				testCreateGame();
				break;
			case R.id.button2:
				testCreatePlayer();
				break;
			case R.id.button3:
				testCreateSession();
				break;
			default:
				Toast.makeText(this, "badBADbad", Toast.LENGTH_SHORT).show();
		}
	}

	private void testCreateSession() {
		if ((g == null) || (p == null)) {
			Toast.makeText(this, "create g & p", Toast.LENGTH_SHORT).show();
			return;
		}
		Session s = new Session();
		Score sc = new Score(s.getId(),p,0);
		//s.addPlayerViaScore(sc);
		s.setGame(g);
		textView3.setText(s.toString());
		DbManager.getInstance(this).insert(s);
		Toast.makeText(this, "we get to here", Toast.LENGTH_SHORT).show();
	}

	private void testCreatePlayer() {
		p = new Player ();
		p.setStrName("Heini");
		p.setiAge(33);
		p.setStrPhoneNr("123456");
		textView2.setText(p.toString());
		DbManager.getInstance(this).insert(p);
	}

	private void testCreateGame() {
		g = new Game();
		g.setStrName("würfln");
		g.setStrAuthor("keina");
		g.setStrPublisher("der laden");
		g.setiYear(99);
		textView.setText(g.toString());
		DbManager.getInstance(this).insert(g);
	}
}
