package com.funkygeneral.fahrzeugchecker;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FahrzeugAdapter extends BaseAdapter {

	private static final int[][] IMAGE_ARRAY = new int[][] {
		{R.drawable.einrad, R.drawable.einrad_silber, R.drawable.einrad_gold, R.drawable.einrad_bronze, R.drawable.einrad_pink},
		{R.drawable.fahrrad, R.drawable.fahrrad_silber, R.drawable.fahrrad_gold, R.drawable.fahrrad_bronze, R.drawable.fahrrad_pink},
		{R.drawable.dreirad, R.drawable.dreirad_silber, R.drawable.dreirad_gold, R.drawable.dreirad_bronze, R.drawable.dreirad_pink},
		{R.drawable.isetta, R.drawable.isetta_silber, R.drawable.isetta_gold, R.drawable.isetta_bronze, R.drawable.isetta_pink},
	};

	private LayoutInflater mInflater;

	private List<Mercedes> mercedesItems = new ArrayList<Mercedes>();

	public FahrzeugAdapter(Context context, List<Mercedes> items) {
		mInflater = LayoutInflater.from(context);
		this.mercedesItems = items;
	}

	public int getCount() {
		return mercedesItems.size();
	}

	public Mercedes getItem(int position) {
		return mercedesItems.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		Mercedes mercedes = mercedesItems.get(position);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.fahrzeug_item, null);
			holder = new ViewHolder();
			holder.fahrzeugImage = (ImageView) convertView.findViewById(R.id.fahrzeugImage);
			holder.reifenIndicator = (TextView) convertView.findViewById(R.id.reifenIndicator);
			holder.farbeIndicator = (TextView) convertView.findViewById(R.id.farbeIndicator);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		String anzahl_Reifen_string = mercedes.anzahl_Reifen;
		int anzahl_Reifen = Integer.parseInt(anzahl_Reifen_string) - 1;
		String farbe_mercedes_stern = mercedes.farbe_mercedes_stern;
		int sternNummer = 0;
		if(farbe_mercedes_stern.equals("SILBER")) {
			sternNummer = 1;
		} else if(farbe_mercedes_stern.equals("GOLD")) {
			sternNummer = 2;
		} else if(farbe_mercedes_stern.equals("BRONZE")) {
			sternNummer = 3;
		} else if(farbe_mercedes_stern.equals("PINK")) {
			sternNummer = 4;
		}

		holder.fahrzeugImage.setImageResource(IMAGE_ARRAY[anzahl_Reifen][sternNummer]);
		//holder.fahrzeugImage.setImageResource(IMAGE_ARRAY[3][4]);
		holder.reifenIndicator.setText(mercedes.anzahl_Reifen);
		holder.farbeIndicator.setText(mercedes.farbe_mercedes_stern);

		return convertView;
	}

	static class ViewHolder {
		ImageView fahrzeugImage;
		TextView reifenIndicator;
		TextView farbeIndicator;
	}

}
