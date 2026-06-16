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

    // Area gambar di layar
    private Rectangle imgARect, imgBRect;

    // Animasi highlight
    private List<HighlightAnim> highlights = new ArrayList<>();

    // Timer countdown
    private int timeLeft;
    private Timer countdownTimer;
    private Timer animTimer;

    // UI labels
    private JLabel lblTimer;
    private JLabel lblFound;
    private JLabel lblLevel;

    // Status game
    private boolean gameOver  = false;
    private boolean gameWon   = false;

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

        // Ukuran & posisi gambar (2 gambar berdampingan)
        int imgW   = (W - 60) / 2;  // lebar tiap gambar
        int imgH   = H - 160;        // tinggi gambar
        int imgY   = 100;            // posisi y gambar
        int gapX   = 20;             // jarak antar gambar

        imgARect = new Rectangle(20, imgY, imgW, imgH);
        imgBRect = new Rectangle(20 + imgW + gapX, imgY, imgW, imgH);

        // --- Tombol HOME ---
        ImageButton btnHome = new ImageButton(btnHomeImg, 60, 60);
        btnHome.setBounds(10, 20, 60, 60);
        btnHome.addActionListener(e -> {
            stopTimers();
            level.reset();
            app.showPanel("LEVEL_SELECT");
        });
        add(btnHome);

        // --- Tombol CLOSE ---
        ImageButton btnClose = new ImageButton(btnCloseImg, 60, 60);
        btnClose.setBounds(W - 75, 20, 60, 60);
        btnClose.addActionListener(e -> {
            stopTimers();
            level.reset();
            app.showPanel("MAIN_MENU");
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

        // Mouse listener untuk deteksi klik perbedaan
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (gameOver || gameWon) return;
                handleClick(e.getX(), e.getY());
            }
        });
    }

    private void handleClick(int mouseX, int mouseY) {
        // Cek klik di gambar A atau gambar B
        boolean clickOnA = imgARect.contains(mouseX, mouseY);
        boolean clickOnB = imgBRect.contains(mouseX, mouseY);

        if (!clickOnA && !clickOnB) return;

        // Konversi koordinat mouse ke koordinat gambar asli
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

        // Cek apakah klik mengenai salah satu perbedaan
        boolean hit = false;
        for (DifferencePoint dp : level.getDifferences()) {
            if (!dp.isFound() && dp.isClicked(relX, relY)) {
                dp.setFound(true);
                hit = true;

                // Tambah highlight di kedua gambar
                addHighlight(dp, scaleX, scaleY);

                // Update label found
                lblFound.setText(LanguageManager.getInstance().get("game.found") +
                        " " + level.getFoundCount() + "/" + level.getDifferences().size());

                // SFX benar
                AudioManager.getInstance().playSFX("select-click.wav");

                // Cek menang
                if (level.isCompleted()) {
                    gameWon = true;
                    stopTimers();
                    showWinDialog();
                }
                break;
            }
        }

        if (!hit) {
            // SFX salah - animasi guncang
            AudioManager.getInstance().playSFX("select-click.wav");
            showWrongEffect(mouseX, mouseY);
        }
    }

    // Tambah lingkaran highlight di kedua gambar
    private void addHighlight(DifferencePoint dp, double scaleX, double scaleY) {
        // Posisi di gambar A (layar)
        int axScreen = imgARect.x + (int) (dp.getX() / scaleX);
        int ayScreen = imgARect.y + (int) (dp.getY() / scaleY);
        int rScreen  = (int) (dp.getRadius() / scaleX);

        highlights.add(new HighlightAnim(axScreen, ayScreen, rScreen));

        // Posisi di gambar B (layar) - koordinat sama
        int bxScreen = imgBRect.x + (int) (dp.getX() / scaleX);
        int byScreen = imgBRect.y + (int) (dp.getY() / scaleY);

        highlights.add(new HighlightAnim(bxScreen, byScreen, rScreen));

        repaint();
    }

    private void showWrongEffect(int x, int y) {
        // Flash merah sebentar di posisi klik
        highlights.add(new HighlightAnim(x, y, 20, Color.RED, true));
        repaint();

        Timer removeTimer = new Timer(500, e -> {
            highlights.removeIf(h -> h.isWrong());
            repaint();
        });
        removeTimer.setRepeats(false);
        removeTimer.start();
    }

    private void startTimers() {
        // Countdown timer
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

        // Animasi timer (repaint tiap 50ms)
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
                // Next level
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
                // Retry level yang sama
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

        // Background gelap
        g2.setColor(new Color(20, 20, 40));
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Header bar
        g2.setColor(new Color(30, 30, 60));
        g2.fillRect(0, 0, getWidth(), 95);

        // Garis bawah header
        g2.setColor(new Color(255, 255, 255, 30));
        g2.drawLine(0, 95, getWidth(), 95);

        // Gambar A
        if (imgA != null && imgARect != null) {
            // Border gambar
            g2.setColor(new Color(255, 255, 255, 50));
            g2.setStroke(new BasicStroke(2f));
            g2.drawRect(imgARect.x - 2, imgARect.y - 2, imgARect.width + 4, imgARect.height + 4);
            g2.drawImage(imgA, imgARect.x, imgARect.y, imgARect.width, imgARect.height, null);
        }

        // Gambar B
        if (imgB != null && imgBRect != null) {
            g2.setColor(new Color(255, 255, 255, 50));
            g2.setStroke(new BasicStroke(2f));
            g2.drawRect(imgBRect.x - 2, imgBRect.y - 2, imgBRect.width + 4, imgBRect.height + 4);
            g2.drawImage(imgB, imgBRect.x, imgBRect.y, imgBRect.width, imgBRect.height, null);
        }

        // Render semua highlight
        for (HighlightAnim h : highlights) {
            h.draw(g2);
        }

        // Label "VS" di tengah antara 2 gambar
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        int vsX = imgARect.x + imgARect.width + 1;
        int vsY = imgARect.y + imgARect.height / 2;
        g2.drawString("VS", vsX, vsY);
    }

    // Inner class untuk animasi highlight lingkaran
    private static class HighlightAnim {
        int x, y, radius;
        Color color;
        boolean wrong;
        float alpha = 1.0f;

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
            g2.setColor(new Color(
                    color.getRed(), color.getGreen(), color.getBlue(), 180));
            g2.setStroke(new BasicStroke(3f));
            g2.drawOval(x - radius, y - radius, radius * 2, radius * 2);

            // Fill transparan
            g2.setColor(new Color(
                    color.getRed(), color.getGreen(), color.getBlue(), 40));
            g2.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        }
    }
}