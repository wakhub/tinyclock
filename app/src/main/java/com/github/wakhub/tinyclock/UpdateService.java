/**
 * Copyright (C) 2014 wak
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.wakhub.tinyclock;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Main operation for widget styling
 */
public class UpdateService extends Service {

    private static final String TAG = UpdateService.class.getSimpleName();

    private static final int REQUEST_CODE_UPDATE = 100;

    public static final String ACTION_UPDATE = "com.github.wakhub.tinyclock.ACTION_UPDATE";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return super.onStartCommand(null, flags, startId);
        }

        String action = intent.getAction();
        if (action == null) {
            return super.onStartCommand(intent, flags, startId);
        }

        Log.d(TAG, String.format("onStartCommand: %s", action));

        if (action.equals(ACTION_UPDATE)) {
            int[] allWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
            for (int widgetId : allWidgetIds) {
                updateWidget(widgetId);
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * Update widget contents
     *
     * @param widgetId ID to update
     */
    private void updateWidget(int widgetId) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());

        RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.widget);

        remoteViews.setOnClickPendingIntent(
                R.id.root,
                PendingIntent.getActivity(
                        this,
                        REQUEST_CODE_UPDATE,
                        new Intent(this, SettingsActivity.class),
                        PendingIntent.FLAG_UPDATE_CURRENT));

        Settings settings = new Settings(this);
        Bundle options = appWidgetManager.getAppWidgetOptions(widgetId);

        int[] backgroundSize = getBackgroundSize(options);
        remoteViews.setImageViewBitmap(
                R.id.imageView,
                settings.getBackgroundBitmap(backgroundSize[0], backgroundSize[1]));

        int textColor = settings.getTextColor();
        remoteViews.setTextColor(R.id.dateText, textColor);
        remoteViews.setTextColor(R.id.timeText, textColor);

        appWidgetManager.updateAppWidget(widgetId, remoteViews);
    }

    /**
     * Returns widget's width and height
     *
     * @param options widget option
     * @return array of width and height
     */
    private int[] getBackgroundSize(Bundle options) {
        Resources res = getResources();

        int minSize = res.getDimensionPixelSize(R.dimen.widget_size_min);

        float minWidth = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH, minSize);
        float maxHeight = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT, minSize);
        float density = res.getDisplayMetrics().density;
        int width = Math.round(minWidth * density);
        int height = Math.round(maxHeight * density);
        return new int[] {width, height};
    }
}


