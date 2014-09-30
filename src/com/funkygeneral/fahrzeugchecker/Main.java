package com.funkygeneral.fahrzeugchecker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends ActionBarActivity {

	private Button setButton;
	private Button getButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		
		setButton = (Button) findViewById(R.id.setButton);
		setButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), FahrzeugAdder.class);
				startActivity(intent);
			}
			
		});
		
		
		getButton = (Button) findViewById(R.id.getButton);
		getButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), FahrzeugeGetter.class);
				startActivity(intent);
			}
			
		});
		
	}
}
