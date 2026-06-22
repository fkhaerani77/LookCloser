import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CoordinateFinder extends JFrame {

    private BufferedImage image;
    private JLabel imgLabel;
    private JTextArea coordLog;
    private int clickCount = 0;

    public CoordinateFinder(String imagePath) throws IOException {
        image = ImageIO.read(new File(imagePath));

        setTitle("Coordinate Finder - Klik area perbedaan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel gambar
        JPanel imgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (image != null) g.drawImage(image, 0, 0, null);
            }
            @Override
            public Dimension getPreferredSize() {
                return image != null
                        ? new Dimension(image.getWidth(), image.getHeight())
                        : new Dimension(800, 600);
            }
        };
        imgPanel.setLayout(null);
        imgPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickCount++;
                int x = e.getX();
                int y = e.getY();

                // Gambar titik merah di posisi klik
                Graphics g = imgPanel.getGraphics();
                g.setColor(Color.RED);
                g.fillOval(x - 5, y - 5, 10, 10);
                g.setColor(Color.YELLOW);
                g.drawString(String.valueOf(clickCount), x + 8, y);

                // Log koordinat
                String coord = String.format(
                        "{\"x\": %d, \"y\": %d, \"radius\": 30}", x, y);
                coordLog.append(coord + ",\n");
                System.out.println("Titik " + clickCount + ": x=" + x + ", y=" + y);
            }
        });

        // Scroll untuk gambar
        JScrollPane imgScroll = new JScrollPane(imgPanel);
        imgScroll.setPreferredSize(new Dimension(900, 500));
        add(imgScroll, BorderLayout.CENTER);

        // Panel log koordinat
        coordLog = new JTextArea(8, 40);
        coordLog.setFont(new Font("Monospaced", Font.PLAIN, 13));
        coordLog.setBorder(BorderFactory.createTitledBorder("Koordinat (copy ke levels.json):"));
        JScrollPane logScroll = new JScrollPane(coordLog);

        // Tombol reset
        JButton btnReset = new JButton("Reset");
        btnReset.addActionListener(e -> {
            clickCount = 0;
            coordLog.setText("");
            imgPanel.repaint();
        });

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(logScroll, BorderLayout.CENTER);
        bottomPanel.add(btnReset, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) throws IOException {
        // Ganti path ini dengan path image_a.png level yang mau dicari koordinatnya
        String imagePath = "src/main/resources/images/ui/level6/level6_a.png";

        SwingUtilities.invokeLater(() -> {
            try {
                new CoordinateFinder(imagePath).setVisible(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}