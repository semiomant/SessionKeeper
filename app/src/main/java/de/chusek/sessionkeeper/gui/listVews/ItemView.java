package de.chusek.sessionkeeper.gui.listVews;

import android.widget.TextView;

/**
 * Created by carsten on 14.02.2017.
 *
 * we show show 1 piece of info, the name maybe later more ?
 * bit KISS first
 *
 * everything is needed as attribute, in the constructor and as a getter ...
 */

public class ItemView {

	private TextView textvName;

	public ItemView(TextView textvName) {
		this.textvName = textvName;
	}

	//too small for regions really
	public TextView getTextvName() {
		return textvName;
	}
}
