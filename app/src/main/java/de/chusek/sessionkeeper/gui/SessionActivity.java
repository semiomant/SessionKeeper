package de.chusek.sessionkeeper.gui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import de.chusek.sessionkeeper.R;
import de.chusek.sessionkeeper.logic.listener.SessionActivityListener;

/**
 *  created my Me, Moi, meine Wenigkeit
 *
 * all relevant comments are in  {@link de.chusek.sessionkeeper.logic.listener.SessionActivityListener}
 */

public class SessionActivity extends AppCompatActivity {

	SessionActivityListener linkedListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.session_activity_layout);

		linkedListener = new SessionActivityListener(this);
	}

	// because of the way permissions work, we need a method that delegates to the Listener
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		linkedListener.permissionReqResult(requestCode,permissions,grantResults);
	}
}
