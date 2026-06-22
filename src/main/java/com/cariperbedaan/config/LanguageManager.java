package com.cariperbedaan.config;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class LanguageManager {

    public enum Language { EN, ID }

    private static LanguageManager instance;
    private Language current = Language.EN; // default English

    // Listener untuk update UI real-time
    private List<Runnable> listeners = new ArrayList<>();

    private static final Map<String, String[]> texts = new HashMap<>();

    static {
        // Format: key → [English, Indonesia]
        texts.put("setting.title",          new String[]{"S E T T I N G",       "P E N G A T U R A N"});
        texts.put("setting.music",          new String[]{"Music",            "Musik"});
        texts.put("setting.sfx",            new String[]{"Sound Effect",     "Efek Suara"});
        texts.put("setting.language",       new String[]{"Language",         "Bahasa"});
        texts.put("setting.back",           new String[]{"Back to Menu",       "Kembali ke Menu"});
        texts.put("setting.reset",          new String[]{"Reset Progress", "Mengatur Ulang Progres"});
        texts.put("setting.reset.confirm",  new String[]{"Reset all progress? Level will start from 1 again!", "Reset semua progres? Level akan mulai dari 1 lagi!"});
        texts.put("setting.reset.success",  new String[]{"Progress has been reset!", "Progres berhasil diatur ulang!"});
        texts.put("setting.on",             new String[]{"ON",                   "ON"});
        texts.put("setting.off",            new String[]{"OFF",                  "OFF"});

        texts.put("level.title",         new String[]{"SELECT LEVEL",         "PILIH LEVEL"});
        texts.put("level.locked",        new String[]{"Tap to play",          "Tap untuk main"});

        texts.put("game.hint",           new String[]{"Hint",                 "Petunjuk"});
        texts.put("game.back",           new String[]{"Back",               "Kembali"});
        texts.put("game.found",          new String[]{"Found!",               "Ditemukan!"});
        texts.put("game.complete",       new String[]{"Level Complete!",      "Level Selesai!"});
        texts.put("game.time",           new String[]{"Time",                 "Waktu"});

        texts.put("quit.message",        new String[]{"Are you sure want to quit?", "Yakin ingin keluar?"});
        texts.put("quit.title",          new String[]{"Quit Game",            "Keluar Game"});
    }

    private LanguageManager() {}

    public static LanguageManager getInstance() {
        if (instance == null) instance = new LanguageManager();
        return instance;
    }

    // Ambil teks sesuai bahasa aktif
    public String get(String key) {
        String[] val = texts.get(key);
        if (val == null) return key;
        return current == Language.EN ? val[0] : val[1];
    }

    // Ganti bahasa & notify semua listener
    public void setLanguage(Language lang) {
        this.current = lang;
        notifyListeners();
    }

    public Language getCurrent() { return current; }

    public boolean isEnglish() { return current == Language.EN; }

    // Register listener (panel yang perlu update real-time)
    public void addListener(Runnable r) { listeners.add(r); }
    public void removeListener(Runnable r) { listeners.remove(r); }

    private void notifyListeners() {
        for (Runnable r : listeners) r.run();
    }
}