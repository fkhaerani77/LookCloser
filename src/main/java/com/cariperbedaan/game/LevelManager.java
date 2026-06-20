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

        // --- Level 2 ---
        levels.add(new Level(2,
                "/images/ui/level2/level2_a.png",
                "/images/ui/level2/level2_b.png",
                120,
                Arrays.asList(
                        new DifferencePoint(789, 255, 60),
                        new DifferencePoint(578, 538, 60),
                        new DifferencePoint(874, 541, 60),
                        new DifferencePoint(479, 690, 60),
                        new DifferencePoint(425, 852, 60),
                        new DifferencePoint(806, 863, 60),
                        new DifferencePoint(1461, 1092, 60)



                )
        ));

        // --- Level 3 ---
        levels.add(new Level(3,
                "/images/ui/level3/level3_a.png",
                "/images/ui/level3/level3_b.png",
                120,
                Arrays.asList(
                        new DifferencePoint( 295, 270, 60),
                        new DifferencePoint(567, 326, 60),
                        new DifferencePoint(813, 570, 60),
                        new DifferencePoint(537, 844, 60),
                        new DifferencePoint(200, 1079, 60),
                        new DifferencePoint(1036, 840, 60),
                        new DifferencePoint(1494, 289, 60)
                )
        ));

        // --- Level 4 ---
        levels.add(new Level(4,
                "/images/ui/level4/level4_a.png",
                "/images/ui/level4/level4_b.png",
                120,
                Arrays.asList(
                        new DifferencePoint(375, 406, 60),
                        new DifferencePoint(584, 875, 60),
                        new DifferencePoint(566, 348, 60),
                        new DifferencePoint(1359, 141, 60),
                        new DifferencePoint(1519, 361, 60),
                        new DifferencePoint(1441, 460, 60),
                        new DifferencePoint(1452, 549, 60)

                )
        ));

        // --- Level 5 ---
        levels.add(new Level(5,
                "/images/ui/level5/level5_a.png",
                "/images/ui/level5/level5_b.png",
                120,
                Arrays.asList(
                        new DifferencePoint(287, 244, 60),
                        new DifferencePoint(641, 204, 60),
                        new DifferencePoint(1036, 100, 60),
                        new DifferencePoint(920, 402, 60),
                        new DifferencePoint(1307, 745, 60),
                        new DifferencePoint(1379, 984, 60),
                        new DifferencePoint(1453, 297, 60)

                )
        ));

        // --- Level 6 (koordinat menyusul) ---
        levels.add(new Level(6,
                "/images/ui/level6/level6_a.jpeg",
                "/images/ui/level6/level6_b.jpeg",
                120,
                Arrays.asList(
                        new DifferencePoint(1075, 227, 60),
                        new DifferencePoint(927, 509, 60),
                        new DifferencePoint(1165, 421, 60),
                        new DifferencePoint(1427, 463, 60),
                        new DifferencePoint(1013, 926, 60),
                        new DifferencePoint(357, 940, 60),
                        new DifferencePoint(144, 1004, 60)



                )
        ));

        // --- Level 7 (koordinat menyusul) ---
        levels.add(new Level(7,
                "/images/ui/level7/level7_a.jpeg",
                "/images/ui/level7/level7_b.jpeg",
                120,
                Arrays.asList(
                        new DifferencePoint(0, 0, 30)
                )
        ));

        // --- Level 8 (koordinat menyusul) ---
        levels.add(new Level(8,
                "/images/ui/level8/level8_a.jpeg",
                "/images/ui/level8/level8_b.jpeg",
                120,
                Arrays.asList(
                        new DifferencePoint(0, 0, 30)
                )
        ));

        // --- Level 9 (koordinat menyusul) ---
        levels.add(new Level(9,
                "/images/ui/level9/level9_a.jpeg",
                "/images/ui/level9/level9_b.jpeg",
                120,
                Arrays.asList(
                        new DifferencePoint(0, 0, 30)
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