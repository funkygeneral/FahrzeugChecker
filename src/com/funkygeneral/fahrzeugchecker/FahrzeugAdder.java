package com.funkygeneral.fahrzeugchecker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class FahrzeugAdder extends ActionBarActivity {

	JSONParser jParser = new JSONParser();
	JSONObject json;
	private static final int STERN_FARBE_KEINE = 0;
	private static final int STERN_FARBE_SILBER = 1;
	private static final int STERN_FARBE_GOLD = 2;
	private static final int STERN_FARBE_BRONZE = 3;
	private static final int STERN_FARBE_PINK = 4;

	private static final String url_create_fahrzeug = "http://10.0.2.2/gruner-jahr/datenbank/create_fahrzeug.php";

	private static final String ANZAHL_REIFEN = "anzahl_Reifen";
	private static final String FARBE_MERCEDES_STERN = "farbe_mercedes_stern";
	private static final String TAG_SUCCESS = "success";

	private static final int[][] IMAGE_ARRAY = new int[][] {
		{R.drawable.einrad, R.drawable.einrad_silber, R.drawable.einrad_gold, R.drawable.einrad_bronze, R.drawable.einrad_pink},
		{R.drawable.fahrrad, R.drawable.fahrrad_silber, R.drawable.fahrrad_gold, R.drawable.fahrrad_bronze, R.drawable.fahrrad_pink},
		{R.drawable.dreirad, R.drawable.dreirad_silber, R.drawable.dreirad_gold, R.drawable.dreirad_bronze, R.drawable.dreirad_pink},
		{R.drawable.isetta, R.drawable.isetta_silber, R.drawable.isetta_gold, R.drawable.isetta_bronze, R.drawable.isetta_pink},
	};

	String TAG = "FahrzeugAdder";

	private ProgressDialog pDialog;

	private TextView reifenIndicator;
	private RadioButton reifenRadioButton1;
	private RadioButton reifenRadioButton2;
	private RadioButton reifenRadioButton3;
	private RadioButton reifenRadioButton4;

	private TextView extrasIndicator;
	private CheckBox editExtrasButton;

	private ImageView resultImage;
	private Button setButton;

	private ViewGroup mContainerView, sternView;

	private String toastString;

	private Mercedes mercedes;

	private int anzahlReifen, sternFarbe;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fahrzeug_adder);

		/*
		getActionBar().setCustomView(R.layout.actionbar);
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);
		 */

		mContainerView = (ViewGroup) findViewById(R.id.container);

		reifenIndicator = (TextView) findViewById(R.id.reifenIndicator);
		reifenRadioButton1 = (RadioButton) findViewById(R.id.reifenRadioButton1);
		reifenRadioButton2 = (RadioButton) findViewById(R.id.reifenRadioButton2);
		reifenRadioButton3 = (RadioButton) findViewById(R.id.reifenRadioButton3);
		reifenRadioButton4 = (RadioButton) findViewById(R.id.reifenRadioButton4);

		extrasIndicator = (TextView) findViewById(R.id.extrasIndicator);
		editExtrasButton = (CheckBox) findViewById(R.id.editExtrasButton);

		resultImage = (ImageView) findViewById(R.id.resultImage);

		setButton = (Button) findViewById(R.id.setButton);

		mercedes = new Mercedes();
		mercedes.anzahl_Reifen = "1";
		mercedes.farbe_mercedes_stern = "KEINE";

		anzahlReifen = 0;

		// REIFEN Check Change Listener
		reifenRadioButton1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked) {
					anzahlReifen = 0;
					String reifenString = "1";
					mercedes.anzahl_Reifen = reifenString;
					reifenIndicator.setText(reifenString);
					reifenRadioButton2.setChecked(false);
					reifenRadioButton3.setChecked(false);
					reifenRadioButton4.setChecked(false);
					setResultImage();
				} 
			}
		});

		reifenRadioButton2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked) {
					anzahlReifen = 1;
					String reifenString = "2";
					mercedes.anzahl_Reifen = reifenString;
					reifenIndicator.setText(reifenString);
					reifenRadioButton1.setChecked(false);
					reifenRadioButton3.setChecked(false);
					reifenRadioButton4.setChecked(false);
					setResultImage();
				} 
			}
		});

		reifenRadioButton3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked) {
					anzahlReifen = 2;
					String reifenString = "3";
					mercedes.anzahl_Reifen = reifenString;
					reifenIndicator.setText(reifenString);
					reifenRadioButton1.setChecked(false);
					reifenRadioButton2.setChecked(false);
					reifenRadioButton4.setChecked(false);
					setResultImage();
				} 
			}
		});

		reifenRadioButton4.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked) {
					anzahlReifen = 3;
					String reifenString = "4";
					mercedes.anzahl_Reifen = reifenString;
					reifenIndicator.setText(reifenString);
					reifenRadioButton1.setChecked(false);
					reifenRadioButton2.setChecked(false);
					reifenRadioButton3.setChecked(false);
					setResultImage();
				} 
			}
		});


		// EXTRAS Check Change Listener
		editExtrasButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				String extrasString = "SILBER";
				if(isChecked) {
					sternFarbe = STERN_FARBE_SILBER;
					addNewSternView();
				} else {
					extrasString = "KEINE";
					sternFarbe = STERN_FARBE_KEINE;
					mContainerView.removeView(sternView);
				}
				mercedes.farbe_mercedes_stern = extrasString;
				setResultImage();
				extrasIndicator.setText(extrasString);
			}
		});

		setButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new AddNewFahrzeug().execute();
			}

		});
	}

	private void setResultImage() {
		Log.d(TAG, "anzahlReifen: " + anzahlReifen);
		Log.d(TAG, "sternFarbe: " + sternFarbe);
		resultImage.setBackgroundResource(IMAGE_ARRAY[anzahlReifen][sternFarbe]);
	}

	public void addNewSternView() {
		sternView = (ViewGroup) LayoutInflater.from(this).inflate(
				R.layout.stern_buttons, mContainerView, false);

		final RadioButton silberRadioButton = (RadioButton) sternView.findViewById(R.id.silberRadioButton);
		final RadioButton goldRadioButton = (RadioButton) sternView.findViewById(R.id.goldRadioButton);
		final RadioButton bronzeRadioButton = (RadioButton) sternView.findViewById(R.id.bronzeRadioButton);
		final RadioButton pinkRadioButton = (RadioButton) sternView.findViewById(R.id.pinkRadioButton);

		silberRadioButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked) {
					sternFarbe = STERN_FARBE_SILBER;
					setResultImage();
					String sternFarbenString = "SILBER";
					mercedes.farbe_mercedes_stern = sternFarbenString;
					goldRadioButton.setChecked(false);
					bronzeRadioButton.setChecked(false);
					pinkRadioButton.setChecked(false);
					extrasIndicator.setText(sternFarbenString);
				} 
			}
		});

		goldRadioButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked) {
					sternFarbe = STERN_FARBE_GOLD;
					setResultImage();
					String sternFarbenString = "GOLD";
					mercedes.farbe_mercedes_stern = sternFarbenString;
					silberRadioButton.setChecked(false);
					bronzeRadioButton.setChecked(false);
					pinkRadioButton.setChecked(false);
					extrasIndicator.setText(sternFarbenString);
				} 
			}
		});

		bronzeRadioButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked) {
					sternFarbe = STERN_FARBE_BRONZE;
					setResultImage();
					String sternFarbenString = "BRONZE";
					mercedes.farbe_mercedes_stern = sternFarbenString;
					silberRadioButton.setChecked(false);
					goldRadioButton.setChecked(false);
					pinkRadioButton.setChecked(false);
					extrasIndicator.setText(sternFarbenString);
				} 
			}
		});

		pinkRadioButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked) {
					sternFarbe = STERN_FARBE_PINK;
					setResultImage();
					String sternFarbenString = "PINK";
					mercedes.farbe_mercedes_stern = sternFarbenString;
					silberRadioButton.setChecked(false);
					goldRadioButton.setChecked(false);
					bronzeRadioButton.setChecked(false);
					extrasIndicator.setText(sternFarbenString);
				} 
			}
		});

		mContainerView.addView(sternView, 0);
	}

	class AddNewFahrzeug extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(FahrzeugAdder.this);
			pDialog.setMessage("Füge Fahrzeug hinzu...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(ANZAHL_REIFEN, mercedes.anzahl_Reifen));
			if(!mercedes.farbe_mercedes_stern.equals("KEINE")) {
				params.add(new BasicNameValuePair(FARBE_MERCEDES_STERN, mercedes.farbe_mercedes_stern));
			} else {

			}
			try {
				json = jParser.makeHttpRequest(url_create_fahrzeug, "GET", params);
			} catch (IOException e) {
				e.printStackTrace();
			}

			Log.d(TAG, json.toString());

			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					toastString = "Neues Fahrzeug mit " + mercedes.anzahl_Reifen + " Reifen erzeugt.";
					if(!mercedes.farbe_mercedes_stern.equals("KEINE")) {
						toastString = "Neues Fahrzeug mit " + mercedes.anzahl_Reifen + " Reifen und einem " + mercedes.farbe_mercedes_stern + "em Stern erzeugt.";
					}
				} else {
					toastString = "Es konnte leider kein neues Fahrzeug erstellt werden.";
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			runOnUiThread(new Runnable() {
				public void run() {
					Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_LONG).show();
				}
			});
		}
	}
}
