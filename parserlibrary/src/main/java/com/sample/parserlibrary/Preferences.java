package com.sample.parserlibrary;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import java.util.HashSet;
import java.util.Set;

public class Preferences {
    private static Activity activity = null;
    public static float mTextScaleFactor = 1;
    public static int mBackgroundColorOption = 1;
    public static int mTextColorHeader = Color.BLACK;
    public static int mTextColorNormal = Color.parseColor("#444444");
    public static boolean mAutoDownloadTutorial = false;
    public static boolean mScreenAlwaysOn = true;
    public static int mSaveImageQuality = 100;
    public static boolean mNightModeDisplay = false;
    public static int mContinueFromSubjectIndex = -1;
    public static String mContinueFromTopicURL = "";
    public static boolean mNarratorDisplay = true;
    public static float mNarratorSpeechRate = 1;
    public static float mNarratorPitch = 1;
    public static String mNarratorVoiceCode = "";

    public static void initializePreferences(Activity a) {
        activity = a;
        obtainContinuePreferences();
        obtainSettingsPreferences();
    }

    private static void obtainSettingsPreferences() {
        mAutoDownloadTutorial = activity.getPreferences(Context.MODE_PRIVATE)
                .getBoolean("SHARED_PREF_AUTO_DOWNLOAD", false);
        mTextScaleFactor = activity.getPreferences(Context.MODE_PRIVATE)
                .getFloat("SHARED_PREF_TEXT_SCALE_FACTOR", 1);
        mBackgroundColorOption = activity.getPreferences(Context.MODE_PRIVATE)
                .getInt("SHARED_PREF_TUTORIAL_BG_COLOR_OPTION", 1);
        mNightModeDisplay = activity.getPreferences(Context.MODE_PRIVATE)
                .getBoolean("SHARED_PREF_NIGHT_MODE_DISPLAY", false);
        mScreenAlwaysOn = activity.getPreferences(Context.MODE_PRIVATE)
                .getBoolean("SHARED_PREF_SCREEN_ALWAYS_ON", true);
        mSaveImageQuality = activity.getPreferences(Context.MODE_PRIVATE)
                .getInt("SHARED_PREF_SAVE_IMAGE_QUALITY", 100);
        mNarratorDisplay = activity.getPreferences(Context.MODE_PRIVATE)
                .getBoolean("SHARED_PREF_NARRATOR_DISPLAY", true);
        mNarratorSpeechRate = activity.getPreferences(Context.MODE_PRIVATE)
                .getFloat("SHARED_PREF_NARRATOR_SPEECH_RATE", 1);
        mNarratorPitch = activity.getPreferences(Context.MODE_PRIVATE)
                .getFloat("SHARED_PREF_NARRATOR_PITCH", 1);
        mNarratorVoiceCode = activity.getPreferences(Context.MODE_PRIVATE)
                .getString("SHARED_PREF_NARRATOR_VOICE_CODE", "");
    }

    public static void resetSettingsPreferences() {
        mAutoDownloadTutorial = false;
        mTextScaleFactor = 1;
        mBackgroundColorOption = 1;
        mNightModeDisplay = false;
        mScreenAlwaysOn = true;
        mSaveImageQuality = 100;
        mNarratorDisplay = true;
        mNarratorSpeechRate = 1;
        mNarratorPitch = 1;
        mNarratorVoiceCode = "";
        SharedPreferences mSharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor mSharedPreferencesEditor = mSharedPreferences.edit();
        mSharedPreferencesEditor.putBoolean("SHARED_PREF_AUTO_DOWNLOAD", false);
        mSharedPreferencesEditor.putFloat("SHARED_PREF_TEXT_SCALE_FACTOR", 1);
        mSharedPreferencesEditor.putInt("SHARED_PREF_TUTORIAL_BG_COLOR_OPTION", 1);
        mSharedPreferencesEditor.putBoolean("SHARED_PREF_NIGHT_MODE_DISPLAY", false);
        mSharedPreferencesEditor.putBoolean("SHARED_PREF_SCREEN_ALWAYS_ON", true);
        mSharedPreferencesEditor.putInt("SHARED_PREF_SAVE_IMAGE_QUALITY", 100);
        mSharedPreferencesEditor.putBoolean("SHARED_PREF_NARRATOR_DISPLAY", true);
        mSharedPreferencesEditor.putFloat("SHARED_PREF_NARRATOR_SPEECH_RATE", 1);
        mSharedPreferencesEditor.putFloat("SHARED_PREF_NARRATOR_PITCH", 1);
        mSharedPreferencesEditor.putString("SHARED_PREF_NARRATOR_VOICE_CODE", "");
        mSharedPreferencesEditor.apply();
    }

    public static void saveSettingsPreferences() {
        SharedPreferences mSharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor mSharedPreferencesEditor = mSharedPreferences.edit();
        mSharedPreferencesEditor.putBoolean("SHARED_PREF_AUTO_DOWNLOAD", mAutoDownloadTutorial);
        mSharedPreferencesEditor.putFloat("SHARED_PREF_TEXT_SCALE_FACTOR", mTextScaleFactor);
        mSharedPreferencesEditor.putInt("SHARED_PREF_TUTORIAL_BG_COLOR_OPTION",
                mBackgroundColorOption);
        mSharedPreferencesEditor.putBoolean("SHARED_PREF_NIGHT_MODE_DISPLAY", mNightModeDisplay);
        mSharedPreferencesEditor.putBoolean("SHARED_PREF_SCREEN_ALWAYS_ON", mScreenAlwaysOn);
        mSharedPreferencesEditor.putInt("SHARED_PREF_SAVE_IMAGE_QUALITY", mSaveImageQuality);
        mSharedPreferencesEditor.putBoolean("SHARED_PREF_NARRATOR_DISPLAY", mNarratorDisplay);
        mSharedPreferencesEditor.putFloat("SHARED_PREF_NARRATOR_SPEECH_RATE", mNarratorSpeechRate);
        mSharedPreferencesEditor.putFloat("SHARED_PREF_NARRATOR_PITCH", mNarratorPitch);
        mSharedPreferencesEditor.putString("SHARED_PREF_NARRATOR_VOICE_CODE", mNarratorVoiceCode);
        mSharedPreferencesEditor.apply();
    }

    private static void obtainContinuePreferences() {
        mContinueFromSubjectIndex = activity.getPreferences(Context.MODE_PRIVATE)
                .getInt("SHARED_PREF_CONTINUE_FROM_SUBJECT_INDEX", -1);
        mContinueFromTopicURL = activity.getPreferences(Context.MODE_PRIVATE)
                .getString("SHARED_PREF_CONTINUE_FROM_TOPIC_URL","");
    }

    public static void resetContinuePreferences() {
        mContinueFromSubjectIndex = -1;
        mContinueFromTopicURL = "";
        if (activity != null) {
            SharedPreferences mSharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor mSharedPreferencesEditor = mSharedPreferences.edit();
            mSharedPreferencesEditor.putInt("SHARED_PREF_CONTINUE_FROM_SUBJECT_INDEX", -1);
            mSharedPreferencesEditor.putString("SHARED_PREF_CONTINUE_FROM_TOPIC_URL", "");
            mSharedPreferencesEditor.apply();
        }
    }

    public static void saveContinuePreferences(int subjectIndex, String topicURL) {
        if (activity != null) {
            SharedPreferences mSharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor mSharedPreferencesEditor = mSharedPreferences.edit();
            mSharedPreferencesEditor.putInt("SHARED_PREF_CONTINUE_FROM_SUBJECT_INDEX",
                    subjectIndex);
            mSharedPreferencesEditor.putString("SHARED_PREF_CONTINUE_FROM_TOPIC_URL", topicURL);
            mSharedPreferencesEditor.apply();
        }
    }

    private static Set<String> obtainTestProgressPreferences() {
        return activity.getPreferences(Context.MODE_PRIVATE)
                .getStringSet("SHARED_PREF_COMPLETED_TESTS_URLS", new HashSet<String>());
    }

    public static void addToTestProgressPreferences(String testURL) {
        Set<String> set = obtainTestProgressPreferences();
        SharedPreferences mSharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor mSharedPreferencesEditor = mSharedPreferences.edit();
        set.add(testURL);
        mSharedPreferencesEditor.putStringSet("SHARED_PREF_COMPLETED_TESTS_URLS", set);
        mSharedPreferencesEditor.apply();
    }

    public static boolean isTestCompletedInPreferences(String testURL) {
        Set<String> set = obtainTestProgressPreferences();
        return set.contains(testURL);
    }
}
