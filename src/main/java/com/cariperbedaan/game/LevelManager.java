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
                "/images/ui/level2/level2_a.png",
                "/images/ui/level2/level2_b.png",
                120,
                Arrays.asList(
                        new DifferencePoint(796, 279, 60),
                        new DifferencePoint(585, 502, 60),
                        new DifferencePoint(892, 513, 60),
                        new DifferencePoint(486, 686, 60),
                        new DifferencePoint(446, 0, 60),
                        new DifferencePoint(796, 0, 60),
                        new DifferencePoint(1481, 1086, 60)


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
                        new DifferencePoint(1075, 227, 30),
                        new DifferencePoint(927, 509, 30),
                        new DifferencePoint(1165, 421, 30),
                        new DifferencePoint(1427, 463, 30),
                        new DifferencePoint(1013, 926, 30),
                        new DifferencePoint(357, 940, 30),
                        new DifferencePoint(144, 1004, 30)



                )
        ));

        levels.add(new Level(7,
                "/images/ui/level7/level7_a.jpeg",
                "/images/ui/level7/level7_b.jpeg",
                120,
                Arrays.asList(
                        new DifferencePoint(1075, 227, 30),
                        new DifferencePoint(927, 509, 30),
                        new DifferencePoint(1165, 421, 30),
                        new DifferencePoint(1427, 463, 30),
                        new DifferencePoint(1013, 926, 30),
                        new DifferencePoint(357, 940, 30),
                        new DifferencePoint(144, 1004, 30)



                )
        ));

        levels.add(new Level(8,
                "/images/ui/level6/level8_a.jpeg",
                "/images/ui/level6/level8_b.jpeg",
                120,
                Arrays.asList(
                        new DifferencePoint(1075, 227, 30),
                        new DifferencePoint(927, 509, 30),
                        new DifferencePoint(1165, 421, 30),
                        new DifferencePoint(1427, 463, 30),
                        new DifferencePoint(1013, 926, 30),
                        new DifferencePoint(357, 940, 30),
                        new DifferencePoint(144, 1004, 30)



                )
        ));

        levels.add(new Level(9,
                "/images/ui/level9/level9_a.jpeg",
                "/images/ui/level9/level9_b.jpeg",
                120,
                Arrays.asList(
                        new DifferencePoint(1075, 227, 30),
                        new DifferencePoint(927, 509, 30),
                        new DifferencePoint(1165, 421, 30),
                        new DifferencePoint(1427, 463, 30),
                        new DifferencePoint(1013, 926, 30),
                        new DifferencePoint(357, 940, 30),
                        new DifferencePoint(144, 1004, 30)



                )
        ));

        levels.add(new Level(10,
                "/images/ui/level6/level10_a.jpeg",
                "/images/ui/level6/level10_b.jpeg",
                120,
                Arrays.asList(
                        new DifferencePoint(523, 182, 60),
                        new DifferencePoint(748, 191, 60),
                        new DifferencePoint(109, 386, 60),
                        new DifferencePoint(515, 534, 60),
                        new DifferencePoint(517, 699, 60),
                        new DifferencePoint(727, 644, 60),
                        new DifferencePoint(945, 506, 60),
                        new DifferencePoint(963, 563, 60)



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