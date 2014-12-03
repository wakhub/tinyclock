package com.github.wakhub.tinyclock;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.RemoteViews;

public class TinyClockAppWidget extends AppWidgetProvider {

    private static final String TAG = TinyClockAppWidget.class.getSimpleName();

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);

        Intent intent = new Intent(context, SettingsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.root, pendingIntent);
        for (int id : appWidgetIds) {
            Bundle options = appWidgetManager.getAppWidgetOptions(id);
            updateStyle(context, appWidgetManager, options, id);
        }
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context,
                                          AppWidgetManager appWidgetManager,
                                          int appWidgetId,
                                          Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);

        updateStyle(context, appWidgetManager, newOptions, appWidgetId);
    }

    private void updateStyle(Context context, AppWidgetManager appWidgetManager, Bundle options, int appWidgetId) {
        Resources res = context.getResources();
        int minSize = res.getDimensionPixelSize(R.dimen.widget_size_min);

        int orientation = context.getResources().getConfiguration().orientation;
        int minWidth = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH, minSize);
        int maxWidth = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH, minSize);
        int minHeight = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT, minSize);
        int maxHeight = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT, minHeight);
        int width;
        int height;
        /*
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            width = maxWidth;
            height = minHeight;
        } else {
        */
        width = minWidth;
        height = maxHeight;

        Settings settings = new Settings(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        remoteViews.setImageViewBitmap(R.id.imageView, settings.getBackgroundBitmap(width, height));
        int textColor=  settings.getTextColor();
        remoteViews.setTextColor(R.id.dateText, textColor);
        remoteViews.setTextColor(R.id.timeText, textColor);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }
}


