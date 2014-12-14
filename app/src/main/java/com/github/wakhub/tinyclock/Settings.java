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

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * SharedPreferences wrapper class
 *
 * Created by wak on 12/3/14.
 */
public class Settings {

    private static final String TAG = Settings.class.getSimpleName();

    private final Context context;

    private final SharedPreferences preferences;

    public Settings(Context context) {
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public int getTextColor() {
        int color = Color.BLACK;
        try {
            color = Color.parseColor(getTextColorString());
        } catch (IllegalArgumentException e) {
            onParseError(e);
        }
        return color;
    }

    public String getTextColorString() {
        Resources res = context.getResources();
        String colorString = preferences.getString(res.getString(R.string.text_color_default), "");
        try {
            String tmpColorString = preferences.getString(res.getString(R.string.pref_text_color_key), colorString);
            Color.parseColor(tmpColorString);
            colorString = tmpColorString;
        } catch (IllegalArgumentException e) {
            onParseError(e);
        }
        return colorString;
    }

    public Bitmap getBackgroundBitmap(int width, int height) {
        Resources res = context.getResources();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(getBackgroundColor());
        paint.setStyle(Paint.Style.FILL);

        canvas.drawARGB(0, 0, 0, 0);
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        int radius = res.getDimensionPixelSize(R.dimen.widget_corner);
        canvas.drawRoundRect(new RectF(rect), radius, radius, paint);

        canvas.drawBitmap(bitmap, rect, rect, paint);
        return bitmap;
    }

    public int getBackgroundColor() {
        int color = Color.WHITE;
        try {
            color = Color.parseColor(getBackgroundColorString());
        } catch (IllegalArgumentException e) {
            onParseError(e);
        }
        return color;
    }

    public String getBackgroundColorString() {
        Resources res = context.getResources();
        String colorString = preferences.getString(res.getString(R.string.background_color_default), "");
        try {
            String tmpColorString = preferences.getString(res.getString(R.string.pref_background_color_key), colorString);
            Color.parseColor(tmpColorString);
            colorString = tmpColorString;
        } catch (IllegalArgumentException e) {
            onParseError(e);
        }
        return colorString;
    }

    private void onParseError(IllegalArgumentException e) {
        Log.d(TAG, "Failed to parse color", e);
    }
}
