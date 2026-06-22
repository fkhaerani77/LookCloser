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

        // --- Level 2 ---
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

        // --- Level 3  ---
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

        // --- Level 6 ---
        levels.add(new Level(6,
                "/images/ui/level6/level6_a.png",
                "/images/ui/level6/level6_b.png",
                120,
                Arrays.asList(
                        new DifferencePoint(1259, 403, 60),
                        new DifferencePoint(1423, 500, 60),
                        new DifferencePoint(940, 495, 60),
                        new DifferencePoint(1044, 225, 60),
                        new DifferencePoint(1034, 919, 60),
                        new DifferencePoint(325, 871, 60),
                        new DifferencePoint(131, 958, 60)

                )
        ));

        // --- Level 7 ---
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

        // --- Level 8 ---
        levels.add(new Level(8,
                "/images/ui/level8/level8_a.jpeg",
                "/images/ui/level8/level8_b.jpeg",
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

        // --- Level 9 ---
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
                "/images/ui/level10/level10_a.png",
                "/images/ui/level10/level10_b.png",
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

        levels.add(new Level(11,
                "/images/ui/level11/level11_a.png",
                "/images/ui/level11/level11_b.png",
                120,
        Arrays.asList(
                        new DifferencePoint(171, 252, 100),
                        new DifferencePoint(132, 484,100),
                        new DifferencePoint(443, 724, 60),
                        new DifferencePoint(474, 601, 80),
                        new DifferencePoint(681, 688, 70),
                        new DifferencePoint(854, 683, 120),
                        new DifferencePoint(815, 406, 100)
                )
        ));
        levels.add(new Level(12,
                "/images/ui/level12/level12_a.png",
                "/images/ui/level12/level12_b.png",
                120,
                Arrays.asList(
                        new DifferencePoint(300, 338, 50),
                        new DifferencePoint(391, 412,50),
                        new DifferencePoint(535, 350, 30),
                        new DifferencePoint(541, 281, 30),
                        new DifferencePoint(107, 613, 70),
                        new DifferencePoint(517, 399, 30),
                        new DifferencePoint(875, 522, 90)
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