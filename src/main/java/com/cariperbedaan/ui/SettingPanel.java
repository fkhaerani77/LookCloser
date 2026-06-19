package com.cariperbedaan.ui;

import com.cariperbedaan.audio.AudioManager;
import com.cariperbedaan.config.GameConfig;
import com.cariperbedaan.config.LanguageManager;
import com.cariperbedaan.main.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SettingPanel extends JPanel {

    private Main app;
    private BufferedImage bgImage;
    private BufferedImage btnCloseImg;
    private BufferedImage btnHomeImg;
    private BufferedImage icMusicOn, icMusicOff;
    private BufferedImage icSfxOn, icSfxOff;
    private BufferedImage icLanguage;

    private boolean musicOn = true;
    private boolean soundOn = true;

    private JPanel cardPanel;

    public SettingPanel(Main app) {
        this.app = app;
        setLayout(null);
        setPreferredSize(new Dimension(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT));
        loadImages();
        initComponents();
        LanguageManager.getInstance().addListener(this::rebuildCard);
    }

    private void loadImages() {
        try {
            bgImage     = ImageIO.read(getClass().getResourceAsStream("/images/ui/bg_level.png"));
            btnCloseImg = ImageIO.read(getClass().getResourceAsStream("/images/ui/bt_close.png"));
            btnHomeImg  = ImageIO.read(getClass().getResourceAsStream("/images/ui/bt_home.png"));
            icMusicOn   = ImageIO.read(getClass().getResourceAsStream("/images/ui/ic_music_on.png"));
            icMusicOff  = ImageIO.read(getClass().getResourceAsStream("/images/ui/ic_music_off.png"));
            icSfxOn     = ImageIO.read(getClass().getResourceAsStream("/images/ui/ic_sound_effect_on.png"));
            icSfxOff    = ImageIO.read(getClass().getResourceAsStream("/images/ui/ic_sound_effect_off.png"));
            icLanguage  = ImageIO.read(getClass().getResourceAsStream("/images/ui/ic_language.png"));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Gagal load image setting: " + e.getMessage());
        }
    }

    private void initComponents() {
        int W = GameConfig.WINDOW_WIDTH;

        // --- Tombol HOME kanan atas ---
        ImageButton btnHome = new ImageButton(btnHomeImg, 80, 80);
        btnHome.setBounds(W - 200, 20, 80, 80);
        btnHome.addActionListener(e -> {
            LanguageManager.getInstance().removeListener(this::rebuildCard);
            app.showPanel("MAIN_MENU");
        });
        add(btnHome);

        // --- Tombol CLOSE kanan atas ---
        ImageButton btnClose = new ImageButton(btnCloseImg, 80, 80);
        btnClose.setBounds(W - 110, 20, 80, 80);
        btnClose.addActionListener(e -> {
            LanguageManager.getInstance().removeListener(this::rebuildCard);
            app.showPanel("MAIN_MENU");
        });
        add(btnClose);

        buildCard();
    }

    private void buildCard() {
        int W = GameConfig.WINDOW_WIDTH;
        int H = GameConfig.WINDOW_HEIGHT;
        int cardW = 520, cardH = 390;
        int cardX = (W - cardW) / 2;
        int cardY = (H - cardH) / 2 - 20;

        LanguageManager lang = LanguageManager.getInstance();

        cardPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Shadow
                g2.setColor(new Color(0, 0, 0, 60));
                g2.fill(new RoundRectangle2D.Float(6, 8, getWidth() - 6, getHeight() - 6, 30, 30));

                // Card background
                g2.setColor(new Color(20, 20, 60, 210));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 30, 30));

                // Border
                g2.setColor(new Color(255, 255, 255, 40));
                g2.setStroke(new BasicStroke(1.5f));
                g2.draw(new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2, 30, 30));

                // Judul
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 34));
                FontMetrics fm = g2.getFontMetrics();
                String title = lang.get("setting.title");
                g2.drawString(title, (getWidth() - fm.stringWidth(title)) / 2, 58);

                // Garis bawah judul
                g2.setColor(new Color(255, 255, 255, 80));
                g2.setStroke(new BasicStroke(1f));
                g2.drawLine(40, 72, getWidth() - 40, 72);

                g2.dispose();
            }
        };
        cardPanel.setOpaque(false);
        cardPanel.setBounds(cardX, cardY, cardW, cardH);

        // --- Row Musik ---
        addToggleRow(cardPanel, lang.get("setting.music"), 95,
                () -> musicOn,
                v -> {
                    musicOn = v;
                    AudioManager.getInstance().setMusicOn(v);
                    if (v) AudioManager.getInstance().playBGM("backsound.wav");
                }, cardW, icMusicOn, icMusicOff
        );

        // --- Row Efek Suara ---
        addToggleRow(cardPanel, lang.get("setting.sfx"), 165,
                () -> soundOn,
                v -> {
                    soundOn = v;
                    AudioManager.getInstance().setSoundOn(v);
                }, cardW, icSfxOn, icSfxOff
        );

        // --- Row Bahasa ---
        addLanguageRow(cardPanel, lang.get("setting.language"), 235, cardW, icLanguage);

        // --- Tombol Kembali ---
        JButton btnBack = new JButton(lang.get("setting.back")) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color base = new Color(235, 100, 180);
                g2.setColor(getModel().isRollover() ? base.darker() : base);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 18));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                        (getWidth() - fm.stringWidth(getText())) / 2,
                        (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        btnBack.setBounds((cardW - 260) / 2, 325, 260, 48);
        btnBack.setOpaque(false);
        btnBack.setContentAreaFilled(false);
        btnBack.setBorderPainted(false);
        btnBack.setFocusPainted(false);
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(e -> {
            LanguageManager.getInstance().removeListener(this::rebuildCard);
            app.showPanel("MAIN_MENU");
        });
        cardPanel.add(btnBack);

        add(cardPanel);
        revalidate();
        repaint();
    }

    private void rebuildCard() {
        if (cardPanel != null) remove(cardPanel);
        buildCard();
    }

    private void addToggleRow(JPanel parent, String label, int y,
                              java.util.function.Supplier<Boolean> getter,
                              java.util.function.Consumer<Boolean> setter,
                              int cardW,
                              BufferedImage iconOn,
                              BufferedImage iconOff) {

        // Icon (ganti sesuai state ON/OFF)
        JLabel icLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                BufferedImage ic = getter.get() ? iconOn : iconOff;
                if (ic != null) g2.drawImage(ic, 0, 0, 36, 36, null);
                g2.dispose();
            }
        };
        icLabel.setBounds(50, y + 2, 36, 36);
        parent.add(icLabel);

        // Teks label
        JLabel lbl = new JLabel(label, SwingConstants.LEFT) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 20));
                g2.drawString(getText(), 0, 26);
                g2.dispose();
            }
        };
        lbl.setIcon(null);          // ← TAMBAH
        lbl.setBounds(96, y, 220, 40);
        parent.add(lbl);

        // Toggle button
        JButton toggle = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                boolean state = getter.get();
                g2.setColor(state ? new Color(46, 204, 113) : new Color(180, 60, 60));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), getHeight(), getHeight()));
                int knobSize = getHeight() - 8;
                int knobX    = state ? getWidth() - knobSize - 4 : 4;
                g2.setColor(Color.WHITE);
                g2.fillOval(knobX, 4, knobSize, knobSize);
                g2.setFont(new Font("Arial", Font.BOLD, 13));
                FontMetrics fm = g2.getFontMetrics();
                String txt = state ? LanguageManager.getInstance().get("setting.on")
                        : LanguageManager.getInstance().get("setting.off");
                int tx = state ? 10 : getWidth() - fm.stringWidth(txt) - 10;
                g2.setColor(Color.WHITE);
                g2.drawString(txt, tx, getHeight() / 2 + fm.getAscent() / 2 - 2);
                g2.dispose();
            }
        };
        toggle.setBounds(cardW - 160, y, 100, 40);
        toggle.setOpaque(false);
        toggle.setContentAreaFilled(false);
        toggle.setBorderPainted(false);
        toggle.setFocusPainted(false);
        toggle.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        toggle.addActionListener(e -> {
            setter.accept(!getter.get());
            icLabel.repaint(); // icon ikut update
            toggle.repaint();
        });
        parent.add(toggle);
    }

    private void addLanguageRow(JPanel parent, String label, int y,
                                int cardW, BufferedImage icon) {

        // Icon bahasa (statis)
        JLabel icLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                if (icon != null) g2.drawImage(icon, 0, 0, 36, 36, null);
                g2.dispose();
            }
        };
        icLabel.setBounds(50, y + 2, 36, 36);
        parent.add(icLabel);

        // Teks label
        JLabel lbl = new JLabel(label, SwingConstants.LEFT) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 20));
                g2.drawString(getText(), 0, 26);
                g2.dispose();
            }
        };
        lbl.setIcon(null);          // ← TAMBAH
        lbl.setBounds(96, y, 220, 40);
        parent.add(lbl);

        // Tombol EN
        JButton btnEN = createLangButton("EN");
        btnEN.setBounds(cardW - 220, y, 70, 40);
        btnEN.addActionListener(e ->
                LanguageManager.getInstance().setLanguage(LanguageManager.Language.EN));
        parent.add(btnEN);

        // Tombol ID
        JButton btnID = createLangButton("ID");
        btnID.setBounds(cardW - 140, y, 70, 40);
        btnID.addActionListener(e ->
                LanguageManager.getInstance().setLanguage(LanguageManager.Language.ID));
        parent.add(btnID);
    }

    private JButton createLangButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                boolean isActive = LanguageManager.getInstance().isEnglish()
                        ? getText().equals("EN")
                        : getText().equals("ID");

                g2.setColor(isActive ? new Color(235, 100, 180) : new Color(80, 80, 120));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));

                if (isActive) {
                    g2.setColor(new Color(255, 255, 255, 60));
                    g2.setStroke(new BasicStroke(2f));
                    g2.draw(new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2, 12, 12));
                }

                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 16));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                        (getWidth() - fm.stringWidth(getText())) / 2,
                        (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
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