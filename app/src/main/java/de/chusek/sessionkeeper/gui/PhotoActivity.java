package de.chusek.sessionkeeper.gui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import de.chusek.sessionkeeper.R;

public class PhotoActivity extends AppCompatActivity implements View.OnClickListener{

	//no listner show i can transfer ans reimplement from the other stuff
	// constants for stlye and avoiding magic values
	private static final String TAG = PhotoActivity.class.getSimpleName();
	private static final int REQUEST_CODE_IMAGE_CAPTURE       = 1;
	private static final int REQUEST_CODE_PERMISSION_CAMERA   = 2;
	//no saving
	//	private static final int REQUEST_CODE_PERMISSION_W_SDCARD = 3;
	private static final String SYS_EXTRA_KEY = "data";

	//alias
	private final int PERMISSIBLE = PackageManager.PERMISSION_GRANTED;
	private final int REQ_CODE_P = 1;
	private final int REQ_CODE_C = 2;
	private final String CAM_PERMISSION = Manifest.permission.CAMERA;

	//
	private Button btnTakePhoto;
	private ImageView imgvPic;

	//region constructor
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_activity_layout);

		btnTakePhoto = (Button) findViewById(R.id.btnTakePhoto);
		imgvPic = (ImageView) findViewById(R.id.imageView);

		btnTakePhoto.setOnClickListener(this);
	}
	//endregion

	//region onClick
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnTakePhoto) {
			takeThatPhotoNow();
		} else {
			Toast.makeText(this, "on Click Oooopsie", Toast.LENGTH_SHORT).show();
		}
	}

	private void takeThatPhotoNow() {
		Log.d(TAG, "1111111111111111111111");
		//guard 1 permission
		if (ActivityCompat.checkSelfPermission(this,CAM_PERMISSION) != PERMISSIBLE) {
			Log.d(TAG, "+++++++++++++++++++++++++++++++++");

			reqPermissionCam();
			return;
		}
		Log.d(TAG, "22222222222222222222222");
		//guard 2 cam on phone
		PackageManager pm = getPackageManager();
		boolean camExist = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
		if(!camExist) {
			txtToast("no Cam u dork!");
			return;
		}
		//make Intent
		Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		//guard 3 cam app exists
		boolean camApp = takePic.resolveActivity(pm) != null;
		if (!camApp) {
			txtToast("no cam App u sap!");
			return;
		}
		//all guards passed: proceed
		startActivityForResult(takePic, REQ_CODE_C);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQ_CODE_C && resultCode == RESULT_OK) {
			Log.d(TAG,"i get here"); // this too fast for Toast?
			Bundle extras    = data.getExtras();
			Bitmap imgBitmap = (Bitmap) extras.get(SYS_EXTRA_KEY);
			//Thumbnail anzeigen
			imgvPic.setImageBitmap(imgBitmap);
		} else {
			Log.d(TAG,"camActRest bad bad bad");
		}
	}


	private void reqPermissionCam() {
		String[] detourDef = {CAM_PERMISSION};
		requestPermissions(detourDef, REQ_CODE_P);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		Log.d(TAG, "onRequestPermissionsResult");
		if ( (requestCode==REQ_CODE_P) && (grantResults[0]==PERMISSIBLE) ) {
			takeThatPhotoNow();
		} else {
			txtToast("bad stuff in permRqRes");
		}
	}
	//endregion

	//region helper
	private  void txtToast(String txt) {
		Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
	}
	//endregion
}
