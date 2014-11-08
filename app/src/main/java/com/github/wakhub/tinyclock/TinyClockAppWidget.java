package com.github.wakhub.tinyclock;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class TinyClockAppWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int appWidgetId : appWidgetIds) {
            RemoteViews remoteView = new RemoteViews(context.getPackageName(),
                    R.layout.widget);

            Intent intent = new Intent(context, SettingsActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            remoteView.setOnClickPendingIntent(R.id.root, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, remoteView);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}


