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
                120,
                Arrays.asList(
                        new DifferencePoint(359, 972, 30),
                        new DifferencePoint(120, 956, 30),
                        new DifferencePoint(585, 756, 30),
                        new DifferencePoint(802, 377, 30),
                        new DifferencePoint(912, 627, 30),
                        new DifferencePoint(803, 894, 30)
                )
        ));

        // --- Level 2 (koordinat menyusul) ---
        levels.add(new Level(2,
                "/images/ui/level2/level2_a.jpeg",
                "/images/ui/level2/level2_b.jpeg",
                120,
                Arrays.asList(
                        new DifferencePoint(0, 0, 30) // placeholder
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