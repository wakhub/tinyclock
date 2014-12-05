package com.github.wakhub.tinyclock;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

public class TinyClockAppWidget extends AppWidgetProvider {

    private static final String TAG = TinyClockAppWidget.class.getSimpleName();

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(TAG, String.format("onUpdate: %s", appWidgetIds));
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

    /*
    private void setLanguage(Context context, Locale locale) {
        Configuration conf = context.getResources().getConfiguration();
        conf.setLocale(locale);
        context.getResources().updateConfiguration(conf, context.getResources().getDisplayMetrics());
    }
    */

    private void updateStyle(Context context, AppWidgetManager appWidgetManager, Bundle options, int appWidgetId) {
        Resources res = context.getResources();

        Settings settings = new Settings(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);

        int minSize = res.getDimensionPixelSize(R.dimen.widget_size_min);

        float minWidth = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH, minSize);
        float maxHeight = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT, minSize);
        float density = context.getResources().getDisplayMetrics().density;
        int width = Math.round(minWidth * density);
        int height = Math.round(maxHeight * density);
        remoteViews.setImageViewBitmap(R.id.imageView, settings.getBackgroundBitmap(width, height));

        int textColor = settings.getTextColor();
        remoteViews.setTextColor(R.id.dateText, textColor);
        remoteViews.setTextColor(R.id.timeText, textColor);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }
}


