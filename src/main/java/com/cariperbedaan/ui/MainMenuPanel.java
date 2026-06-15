package com.cariperbedaan.ui;

import com.cariperbedaan.config.GameConfig;
import com.cariperbedaan.main.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MainMenuPanel extends JPanel {

    private Main app;
    private BufferedImage bgImage;
    private BufferedImage btnPlayImg;
    private BufferedImage btnSettingImg;
    private BufferedImage btnCloseImg;

    public MainMenuPanel(Main app) {
        this.app = app;
        setLayout(null);
        setPreferredSize(new Dimension(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT));

        loadImages();
        initComponents();
    }

    private void loadImages() {
        try {
            bgImage      = ImageIO.read(getClass().getResourceAsStream("/images/ui/bg_menu.png"));
            btnPlayImg   = ImageIO.read(getClass().getResourceAsStream("/images/ui/bt_play.png"));
            btnSettingImg= ImageIO.read(getClass().getResourceAsStream("/images/ui/bt_setting.png"));
            btnCloseImg  = ImageIO.read(getClass().getResourceAsStream("/images/ui/bt_close.png"));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Gagal load gambar: " + e.getMessage());
        }
    }

    private void initComponents() {
        int W = GameConfig.WINDOW_WIDTH;
        int H = GameConfig.WINDOW_HEIGHT;

        // --- Tombol SETTING (kiri atas) ---
        ImageButton btnSetting = new ImageButton(btnSettingImg, 80, 80);
        btnSetting.setBounds(30, 20, 80, 80);
        btnSetting.addActionListener(e -> onSetting());
        add(btnSetting);

        // --- Tombol CLOSE / QUIT (kanan atas) ---
        ImageButton btnClose = new ImageButton(btnCloseImg, 80, 80);
        btnClose.setBounds(W - 110, 20, 80, 80);
        btnClose.addActionListener(e -> onQuit());
        add(btnClose);

        // --- Tombol PLAY (tengah bawah) ---
        // Sesuaikan ukuran & posisi dengan referensi gambar
        int playW = 180, playH = 180;
        ImageButton btnPlay = new ImageButton(btnPlayImg, playW, playH);
        btnPlay.setBounds((W - playW) / 2, H - 330, playW, playH);
        btnPlay.addActionListener(e -> onPlay());
        add(btnPlay);
    }

    // Custom button yang render dari BufferedImage

    // Background = bg_menu.png full stretch
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        if (bgImage != null) {
            g2.drawImage(bgImage, 0, 0, getWidth(), getHeight(), null);
        } else {
            // Fallback kalau gambar gagal load
            GradientPaint gp = new GradientPaint(0, 0, new Color(130, 80, 200),
                    getWidth(), getHeight(), new Color(80, 200, 220));
            g2.setPaint(gp);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private void onPlay() {
        LevelSelectPanel levelSelect = new LevelSelectPanel(app);
        app.addPanel(levelSelect, "LEVEL_SELECT");
        app.showPanel("LEVEL_SELECT");
    }

    private void onSetting() {
        SettingPanel setting = new SettingPanel(app);
        app.addPanel(setting, "SETTING");
        app.showPanel("SETTING");
    }

    private void onQuit() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Yakin ingin keluar dari game?",
                "Konfirmasi Keluar",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}