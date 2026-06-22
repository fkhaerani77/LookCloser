package com.cariperbedaan.utils;

import java.io.*;
import java.util.prefs.Preferences;

public class SaveManager {

    private static SaveManager instance;
    private static final String KEY_UNLOCKED = "unlocked_level";
    private Preferences prefs;

    private SaveManager() {
        prefs = Preferences.userNodeForPackage(SaveManager.class);
    }

    public static SaveManager getInstance() {
        if (instance == null) instance = new SaveManager();
        return instance;
    }

    // Simpan level yang sudah di-unlock
    public void setUnlockedLevel(int level) {
        int current = getUnlockedLevel();
        if (level > current) {
            prefs.putInt(KEY_UNLOCKED, level);
        }
    }

    // Ambil level terakhir yang di-unlock
    public int getUnlockedLevel() {
        return prefs.getInt(KEY_UNLOCKED, 1); // default level 1
    }

    // Reset semua progress
    public void resetProgress() {
        prefs.putInt(KEY_UNLOCKED, 1);
    }
}