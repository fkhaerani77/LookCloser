package com.cariperbedaan.main;

import com.cariperbedaan.config.GameConfig;
import com.cariperbedaan.ui.LevelSelectPanel;
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
        frame.setUndecorated(true);
        frame.setSize(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        cardLayout    = new CardLayout();
        cardContainer = new JPanel(cardLayout);

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

    // ← TAMBAH METHOD INI
    public void showLevelSelect() {
        LevelSelectPanel levelSelect = new LevelSelectPanel(this);
        cardContainer.add(levelSelect, "LEVEL_SELECT");
        cardLayout.show(cardContainer, "LEVEL_SELECT");
    }

    public JFrame getFrame() {
        return frame;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}