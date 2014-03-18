package com.assignment1.ui;

import java.util.Calendar;
import java.util.Date;
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
	Handler homeScreenHandler;
	long splashStartTime;
	static final String SPLASH_SAVE_START_TIME = "splashStartTime";
	static final long SPLASH_DELAY = 2000;

	@Override
	protected void onCreate(Bundle SaveInstance) {
		super.onCreate(SaveInstance);
		setContentView(R.layout.a_splash_screen_activity);
		currentTimeTextView = (TextView) findViewById(R.id.current_time_text);
		if (SaveInstance != null) {
			splashStartTime = SaveInstance.getLong(SPLASH_SAVE_START_TIME);
		}
	}

	@Override
	protected void onStart() {
		super.onStart();

		long currentTime = new Date().getTime();

		if (0 == splashStartTime) {
			splashStartTime = currentTime;
		}

		long splashDelayTime = SPLASH_DELAY - (currentTime - splashStartTime);

		if (splashDelayTime <= 0) {
			runHomeScreenActivity();
		} else if (splashDelayTime > SPLASH_DELAY) {
			finish();
		} else {
			homeScreenHandler = new Handler();// start handler here
			homeScreenHandler.postDelayed(splashTask, splashDelayTime);
		}

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

	private final Runnable splashTask = new Runnable() {
		@Override
		public void run() {
			runHomeScreenActivity();
		}
	};

	void runHomeScreenActivity() {
		final Intent homeScreenIntent;
		homeScreenIntent = new Intent(this, HomeScreenActivity.class);
		startActivity(homeScreenIntent);
		finish();
	}

	private String getCurrentTime() {
		return "Splash screen\n"
				+ String.format("%02d:%02d:%02d",
						Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
						Calendar.getInstance().get(Calendar.MINUTE), Calendar
								.getInstance().get(Calendar.SECOND));
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (homeScreenHandler != null) {
			homeScreenHandler.removeCallbacks(splashTask);
		}
		splashCurrTimer.cancel();
		splashCurrTimer.purge();
		splashCurrTimer = null;
	}

	@Override
	protected void onSaveInstanceState(Bundle SaveInstance) {
		super.onSaveInstanceState(SaveInstance);
		SaveInstance.putLong(SPLASH_SAVE_START_TIME, splashStartTime);
	}
}