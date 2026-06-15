package com.cariperbedaan.ui;

import com.cariperbedaan.config.GameConfig;
import com.cariperbedaan.main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class SettingPanel extends JPanel {

    private Main app;
    private boolean musicOn = true;
    private boolean soundOn = true;

    public SettingPanel(Main app) {
        this.app = app;
        setLayout(null);
        setPreferredSize(new Dimension(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT));
        initComponents();
    }

    private void initComponents() {
        int centerX = GameConfig.WINDOW_WIDTH / 2;

        // Judul
        JLabel title = new JLabel("SETTING", SwingConstants.CENTER);
        title.setFont(GameConfig.FONT_TITLE);
        title.setForeground(GameConfig.COLOR_TEXT);
        title.setBounds(centerX - 200, 100, 400, 60);
        add(title);

        // --- Panel musik ---
        addToggleRow("🎵  Musik",    280, () -> musicOn, v -> musicOn = v);
        addToggleRow("🔊  Efek Suara", 370, () -> soundOn, v -> soundOn = v);

        // Tombol Back
        JButton btnBack = new JButton("← Kembali ke Menu") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? GameConfig.COLOR_SECONDARY : GameConfig.COLOR_PRIMARY);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));
                g2.setColor(Color.WHITE);
                g2.setFont(GameConfig.FONT_BUTTON);
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                        (getWidth() - fm.stringWidth(getText())) / 2,
                        (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        btnBack.setBounds(centerX - 150, 490, 300, 50);
        btnBack.setOpaque(false);
        btnBack.setContentAreaFilled(false);
        btnBack.setBorderPainted(false);
        btnBack.setFocusPainted(false);
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(e -> app.showPanel("MAIN_MENU"));
        add(btnBack);
    }

    // Buat baris label + toggle button
    private void addToggleRow(String label, int y, java.util.function.Supplier<Boolean> getter,
                              java.util.function.Consumer<Boolean> setter) {
        int centerX = GameConfig.WINDOW_WIDTH / 2;

        JLabel lbl = new JLabel(label);
        lbl.setFont(GameConfig.FONT_LEVEL);
        lbl.setForeground(GameConfig.COLOR_TEXT);
        lbl.setBounds(centerX - 200, y, 280, 50);
        add(lbl);

        JButton toggle = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                boolean state = getter.get();
                Color bg = state ? GameConfig.COLOR_SUCCESS : GameConfig.COLOR_ACCENT;
                g2.setColor(bg);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), getHeight(), getHeight()));

                // Lingkaran toggle
                int knobSize = getHeight() - 8;
                int knobX = state ? getWidth() - knobSize - 4 : 4;
                g2.setColor(Color.WHITE);
                g2.fillOval(knobX, 4, knobSize, knobSize);

                // Teks ON/OFF
                g2.setFont(new Font("Arial", Font.BOLD, 12));
                g2.setColor(Color.WHITE);
                String txt = state ? "ON" : "OFF";
                FontMetrics fm = g2.getFontMetrics();
                int tx = state ? 8 : getWidth() - fm.stringWidth(txt) - 8;
                g2.drawString(txt, tx, getHeight() / 2 + fm.getAscent() / 2 - 2);
                g2.dispose();
            }
        };

        toggle.setBounds(centerX + 100, y + 5, 90, 38);
        toggle.setOpaque(false);
        toggle.setContentAreaFilled(false);
        toggle.setBorderPainted(false);
        toggle.setFocusPainted(false);
        toggle.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        toggle.addActionListener(e -> {
            setter.accept(!getter.get());
            toggle.repaint();
            // Nanti: AudioManager.getInstance().setMusicEnabled(musicOn);
        });
        add(toggle);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        GradientPaint gp = new GradientPaint(
                0, 0, new Color(30, 39, 46),
                0, getHeight(), new Color(20, 90, 120)
        );
        g2.setPaint(gp);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }
}