package com.github.wakhub.tinyclock;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;

/**
 * Created by wak on 11/8/14.
 */
public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        findViewById(R.id.openClockButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager packageManager = getPackageManager();
                Intent clockIntent = new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER);

                // http://stackoverflow.com/questions/3590955/intent-to-launch-the-clock-application-on-android
                String clockApps[][] = {
                        {"com.htc.android.worldclock", "com.htc.android.worldclock.WorldClockTabControl"},
                        {"com.android.deskclock", "com.android.deskclock.AlarmClock"},
                        {"com.google.android.deskclock", "com.android.deskclock.DeskClock"},
                        {"com.motorola.blur.alarmclock", "com.motorola.blur.alarmclock.AlarmClock"},
                        {"com.sec.android.app.clockpackage", "com.sec.android.app.clockpackage.ClockPackage"}
                };
                boolean foundClockApp = false;

                for (int i = 0; i < clockApps.length; i++) {
                    String packageName = clockApps[i][0];
                    String className = clockApps[i][1];
                    try {
                        ComponentName cn = new ComponentName(packageName, className);
                        packageManager.getActivityInfo(cn, PackageManager.GET_META_DATA);
                        clockIntent.setComponent(cn);
                        foundClockApp = true;
                    } catch (PackageManager.NameNotFoundException e) {
                    }
                }

                if (foundClockApp) {
                    startActivity(clockIntent);
                } else {
                    try {
                        startActivity(new Intent(AlarmClock.ACTION_SHOW_ALARMS));
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(AlarmClock.ACTION_SET_ALARM));
                    }
                }
                finish();
            }
        });

        findViewById(R.id.openDataAndTimeSettingsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS));
                finish();
            }
        });

        findViewById(R.id.closeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
