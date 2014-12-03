package com.github.wakhub.tinyclock;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.preference.PreferenceManager;

/**
 * Created by wak on 12/3/14.
 */
public class Settings {

    private final Context context;

    private final SharedPreferences preferences;

    public Settings(Context context) {
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public int getTextColor() {
        Resources res = context.getResources();
        int color = Color.WHITE;
        try {
            color = Color.parseColor(getTextColorString());
        } catch (IllegalArgumentException e) {
        }
        return color;
    }

    public String getTextColorString() {
        Resources res = context.getResources();
        String colorString = preferences.getString(res.getString(R.string.text_color_default), "white");
        try {
            String tmpColorString = preferences.getString(res.getString(R.string.pref_text_color_key), colorString);
            Color.parseColor(tmpColorString);
            colorString = tmpColorString;
        } catch (IllegalArgumentException e) {
        }
        return colorString;
    }

    public Bitmap getBackgroundBitmap(int width, int height) {
        Resources res = context.getResources();
        ShapeDrawable drawable = new ShapeDrawable();
        Paint paint = drawable.getPaint();
        paint.setColor(getBackgroundColor());
        paint.setPathEffect(new CornerPathEffect(res.getInteger(R.integer.widget_corner)));
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawRect(0, 0, width, height, paint);

        canvas.drawBitmap(bitmap, new Matrix(), null);
        return bitmap;
    }

    public int getBackgroundColor() {
        Resources res = context.getResources();
        int color = Color.BLACK;
        try {
            color = Color.parseColor(getBackgroundColorString());
        } catch (IllegalArgumentException e) {
        }
        return color;
    }

    public String getBackgroundColorString() {
        Resources res = context.getResources();
        String colorString = preferences.getString(res.getString(R.string.background_color_default), "black");
        try {
            String tmpColorString = preferences.getString(res.getString(R.string.pref_background_color_key), colorString);
            Color.parseColor(tmpColorString);
            colorString = tmpColorString;
        } catch (IllegalArgumentException e) {
        }
        return colorString;
    }
}
