package com.cariperbedaan.config;

import java.awt.Color;
import java.awt.Font;

public class GameConfig {

    // Window
    public static final String GAME_TITLE = "Cari Perbedaan";
    public static final int WINDOW_WIDTH  = 1024;
    public static final int WINDOW_HEIGHT = 700;

    // Total level
    public static final int TOTAL_LEVELS = 6;

    // Warna tema
    public static final Color COLOR_PRIMARY    = new Color(52, 152, 219);
    public static final Color COLOR_SECONDARY  = new Color(41, 128, 185);
    public static final Color COLOR_BACKGROUND = new Color(30, 39, 46);
    public static final Color COLOR_TEXT       = new Color(236, 240, 241);
    public static final Color COLOR_ACCENT     = new Color(231, 76, 60);
    public static final Color COLOR_SUCCESS    = new Color(46, 204, 113);
    public static final Color COLOR_LOCKED     = new Color(127, 140, 141);

    // Font
    public static final Font FONT_TITLE   = new Font("Arial", Font.BOLD, 48);
    public static final Font FONT_BUTTON  = new Font("Arial", Font.BOLD, 20);
    public static final Font FONT_NORMAL  = new Font("Arial", Font.PLAIN, 16);
    public static final Font FONT_SMALL   = new Font("Arial", Font.PLAIN, 13);
    public static final Font FONT_LEVEL   = new Font("Arial", Font.BOLD, 22);

    // Ukuran tombol menu
    public static final int BUTTON_WIDTH  = 250;
    public static final int BUTTON_HEIGHT = 55;

    // Gameplay
    public static final int HINT_LIMIT         = 3;
    public static final int TIME_PER_LEVEL      = 120; // detik
    public static final int DIFFERENCE_RADIUS   = 30;  // radius klik area

    // Audio
    public static final float DEFAULT_BGM_VOLUME = 0.7f;
    public static final float DEFAULT_SFX_VOLUME = 1.0f;

    // Save file
    public static final String SAVE_FILE = "save/progress.dat";
}