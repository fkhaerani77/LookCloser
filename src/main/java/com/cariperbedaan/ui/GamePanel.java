package com.cariperbedaan.ui;

import com.cariperbedaan.audio.AudioManager;
import com.cariperbedaan.config.GameConfig;
import com.cariperbedaan.config.LanguageManager;
import com.cariperbedaan.game.DifferencePoint;
import com.cariperbedaan.game.Level;
import com.cariperbedaan.game.LevelManager;
import com.cariperbedaan.main.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {

    private Main app;
    private int levelNum;
    private Level level;

    private BufferedImage imgA, imgB;
    private BufferedImage btnCloseImg, btnHomeImg;

    private Rectangle imgARect, imgBRect;
    private List<HighlightAnim> highlights = new ArrayList<>();

    private int timeLeft;
    private Timer countdownTimer;
    private Timer animTimer;

    private JLabel lblTimer;
    private JLabel lblFound;
    private JLabel lblLevel;

    private boolean gameOver = false;
    private boolean gameWon  = false;

    public GamePanel(Main app, int levelNum) {
        this.app      = app;
        this.levelNum = levelNum;
        this.level    = LevelManager.getInstance().getLevel(levelNum);
        this.timeLeft = level.getTimeLimit();

        setLayout(null);
        setPreferredSize(new Dimension(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT));

        loadImages();
        initComponents();
        startTimers();
    }

    private void loadImages() {
        try {
            imgA        = ImageIO.read(getClass().getResourceAsStream(level.getImageAPath()));
            imgB        = ImageIO.read(getClass().getResourceAsStream(level.getImageBPath()));
            btnCloseImg = ImageIO.read(getClass().getResourceAsStream("/images/ui/bt_close.png"));
            btnHomeImg  = ImageIO.read(getClass().getResourceAsStream("/images/ui/bt_home.png"));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Gagal load gambar level: " + e.getMessage());
        }
    }

    private void initComponents() {
        int W = GameConfig.WINDOW_WIDTH;
        int H = GameConfig.WINDOW_HEIGHT;

        int imgW = (W - 60) / 2;
        int imgH = H - 160;
        int imgY = 100;
        int gapX = 20;

        imgARect = new Rectangle(20, imgY, imgW, imgH);
        imgBRect = new Rectangle(20 + imgW + gapX, imgY, imgW, imgH);

        // --- Tombol HOME → PAUSE ---
        ImageButton btnHome = new ImageButton(btnHomeImg, 60, 60);
        btnHome.setBounds(10, 20, 60, 60);
        btnHome.addActionListener(e -> showPauseDialog());
        add(btnHome);

        // --- Tombol CLOSE → Confirm quit ---
        ImageButton btnClose = new ImageButton(btnCloseImg, 60, 60);
        btnClose.setBounds(W - 75, 20, 60, 60);
        btnClose.addActionListener(e -> {
            if (countdownTimer != null) countdownTimer.stop();
            if (animTimer != null) animTimer.stop();

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Yakin ingin keluar ke menu utama?",
                    "Keluar",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                stopTimers();
                level.reset();
                highlights.clear();
                app.showPanel("MAIN_MENU");
            } else {
                countdownTimer.start();
                animTimer.start();
            }
        });
        add(btnClose);

        // --- Label Level ---
        lblLevel = new JLabel("LEVEL " + levelNum, SwingConstants.CENTER);
        lblLevel.setFont(new Font("Arial", Font.BOLD, 22));
        lblLevel.setForeground(Color.WHITE);
        lblLevel.setBounds(W / 2 - 100, 25, 200, 35);
        add(lblLevel);

        // --- Label Found ---
        lblFound = new JLabel(LanguageManager.getInstance().get("game.found") +
                " 0/" + level.getDifferences().size(), SwingConstants.CENTER);
        lblFound.setFont(new Font("Arial", Font.BOLD, 18));
        lblFound.setForeground(new Color(46, 204, 113));
        lblFound.setBounds(W / 2 - 120, 60, 240, 30);
        add(lblFound);

        // --- Label Timer ---
        lblTimer = new JLabel(formatTime(timeLeft), SwingConstants.CENTER);
        lblTimer.setFont(new Font("Arial", Font.BOLD, 20));
        lblTimer.setForeground(Color.WHITE);
        lblTimer.setBounds(W - 160, 25, 80, 35);
        add(lblTimer);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (gameOver || gameWon) return;
                handleClick(e.getX(), e.getY());
            }
        });
    }

    private void showPauseDialog() {
        if (countdownTimer != null) countdownTimer.stop();
        if (animTimer != null) animTimer.stop();

        JDialog pauseDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), true);
        pauseDialog.setUndecorated(true);
        pauseDialog.setSize(360, 380);
        pauseDialog.setLocationRelativeTo(this);
        pauseDialog.setBackground(new Color(0, 0, 0, 0));

        JPanel content = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Shadow
                g2.setColor(new Color(0, 0, 0, 80));
                g2.fill(new RoundRectangle2D.Float(6, 8, getWidth() - 6, getHeight() - 6, 30, 30));

                // Background card
                g2.setColor(new Color(20, 20, 60, 230));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 30, 30));

                // Border
                g2.setColor(new Color(255, 255, 255, 50));
                g2.setStroke(new BasicStroke(1.5f));
                g2.draw(new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2, 30, 30));

                // Judul
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 32));
                FontMetrics fm = g2.getFontMetrics();
                String title = "PAUSED";
                g2.drawString(title, (getWidth() - fm.stringWidth(title)) / 2, 65);

                // Garis bawah judul
                g2.setColor(new Color(255, 255, 255, 60));
                g2.setStroke(new BasicStroke(1f));
                g2.drawLine(30, 80, getWidth() - 30, 80);

                // Info level & waktu
                g2.setColor(new Color(200, 200, 200));
                g2.setFont(new Font("Arial", Font.PLAIN, 15));
                fm = g2.getFontMetrics();
                String info = "Level " + levelNum + "  •  Time: " + formatTime(timeLeft);
                g2.drawString(info, (getWidth() - fm.stringWidth(info)) / 2, 108);

                g2.dispose();
            }
        };
        content.setOpaque(false);
        content.setPreferredSize(new Dimension(360, 380));

        // --- Tombol RESUME ---
        JButton btnResume = createPauseButton("Resume", new Color(46, 204, 113));
        btnResume.setBounds(55, 135, 250, 55);
        btnResume.addActionListener(e -> {
            pauseDialog.dispose();
            countdownTimer.start();
            animTimer.start();
        });
        content.add(btnResume);

        // --- Tombol RESTART ---
        JButton btnRestart = createPauseButton("Restart", new Color(52, 152, 219));
        btnRestart.setBounds(55, 205, 250, 55);
        btnRestart.addActionListener(e -> {
            pauseDialog.dispose();
            stopTimers();
            level.reset();
            highlights.clear();
            GamePanel retry = new GamePanel(app, levelNum);
            app.addPanel(retry, "GAME_RESTART_" + levelNum);
            app.showPanel("GAME_RESTART_" + levelNum);
        });
        content.add(btnRestart);

        // --- Tombol HOME ---
        JButton btnHomeDialog = createPauseButton("Home", new Color(235, 100, 180));
        btnHomeDialog.setBounds(55, 275, 250, 55);
        btnHomeDialog.addActionListener(e -> {
            pauseDialog.dispose();
            stopTimers();
            level.reset();
            highlights.clear();
            app.showPanel("LEVEL_SELECT");
        });
        content.add(btnHomeDialog);

        pauseDialog.setContentPane(content);
        pauseDialog.pack();
        pauseDialog.setVisible(true);
    }

    private JButton createPauseButton(String text, Color baseColor) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color bg = getModel().isPressed()  ? baseColor.darker()
                        : getModel().isRollover() ? baseColor.brighter()
                        : baseColor;

                g2.setColor(bg);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));

                g2.setColor(new Color(255, 255, 255, 40));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight() / 2f, 15, 15));

                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 18));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                        (getWidth() - fm.stringWidth(getText())) / 2,
                        (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        btn.setIcon(null);              // ← TAMBAH INI
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setHorizontalTextPosition(SwingConstants.CENTER); // ← TAMBAH INI
        btn.setVerticalTextPosition(SwingConstants.CENTER);   // ← TAMBAH INI
        return btn;
    }

    private void handleClick(int mouseX, int mouseY) {
        boolean clickOnA = imgARect.contains(mouseX, mouseY);
        boolean clickOnB = imgBRect.contains(mouseX, mouseY);

        if (!clickOnA && !clickOnB) return;

        double scaleX, scaleY;
        int relX, relY;

        if (clickOnA) {
            scaleX = (double) imgA.getWidth()  / imgARect.width;
            scaleY = (double) imgA.getHeight() / imgARect.height;
            relX   = (int) ((mouseX - imgARect.x) * scaleX);
            relY   = (int) ((mouseY - imgARect.y) * scaleY);
        } else {
            scaleX = (double) imgB.getWidth()  / imgBRect.width;
            scaleY = (double) imgB.getHeight() / imgBRect.height;
            relX   = (int) ((mouseX - imgBRect.x) * scaleX);
            relY   = (int) ((mouseY - imgBRect.y) * scaleY);
        }

        boolean hit = false;
        for (DifferencePoint dp : level.getDifferences()) {
            if (!dp.isFound() && dp.isClicked(relX, relY)) {
                dp.setFound(true);
                hit = true;

                addHighlight(dp, scaleX, scaleY);

                lblFound.setText(LanguageManager.getInstance().get("game.found") +
                        " " + level.getFoundCount() + "/" + level.getDifferences().size());

                AudioManager.getInstance().playSFX("select-click.wav");

                if (level.isCompleted()) {
                    gameWon = true;
                    stopTimers();
                    showWinDialog();
                }
                break;
            }
        }

        if (!hit) {
            AudioManager.getInstance().playSFX("select-click.wav");
            showWrongEffect(mouseX, mouseY);
        }
    }

    private void addHighlight(DifferencePoint dp, double scaleX, double scaleY) {
        int axScreen = imgARect.x + (int) (dp.getX() / scaleX);
        int ayScreen = imgARect.y + (int) (dp.getY() / scaleY);
        int rScreen  = (int) (dp.getRadius() / scaleX);
        highlights.add(new HighlightAnim(axScreen, ayScreen, rScreen));

        int bxScreen = imgBRect.x + (int) (dp.getX() / scaleX);
        int byScreen = imgBRect.y + (int) (dp.getY() / scaleY);
        highlights.add(new HighlightAnim(bxScreen, byScreen, rScreen));

        repaint();
    }

    private void showWrongEffect(int x, int y) {
        highlights.add(new HighlightAnim(x, y, 20, Color.RED, true));
        repaint();

        Timer removeTimer = new Timer(500, e -> {
            highlights.removeIf(HighlightAnim::isWrong);
            repaint();
        });
        removeTimer.setRepeats(false);
        removeTimer.start();
    }

    private void startTimers() {
        countdownTimer = new Timer(1000, e -> {
            timeLeft--;
            lblTimer.setText(formatTime(timeLeft));

            if (timeLeft <= 10) {
                lblTimer.setForeground(new Color(231, 76, 60));
            }

            if (timeLeft <= 0) {
                gameOver = true;
                stopTimers();
                showGameOverDialog();
            }
        });
        countdownTimer.start();

        animTimer = new Timer(50, e -> repaint());
        animTimer.start();
    }

    private void stopTimers() {
        if (countdownTimer != null) countdownTimer.stop();
        if (animTimer != null) animTimer.stop();
    }

    private String formatTime(int seconds) {
        int m = seconds / 60;
        int s = seconds % 60;
        return String.format("%02d:%02d", m, s);
    }

    private void showWinDialog() {
        SwingUtilities.invokeLater(() -> {
            String[] options = {"Next Level", "Menu"};
            int choice = JOptionPane.showOptionDialog(
                    this,
                    "🎉 " + LanguageManager.getInstance().get("game.complete") +
                            "\nTime left: " + formatTime(timeLeft),
                    "Level " + levelNum + " Complete!",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null, options, options[0]
            );

            level.reset();
            if (choice == 0 && levelNum < GameConfig.TOTAL_LEVELS) {
                GamePanel next = new GamePanel(app, levelNum + 1);
                app.addPanel(next, "GAME_" + (levelNum + 1));
                app.showPanel("GAME_" + (levelNum + 1));
            } else {
                app.showPanel("LEVEL_SELECT");
            }
        });
    }

    private void showGameOverDialog() {
        SwingUtilities.invokeLater(() -> {
            String[] options = {"Retry", "Menu"};
            int choice = JOptionPane.showOptionDialog(
                    this,
                    "⏰ Time's Up!",
                    "Game Over",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null, options, options[0]
            );

            level.reset();
            if (choice == 0) {
                GamePanel retry = new GamePanel(app, levelNum);
                app.addPanel(retry, "GAME_RETRY_" + levelNum);
                app.showPanel("GAME_RETRY_" + levelNum);
            } else {
                app.showPanel("LEVEL_SELECT");
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g2.setColor(new Color(20, 20, 40));
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.setColor(new Color(30, 30, 60));
        g2.fillRect(0, 0, getWidth(), 95);

        g2.setColor(new Color(255, 255, 255, 30));
        g2.drawLine(0, 95, getWidth(), 95);

        if (imgA != null && imgARect != null) {
            g2.setColor(new Color(255, 255, 255, 50));
            g2.setStroke(new BasicStroke(2f));
            g2.drawRect(imgARect.x - 2, imgARect.y - 2, imgARect.width + 4, imgARect.height + 4);
            g2.drawImage(imgA, imgARect.x, imgARect.y, imgARect.width, imgARect.height, null);
        }

        if (imgB != null && imgBRect != null) {
            g2.setColor(new Color(255, 255, 255, 50));
            g2.setStroke(new BasicStroke(2f));
            g2.drawRect(imgBRect.x - 2, imgBRect.y - 2, imgBRect.width + 4, imgBRect.height + 4);
            g2.drawImage(imgB, imgBRect.x, imgBRect.y, imgBRect.width, imgBRect.height, null);
        }

        for (HighlightAnim h : highlights) {
            h.draw(g2);
        }

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        int vsX = imgARect.x + imgARect.width + 1;
        int vsY = imgARect.y + imgARect.height / 2;
        g2.drawString("VS", vsX, vsY);
    }

    private static class HighlightAnim {
        int x, y, radius;
        Color color;
        boolean wrong;

        HighlightAnim(int x, int y, int radius) {
            this.x      = x;
            this.y      = y;
            this.radius = radius;
            this.color  = new Color(46, 204, 113);
            this.wrong  = false;
        }

        HighlightAnim(int x, int y, int radius, Color color, boolean wrong) {
            this.x      = x;
            this.y      = y;
            this.radius = radius;
            this.color  = color;
            this.wrong  = wrong;
        }

        boolean isWrong() { return wrong; }

        void draw(Graphics2D g2) {
            g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 180));
            g2.setStroke(new BasicStroke(3f));
            g2.drawOval(x - radius, y - radius, radius * 2, radius * 2);

            g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 40));
            g2.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        }
    }
}