package com.funkygeneral.fahrzeugchecker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.funkygeneral.fahrzeugchecker.FahrzeugAdder.AddNewFahrzeug;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class FahrzeugeGetter extends ActionBarActivity {

	private ProgressDialog pDialog;

	JSONParser jParser = new JSONParser();
	JSONObject json;
	private static final int STERN_FARBE_KEINE = 0;
	private static final int STERN_FARBE_SILBER = 1;
	private static final int STERN_FARBE_GOLD = 2;
	private static final int STERN_FARBE_BRONZE = 3;
	private static final int STERN_FARBE_PINK = 4;

	private static final String url_get_fahrzeug = "http://10.0.2.2/gruner-jahr/datenbank/get_fahrzeug.php";

	private static final String ANZAHL_REIFEN = "anzahl_Reifen";
	private static final String FARBE_MERCEDES_STERN = "farbe_mercedes_stern";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_FAHRZEUGE = "fahrzeuge";

	private TextView reifenIndicator;
	private CheckBox editReifenButton;

	private TextView extrasIndicator;
	private CheckBox editExtrasButton;

	private Button getButton;
	
	private ListView fahrzeugListView;

	private ViewGroup reifenContainerView, extraContainerView, reifenView, extraView;

	private Mercedes mercedes;
	private ArrayList<Mercedes> mercedesList = new ArrayList<Mercedes>();
	private FahrzeugAdapter fahrzeugAdapter;

	String TAG = "FahrzeugeGetter";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fahrzeuge_getter);

		reifenContainerView = (ViewGroup) findViewById(R.id.reifenContainer);
		extraContainerView = (ViewGroup) findViewById(R.id.extrasContainer);

		mercedes = new Mercedes();
		mercedes.anzahl_Reifen = "ALLE";
		mercedes.farbe_mercedes_stern = "KEINE";
		
		fahrzeugListView = (ListView) findViewById(R.id.fahrzeugListView);
		fahrzeugAdapter = new FahrzeugAdapter(FahrzeugeGetter.this, mercedesList);
		fahrzeugListView.setAdapter(fahrzeugAdapter);

		reifenIndicator = (TextView) findViewById(R.id.reifenIndicator);
		editReifenButton = (CheckBox) findViewById(R.id.editReifenButton);
		editReifenButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked) {
					String reifenString = "1";
					mercedes.anzahl_Reifen = reifenString;
					reifenIndicator.setText(reifenString);
					showReifenView();
				} else {
					String reifenString = "ALLE";
					mercedes.anzahl_Reifen = reifenString;
					reifenIndicator.setText(reifenString);
					reifenContainerView.removeView(reifenView);
				}

			}
		});

		extrasIndicator = (TextView) findViewById(R.id.extrasIndicator);
		editExtrasButton = (CheckBox) findViewById(R.id.editExtrasButton);
		editExtrasButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked) {
					String sternFarbenString = "SILBER";
					mercedes.farbe_mercedes_stern = sternFarbenString;
					extrasIndicator.setText(sternFarbenString);
					showSternView();
				} else {
					String sternFarbenString = "KEINE";
					mercedes.farbe_mercedes_stern = sternFarbenString;
					extrasIndicator.setText(sternFarbenString);
					extraContainerView.removeView(extraView);
				}

			}
		});

		getButton = (Button) findViewById(R.id.getButton);
		getButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new GetFahrzeuge().execute();
			}

		});

	}

	public void showReifenView() {
		reifenView = (ViewGroup) LayoutInflater.from(this).inflate(
				R.layout.reifen_buttons, reifenContainerView, false);

		final RadioButton reifenRadioButton1 = (RadioButton) reifenView.findViewById(R.id.reifenRadioButton1);
		final RadioButton reifenRadioButton2 = (RadioButton) reifenView.findViewById(R.id.reifenRadioButton2);
		final RadioButton reifenRadioButton3 = (RadioButton) reifenView.findViewById(R.id.reifenRadioButton3);
		final RadioButton reifenRadioButton4 = (RadioButton) reifenView.findViewById(R.id.reifenRadioButton4);

		// REIFEN Check Change Listener
		reifenRadioButton1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked) {
					String reifenString = "1";
					mercedes.anzahl_Reifen = reifenString;
					reifenIndicator.setText(reifenString);
					reifenRadioButton2.setChecked(false);
					reifenRadioButton3.setChecked(false);
					reifenRadioButton4.setChecked(false);
				} 
			}
		});

		reifenRadioButton2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked) {
					String reifenString = "2";
					mercedes.anzahl_Reifen = reifenString;
					reifenIndicator.setText(reifenString);
					reifenRadioButton1.setChecked(false);
					reifenRadioButton3.setChecked(false);
					reifenRadioButton4.setChecked(false);
				} 
			}
		});

		reifenRadioButton3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked) {
					String reifenString = "3";
					mercedes.anzahl_Reifen = reifenString;
					reifenIndicator.setText(reifenString);
					reifenRadioButton1.setChecked(false);
					reifenRadioButton2.setChecked(false);
					reifenRadioButton4.setChecked(false);
				} 
			}
		});

		reifenRadioButton4.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked) {
					String reifenString = "4";
					mercedes.anzahl_Reifen = reifenString;
					reifenIndicator.setText(reifenString);
					reifenRadioButton1.setChecked(false);
					reifenRadioButton2.setChecked(false);
					reifenRadioButton3.setChecked(false);
				} 
			}
		});

		reifenContainerView.addView(reifenView, 0);
	}

	public void showSternView() {
		extraView = (ViewGroup) LayoutInflater.from(this).inflate(
				R.layout.stern_buttons, extraContainerView, false);

		final RadioButton silberRadioButton = (RadioButton) extraView.findViewById(R.id.silberRadioButton);
		final RadioButton goldRadioButton = (RadioButton) extraView.findViewById(R.id.goldRadioButton);
		final RadioButton bronzeRadioButton = (RadioButton) extraView.findViewById(R.id.bronzeRadioButton);
		final RadioButton pinkRadioButton = (RadioButton) extraView.findViewById(R.id.pinkRadioButton);

		silberRadioButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked) {
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
					String sternFarbenString = "PINK";
					mercedes.farbe_mercedes_stern = sternFarbenString;
					silberRadioButton.setChecked(false);
					goldRadioButton.setChecked(false);
					bronzeRadioButton.setChecked(false);
					extrasIndicator.setText(sternFarbenString);
				} 
			}
		});

		extraContainerView.addView(extraView, 0);
	}

	class GetFahrzeuge extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(FahrzeugeGetter.this);
			pDialog.setMessage("Suche nach Fahrzeugen...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			if(!mercedes.anzahl_Reifen.equals("ALLE")) {
				params.add(new BasicNameValuePair(ANZAHL_REIFEN, mercedes.anzahl_Reifen));
			}
			if(!mercedes.farbe_mercedes_stern.equals("KEINE")) {
				params.add(new BasicNameValuePair(FARBE_MERCEDES_STERN, mercedes.farbe_mercedes_stern));
			}

			try {
				json = jParser.makeHttpRequest(url_get_fahrzeug, "GET", params);
			} catch (IOException e) {
				Log.d(TAG, "Getting IOException");
				e.printStackTrace();
				return null;
			}
			
			try {
				Log.d(TAG, "Setting mercedesList");
				mercedesList.clear();
				
				Log.d(TAG, json.toString());
				
				JSONArray fahrzeuge = json.getJSONArray(TAG_FAHRZEUGE);
				for(int i = 0; i < fahrzeuge.length(); i++) {
					JSONObject fahrzeug = fahrzeuge.getJSONObject(i);
					
					Mercedes tempMercedes = new Mercedes();
					tempMercedes.anzahl_Reifen = fahrzeug.getString(ANZAHL_REIFEN);
					if(fahrzeug.has(FARBE_MERCEDES_STERN)) {
						tempMercedes.farbe_mercedes_stern = fahrzeug.getString(FARBE_MERCEDES_STERN);
					}
					mercedesList.add(tempMercedes);
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
					fahrzeugAdapter.notifyDataSetChanged();
				}
			});
		}
	}
}
