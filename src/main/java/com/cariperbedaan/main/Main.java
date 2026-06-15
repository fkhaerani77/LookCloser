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
        frame.setSize(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); // tengah layar

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