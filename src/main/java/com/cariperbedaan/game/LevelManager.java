package com.cariperbedaan.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LevelManager {

    private static LevelManager instance;
    private List<Level> levels;

    private LevelManager() {
        levels = new ArrayList<>();
        initLevels();
    }

    public static LevelManager getInstance() {
        if (instance == null) instance = new LevelManager();
        return instance;
    }

    private void initLevels() {
        // --- Level 1 ---
        levels.add(new Level(1,
                "/images/ui/level1/level1_a.jpeg",
                "/images/ui/level1/level1_b.jpeg",
                90,
                Arrays.asList(
                        new DifferencePoint(805, 444, 60),
                        new DifferencePoint(922, 606, 60),
                        new DifferencePoint(587, 738, 60),
                        new DifferencePoint(810, 864, 60),
                        new DifferencePoint(351, 1000, 60),
                        new DifferencePoint(115, 996, 60),
                        new DifferencePoint(1013, 966, 60)

                )
        ));

        // --- Level 2 (koordinat menyusul) ---
        levels.add(new Level(2,
                "/images/ui/level2/level2_a.jpeg",
                "/images/ui/level2/level2_b.jpeg",
                120,
                Arrays.asList(
                        new DifferencePoint(983, 278, 30),
                        new DifferencePoint(815, 846, 30),
                        new DifferencePoint(1459, 1054, 30),
                        new DifferencePoint(426, 863, 30),
                        new DifferencePoint(480, 691, 30),
                        new DifferencePoint(721, 716, 30),
                        new DifferencePoint(922, 548, 30),
                        new DifferencePoint(545, 516, 30)
                )
        ));

        // --- Level 3 (koordinat menyusul) ---
        levels.add(new Level(3,
                "/images/ui/level3/level3_a.jpeg",
                "/images/ui/level3/level3_b.jpeg",
                120,
                Arrays.asList(
                        new DifferencePoint(0, 0, 30) // placeholder
                )
        ));

        // --- Level 4 (koordinat menyusul) ---
        levels.add(new Level(4,
                "/images/ui/level4/level4_a.jpeg",
                "/images/ui/level4/level4_b.jpeg",
                120,
                Arrays.asList(
                        new DifferencePoint(0, 0, 30) // placeholder
                )
        ));

        // --- Level 5 (koordinat menyusul) ---
        levels.add(new Level(5,
                "/images/ui/level5/level5_a.jpeg",
                "/images/ui/level5/level5_b.jpeg",
                120,
                Arrays.asList(
                        new DifferencePoint(0, 0, 30) // placeholder
                )
        ));

        // --- Level 6 (koordinat menyusul) ---
        levels.add(new Level(6,
                "/images/ui/level6/level6_a.jpeg",
                "/images/ui/level6/level6_b.jpeg",
                120,
                Arrays.asList(
                        new DifferencePoint(0, 0, 30) // placeholder
                )
        ));
    }

    public Level getLevel(int levelNum) {
        return levels.get(levelNum - 1);
    }

    public int getTotalLevels() {
        return levels.size();
    }
}