package com.cariperbedaan.ui;

import com.cariperbedaan.config.GameConfig;
import com.cariperbedaan.main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class GamePanel extends JPanel {

    private Main app;
    private int levelNum;

    public GamePanel(Main app, int levelNum) {
        this.app = app;
        this.levelNum = levelNum;
        setLayout(null);
        setPreferredSize(new Dimension(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT));
        initComponents();
    }

    private void initComponents() {
        int centerX = GameConfig.WINDOW_WIDTH / 2;

        JLabel placeholder = new JLabel("GAME LEVEL " + levelNum + " — Coming Soon!", SwingConstants.CENTER);
        placeholder.setFont(GameConfig.FONT_LEVEL);
        placeholder.setForeground(GameConfig.COLOR_TEXT);
        placeholder.setBounds(centerX - 300, 300, 600, 50);
        add(placeholder);

        JButton btnBack = new JButton("← Kembali") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? GameConfig.COLOR_SECONDARY : GameConfig.COLOR_PRIMARY);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                g2.setColor(Color.WHITE);
                g2.setFont(GameConfig.FONT_NORMAL);
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                        (getWidth() - fm.stringWidth(getText())) / 2,
                        (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        btnBack.setBounds(30, 30, 120, 40);
        btnBack.setOpaque(false);
        btnBack.setContentAreaFilled(false);
        btnBack.setBorderPainted(false);
        btnBack.setFocusPainted(false);
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(e -> app.showPanel("LEVEL_SELECT"));
        add(btnBack);
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