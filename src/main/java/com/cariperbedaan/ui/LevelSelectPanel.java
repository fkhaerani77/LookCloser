package com.cariperbedaan.ui;

import com.cariperbedaan.config.GameConfig;
import com.cariperbedaan.main.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class LevelSelectPanel extends JPanel {

    private Main app;
    private BufferedImage bgImage;
    private BufferedImage btnSettingImg;
    private BufferedImage btnCloseImg;
    private BufferedImage btnHomeImg;      // ← TAMBAH
    private BufferedImage[] thumbnails;

    private int unlockedLevel = 1;

    private static final Color CARD_COLOR        = new Color(235, 100, 180);
    private static final Color CARD_LOCKED_COLOR  = new Color(160, 160, 160);
    private static final Color CARD_SHADOW        = new Color(0, 0, 0, 60);
    private static final Color TEXT_COLOR         = Color.WHITE;
    private static final Font  LEVEL_FONT         = new Font("Arial", Font.BOLD, 22);

    public LevelSelectPanel(Main app) {
        this.app = app;
        setLayout(null);
        setPreferredSize(new Dimension(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT));
        loadImages();
        initComponents();
    }

    private void loadImages() {
        try {
            bgImage       = ImageIO.read(getClass().getResourceAsStream("/images/ui/bg_level.png"));
            btnSettingImg = ImageIO.read(getClass().getResourceAsStream("/images/ui/bt_setting.png"));
            btnCloseImg   = ImageIO.read(getClass().getResourceAsStream("/images/ui/bt_close.png"));
            btnHomeImg    = ImageIO.read(getClass().getResourceAsStream("/images/ui/bt_home.png")); // ← TAMBAH
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Gagal load UI image: " + e.getMessage());
        }

        thumbnails = new BufferedImage[GameConfig.TOTAL_LEVELS];
        for (int i = 0; i < GameConfig.TOTAL_LEVELS; i++) {
            try {
                String path = "/images/ui/levels/level" + (i + 1) + ".png";
                thumbnails[i] = ImageIO.read(getClass().getResourceAsStream(path));
            } catch (IOException | IllegalArgumentException e) {
                thumbnails[i] = null;
                System.err.println("Thumbnail level " + (i + 1) + " tidak ditemukan");
            }
        }
    }

    private void initComponents() {
        int W = GameConfig.WINDOW_WIDTH;
        int H = GameConfig.WINDOW_HEIGHT;

        // --- Tombol SETTING kiri atas ---
        ImageButton btnSetting = new ImageButton(btnSettingImg, 80, 80);
        btnSetting.setBounds(30, 20, 80, 80);
        btnSetting.addActionListener(e -> {
            SettingPanel setting = new SettingPanel(app);
            app.addPanel(setting, "SETTING");
            app.showPanel("SETTING");
        });
        add(btnSetting);

        // --- Tombol HOME kanan atas (sebelah close) --- ← TAMBAH
        ImageButton btnHome = new ImageButton(btnHomeImg, 80, 80);
        btnHome.setBounds(W - 200, 20, 80, 80);
        btnHome.addActionListener(e -> app.showPanel("MAIN_MENU"));
        add(btnHome);

        // --- Tombol CLOSE kanan atas ---
        ImageButton btnClose = new ImageButton(btnCloseImg, 80, 80);
        btnClose.setBounds(W - 110, 20, 80, 80);
        btnClose.addActionListener(e -> app.showPanel("MAIN_MENU"));
        add(btnClose);

        // --- Grid 6 level card ---
        int cardW  = 270, cardH  = 230;
        int gapX   = 30,  gapY   = 25;
        int cols   = 3;
        int totalGridW = cols * cardW + (cols - 1) * gapX;
        int startX = (W - totalGridW) / 2;
        int startY = 110;

        String[] levelNames = {
                "LEVEL 1", "LEVEL 2", "LEVEL 3",
                "LEVEL 4", "LEVEL 5", "LEVEL 6"
        };

        for (int i = 0; i < GameConfig.TOTAL_LEVELS; i++) {
            int col = i % cols;
            int row = i / cols;
            int x   = startX + col * (cardW + gapX);
            int y   = startY + row * (cardH + gapY);

            final int levelNum     = i + 1;
            final boolean unlocked = levelNum <= unlockedLevel;
            final BufferedImage thumb = thumbnails[i];

            JPanel card = createLevelCard(levelNum, levelNames[i], unlocked, thumb, cardW, cardH);
            card.setBounds(x, y, cardW, cardH);
            add(card);
        }
    }

    private JPanel createLevelCard(int levelNum, String label, boolean unlocked,
                                   BufferedImage thumb, int cardW, int cardH) {
        JPanel card = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                int arc = 30;
                int w   = getWidth();
                int h   = getHeight();

                // Shadow
                g2.setColor(CARD_SHADOW);
                g2.fill(new RoundRectangle2D.Float(4, 6, w - 4, h - 4, arc, arc));

                // Card background
                Color cardColor = unlocked ? CARD_COLOR : CARD_LOCKED_COLOR;
                g2.setColor(cardColor);
                g2.fill(new RoundRectangle2D.Float(0, 0, w, h, arc, arc));

                // Thumbnail
                int thumbPad = 12;
                int thumbW   = w - thumbPad * 2;
                int thumbH   = h - 65;
                int thumbX   = thumbPad;
                int thumbY   = thumbPad;

                Shape thumbClip = new RoundRectangle2D.Float(thumbX, thumbY, thumbW, thumbH, 18, 18);
                g2.setClip(thumbClip);

                if (thumb != null) {
                    g2.drawImage(thumb, thumbX, thumbY, thumbW, thumbH, null);
                } else {
                    g2.setColor(new Color(200, 200, 200));
                    g2.fillRoundRect(thumbX, thumbY, thumbW, thumbH, 18, 18);
                }

                if (!unlocked) {
                    g2.setColor(new Color(0, 0, 0, 100));
                    g2.fillRect(thumbX, thumbY, thumbW, thumbH);
                }

                g2.setClip(null);

                if (!unlocked) {
                    g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
                    FontMetrics fm = g2.getFontMetrics();
                    String lock = "🔒";
                    int lx = thumbX + (thumbW - fm.stringWidth(lock)) / 2;
                    int ly = thumbY + thumbH / 2 + fm.getAscent() / 2 - 5;
                    g2.drawString(lock, lx, ly);
                }

                // Label
                g2.setClip(null);
                g2.setFont(LEVEL_FONT);
                FontMetrics fm = g2.getFontMetrics();
                int tx = (w - fm.stringWidth(label)) / 2;
                int ty = h - 16;
                g2.setColor(new Color(0, 0, 0, 80));
                g2.drawString(label, tx + 1, ty + 1);
                g2.setColor(TEXT_COLOR);
                g2.drawString(label, tx, ty);

                g2.dispose();
            }
        };

        card.setOpaque(false);

        if (unlocked) {
            card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            card.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    card.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));
                    card.repaint();
                }
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    card.setBorder(null);
                    card.repaint();
                }
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    openLevel(levelNum);
                }
            });
        }

        return card;
    }

    private void openLevel(int levelNum) {
        GamePanel gamePanel = new GamePanel(app, levelNum);
        app.addPanel(gamePanel, "GAME_" + levelNum);
        app.showPanel("GAME_" + levelNum);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        if (bgImage != null) {
            g2.drawImage(bgImage, 0, 0, getWidth(), getHeight(), null);
        } else {
            GradientPaint gp = new GradientPaint(0, 0, new Color(130, 80, 200),
                    getWidth(), getHeight(), new Color(80, 200, 220));
            g2.setPaint(gp);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}