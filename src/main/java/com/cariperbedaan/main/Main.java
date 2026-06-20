package com.cariperbedaan.main;

import com.cariperbedaan.config.GameConfig;
import com.cariperbedaan.ui.MainMenuPanel;

import javax.swing.*;
import java.awt.*;

public class Main {

    private JFrame frame;
    private JPanel cardContainer;
    private CardLayout cardLayout;

    public Main() {
        frame = new JFrame(GameConfig.GAME_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // --- PERUBAHAN UNTUK FULLSCREEN ---
        // 1. Hilangkan border, title bar, dan tombol close bawaan Windows
        frame.setUndecorated(true);

        // 2. Set ukuran frame langsung mengambil ukuran layar dari GameConfig yang baru
        frame.setSize(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);

        // 3. Maksimalkan window ke mode fullscreen penuh
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Sudah tidak perlu setResizable(false) atau setLocationRelativeTo(null)
        // karena frame sudah otomatis memenuhi seluruh layar dari ujung ke ujung.
        // ----------------------------------

        cardLayout    = new CardLayout();
        cardContainer = new JPanel(cardLayout);

        // Daftarkan semua panel
        MainMenuPanel mainMenu = new MainMenuPanel(this);
        cardContainer.add(mainMenu, "MAIN_MENU");

        frame.add(cardContainer);
        frame.setVisible(true);

        showPanel("MAIN_MENU");
    }

    // Navigasi ke panel tertentu
    public void showPanel(String panelName) {
        cardLayout.show(cardContainer, panelName);
    }

    // Tambah panel baru ke CardLayout
    public void addPanel(JPanel panel, String name) {
        cardContainer.add(panel, name);
    }

    // Getter frame (dipakai panel lain kalau perlu)
    public JFrame getFrame() {
        return frame;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}