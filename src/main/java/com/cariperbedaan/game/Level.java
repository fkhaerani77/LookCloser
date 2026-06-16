package com.cariperbedaan.game;

import java.util.List;

public class Level {
    private int id;
    private String imageAPath;
    private String imageBPath;
    private int timeLimit;
    private List<DifferencePoint> differences;

    public Level(int id, String imageAPath, String imageBPath,
                 int timeLimit, List<DifferencePoint> differences) {
        this.id          = id;
        this.imageAPath  = imageAPath;
        this.imageBPath  = imageBPath;
        this.timeLimit   = timeLimit;
        this.differences = differences;
    }

    public int getId()                          { return id; }
    public String getImageAPath()               { return imageAPath; }
    public String getImageBPath()               { return imageBPath; }
    public int getTimeLimit()                   { return timeLimit; }
    public List<DifferencePoint> getDifferences() { return differences; }

    public int getFoundCount() {
        return (int) differences.stream().filter(DifferencePoint::isFound).count();
    }

    public boolean isCompleted() {
        return differences.stream().allMatch(DifferencePoint::isFound);
    }

    public void reset() {
        differences.forEach(d -> d.setFound(false));
    }
}