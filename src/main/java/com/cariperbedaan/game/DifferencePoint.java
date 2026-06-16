package com.cariperbedaan.game;

public class DifferencePoint {
    private int x, y, radius;
    private boolean found;

    public DifferencePoint(int x, int y, int radius) {
        this.x      = x;
        this.y      = y;
        this.radius = radius;
        this.found  = false;
    }

    // Cek apakah klik user mengenai area perbedaan
    public boolean isClicked(int clickX, int clickY) {
        int dx = clickX - x;
        int dy = clickY - y;
        return Math.sqrt(dx * dx + dy * dy) <= radius;
    }

    public int getX()          { return x; }
    public int getY()          { return y; }
    public int getRadius()     { return radius; }
    public boolean isFound()   { return found; }
    public void setFound(boolean found) { this.found = found; }
}