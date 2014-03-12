package com.assignment1.ui;

import com.example.splashscreen.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HomeScreenActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_home_screen_activity);
		ListView homeScreenListView = (ListView) findViewById(R.id.home_screen_list);
		final String[] itemsArray = new String[20];
		for (int i = 0; i < 20; i++)
			itemsArray[i] = ("\tItem " + (i + 1));

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, itemsArray);
		homeScreenListView.setAdapter(adapter);
	}

}