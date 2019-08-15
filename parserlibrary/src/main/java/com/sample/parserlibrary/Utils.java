package com.sample.parserlibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Html;
import android.text.SpannableStringBuilder;

import org.json.JSONObject;

import java.io.File;

public class Utils {

    public static Typeface mApplicationTypeface = null;
    public static Typeface mTutorialTypeface = null;
    public static Typeface mCodeTypeface = null;
    public static boolean mPerformListAnimations = false;
    public static float mDpToPixelFactor = 0;
    public static JSONObject mJSONContent = null;
    public static Bitmap mCurrentPreviewImageBitmap = null;

    // Tutorial Page tag-wise font sizes
    public static float TEXT_SIZE_NORMAL = 16;
    public static float TEXT_SIZE_TAG_PRE = 11;
    public static float TEXT_SIZE_TAG_H1 = 32;
    public static float TEXT_SIZE_TAG_H2 = 26;
    public static float TEXT_SIZE_TAG_H3 = 26;
    public static float TEXT_SIZE_TAG_H4 = 24;
    public static float TEXT_SIZE_TAG_H5 = 16;
    public static float TEXT_SIZE_TAG_H6 = 14;

    // Test question font size
    public static float TEXT_SIZE_TEST_QUESTION = 20;

    // Pre tag colors
    public static final int COLOR_BACKGROUND_PRE_TAG = Color.parseColor("#1E2A37");
    public static final int COLOR_SECTION_COMMENT = Color.parseColor("#75715E");
    public static final int COLOR_SECTION_SINGLE_QUOTES = Color.parseColor("#E6DB74");
    public static final int COLOR_SECTION_DOUBLE_QUOTES = Color.parseColor("#E6DB74");
    public static final int COLOR_OPERATOR = Color.parseColor("#F92672");
    public static final int COLOR_DELIMITER = Color.parseColor("#F8F8F2");
    public static final int COLOR_RESERVED_KEYWORD = Color.parseColor("#F92672");
    public static final int COLOR_LITERAL_VALUE_KEYWORD = Color.parseColor("#A078ED");
    public static final int COLOR_NUMERIC_LITERAL = Color.parseColor("#A078ED");
    public static final int COLOR_MOST_USED_CLASSES = Color.parseColor("#5CC3D8");
    public static final int COLOR_NORMAL_PRE_TAG = Color.parseColor("#F8F8F2");
    public static final int COLOR_INCLUDE_STATEMENT = Color.parseColor("#DB9E97");
    public static final int COLOR_XML_TAG = Color.parseColor("#A078ED");

    public static boolean isDatabasePresent(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    public static int manipulateColor(int color, float factor) {
        // less than 1.0f to darken, more for lighten
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a, Math.min(r,255), Math.min(g,255), Math.min(b,255));
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo mNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isConnected();
            }
        }
        return false;
    }

    public static SpannableStringBuilder convertHtmlToNormal(String text) {
        if (text != null) {
            if (text.contains("<filter") || text.contains("<listener") || text.contains("<se")) {
                return new SpannableStringBuilder(text.trim());
            } else {
                return new SpannableStringBuilder(
                        Html.fromHtml(text
                                .replaceAll("<code>", "<font color=\"red\">")
                                .replaceAll("</code>", "</font>")));
            }
        }
        return null;
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }
}
