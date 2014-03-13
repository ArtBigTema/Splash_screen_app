package com.assignment1.ui;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.example.splashscreen.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class SplashScreenActivity extends Activity {
	private Timer splashCurrTimer;
	private TextView currentTimeTextView;
	boolean isHomeScreenRun=false;

	@Override
	protected void onCreate(Bundle SaveInstance) {
		super.onCreate(SaveInstance);
		setContentView(R.layout.a_splash_screen_activity);
		currentTimeTextView = (TextView) findViewById(R.id.current_time_text);
	}

	@Override
	protected void onStart() {
		super.onStart();
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				runHomeScreenActivity();
			}
		}, 2000);
	splashCurrTimer = new Timer("DigitalClock");

		final Runnable updateTask = new Runnable() {
			public void run() {
				currentTimeTextView.setText(getCurrentTime());
			}
		};

		splashCurrTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(updateTask);

			}
		}, 0, 1000);

	}

	void runHomeScreenActivity() {
		final Intent homeScreenIntent;
		homeScreenIntent = new Intent(this, HomeScreenActivity.class);
		if (!isHomeScreenRun) {
			startActivity(homeScreenIntent);
			SplashScreenActivity.super.onStop();
			SplashScreenActivity.super.finish();
			SplashScreenActivity.super.onDestroy();
		}
	}

	private String getCurrentTime() {
		return "Splash screen\n"
				+ String.format("%02d:%02d:%02d",
						Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
						Calendar.getInstance().get(Calendar.MINUTE), Calendar
								.getInstance().get(Calendar.SECOND));
	}

	@Override
	public void onBackPressed() {
		SplashScreenActivity.super.onStop();
		SplashScreenActivity.super.onBackPressed();
	}

	@Override
	protected void onStop() {
		super.onStop();
		isHomeScreenRun=true;
		splashCurrTimer.cancel();
		splashCurrTimer.purge();
		splashCurrTimer = null;
	}

	@Override
	protected void onSaveInstanceState(Bundle SaveInstance) {
		super.onSaveInstanceState(SaveInstance);
		SaveInstance.putBoolean("IsHomeScreenRun", true);
	}

	@Override
	protected void onRestoreInstanceState(Bundle SaveInstance) {
		super.onRestoreInstanceState(SaveInstance);
		isHomeScreenRun = SaveInstance.getBoolean("IsHomeScreenRun");
	}
}