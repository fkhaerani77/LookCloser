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
                60,
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
                60,
        Arrays.asList(
                        new DifferencePoint(797, 298, 60),
                        new DifferencePoint(571, 545, 60),
                        new DifferencePoint(886, 541, 60),
                        new DifferencePoint(471, 696, 60),
                        new DifferencePoint(434, 878, 60),
                        new DifferencePoint(829, 880, 60),
                        new DifferencePoint(1418, 1032, 60)

                )
        ));

        // --- Level 3 (koordinat menyusul) ---
        levels.add(new Level(3,
                "/images/ui/level3/level3_a.png",
                "/images/ui/level3/level3_b.png",
                60,
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

        // --- Level 4 (koordinat menyusul) ---
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

        // --- Level 5 (koordinat menyusul) ---
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
                "/images/ui/level6/level6_a.png",
                "/images/ui/level6/level6_b.png",
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

        levels.add(new Level(7,
                "/images/ui/level7/level7_a.jpeg",
                "/images/ui/level7/level7_b.jpeg",
                120,
                Arrays.asList(
                        new DifferencePoint(148, 154, 60),
                        new DifferencePoint(94, 487, 60),
                        new DifferencePoint(700, 658, 60),
                        new DifferencePoint(688, 520, 60),
                        new DifferencePoint(433, 606, 60),
                        new DifferencePoint(1139, 734, 60),
                        new DifferencePoint(995, 222, 60)



                )
        ));

        levels.add(new Level(8,
                "/images/ui/level6/level8_a.jpeg",
                "/images/ui/level6/level8_b.jpeg",
                120,
                Arrays.asList(
                        new DifferencePoint(99, 260, 60),
                        new DifferencePoint(874, 96, 60),
                        new DifferencePoint(799, 518, 60),
                        new DifferencePoint(469, 585, 60),
                        new DifferencePoint(759, 806, 60),
                        new DifferencePoint(548, 852, 60),
                        new DifferencePoint(1220, 736, 60)



                )
        ));

        levels.add(new Level(9,
                "/images/ui/level9/level9_a.jpeg",
                "/images/ui/level9/level9_b.jpeg",
                120,
                Arrays.asList(
                        new DifferencePoint(489, 335, 60),
                        new DifferencePoint(307, 742, 60),
                        new DifferencePoint(56, 661, 60),
                        new DifferencePoint(843, 603, 60),
                        new DifferencePoint(828, 735, 60),
                        new DifferencePoint(981, 880, 60),
                        new DifferencePoint(857, 545, 60)



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