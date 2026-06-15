package com.cariperbedaan.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageButton extends JButton {

    private BufferedImage image;
    private int imgW, imgH;

    public ImageButton(BufferedImage image, int w, int h) {
        this.image = image;
        this.imgW  = w;
        this.imgH  = h;
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        float alpha = getModel().isPressed() ? 0.75f : 1.0f;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        if (getModel().isRollover() && !getModel().isPressed()) {
            int offset = 4;
            g2.drawImage(image, -offset / 2, -offset / 2, imgW + offset, imgH + offset, null);
        } else {
            g2.drawImage(image, 0, 0, imgW, imgH, null);
        }
        g2.dispose();
    }
}