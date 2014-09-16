package com.ads.puzzle.fifa;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2014/6/24.
 */
public class Answer {
    public static final int GATE_MAX = 12;
    public final static int GRADE_1 = 3;
    public final static int GRADE_2 = 6;
    public final static int GRADE_3 = 12;
    public final static int GRADE_4 = 20;

    public final static String[] TITLES = new String[]{
            "您总算过关了!", "恭喜您过关了!", "您真棒!", "您是天才!"};

    public final static String[] VALUES =
            {"03,11,22,30",
                    "2,3,1,0",
                    "1,0,2,3",
                    "1,2,3,4",
                    "2,0,1,3",
                    "1,3,0,2",
                    "3,1,0,2",
                    "1,0,2,3",
                    "3,0,2,1",
                    "2,1,0,3",
                    "1,3,2,0",
                    "2,0,1,3",
                    "2,3,1,0",
                    "0,3,2,1",
                    "3,1,0,2",
                    "2,1,3,0",
                    "0,3,1,2",
                    "3,1,2,0",
                    "1,2,3,0",
                    "1,3,2,0",
                    "1,0,3,2",
                    "3,1,2,0",
                    "2,0,3,1",
                    "3,1,2,0",
                    "2,0,1,3",
                    "1,0,3,2",
                    "1,2,0,3",
                    "0,2,3,1",
                    "3,1,0,2",
                    "3,0,1,2",
                    "2,1,0,3",
                    "2,3,0,1",
                    "2,1,3,0",
                    "1,3,0,2",
                    "3,1,0,2",
                    "3,2,1,0",
                    "1,3,0,2",
                    "0,2,1,3",
                    "2,3,0,1",
                    "2,1,0,3",
                    "0,3,2,1",
                    "1,2,3,0",
                    "0,2,1,3",
                    "1,0,3,2",
                    "3,2,1,0",
                    "1,0,3,2",
                    "1,0,2,3",
                    "0,2,1,3",
                    "0,1,2,3",
                    "1,0,2,3",
                    "1,2,3,0",
                    "2,1,3,0",
                    "2,1,3,0",
                    "3,1,0,2",
                    "3,1,0,2",
                    "3,1,0,2",
                    "0,3,2,1",
                    "0,3,1,2",
                    "0,3,2,1",
                    "0,3,2,1",
                    "1,2,3,0",
                    "1,2,3,0",
                    "3,1,0,2",
                    "3,1,0,2",
                    "3,1,0,2",
                    "0,3,2,1",
                    "0,3,1,2",
                    "1,2,3,0",
                    "3,1,0,2",
                    "2,3,1,0",
                    "3,1,0,2",
                    "1,2,3,0"};

    public final static Integer[][] CHALLENGES = {
            {0, 0, 0, 0, 0},
            {3, 3, 3, 3, 3},
            {1, 1, 1, 1},
            {2, 2, 2, 2, 4, 4, 4},
            {3, 3, 3, 3, 3, 3, 1},
            {1, 1, 1, 4, 4, 4},
            {0, 0, 0, 4, 4, 4, 4},
            {1, 1, 1, 2},
            {0, 0, 0, 1, 1, 1, 1},
            {4, 4, 4, 3, 3},
            {3, 3, 1},
            {3, 3, 3, 1, 1, 1, 1},
            {3, 3, 3, 0},
            {1, 1, 0, 0},
            {0, 0, 4, 4, 4, 4, 4},
            {2, 2, 4, 3},
            {0, 0, 3, 3, 4, 4, 1, 1, 1},
            {0, 3, 4},
            {2, 2, 2, 2, 2, 0, 0},
            {3, 2, 1},
            {1, 1, 1, 0, 0, 0, 2, 2, 4},
            {0, 4, 2},
            {1, 1, 1, 1, 3, 2},
            {0, 4, 4, 3},
            {3, 3, 3, 3, 0, 1},
            {1, 1, 1, 1, 2, 4},
            {2, 2, 2, 4, 4, 4, 3},
            {1, 1, 1, 0, 0, 0, 0, 2},
            {0, 0, 0, 1, 1, 4, 4, 4},
            {0, 1, 1, 1, 4, 4},
            {4, 4, 3, 0, 0},
            {1, 1, 1, 4, 4, 4, 3},
            {2, 2, 3, 0},
            {4, 4, 4, 4, 2, 2, 1, 1},
            {4, 4, 4, 0, 0, 1, 1, 1},
            {0, 2, 2, 4, 4},
            {1, 2, 2, 0, 0, 4, 4, 4, 3},
            {0, 0, 0, 0, 3, 2},
            {0, 0, 0, 3, 3, 4, 4},
            {4, 4, 4, 3, 3, 1},
            {0, 0, 0, 0, 1, 1, 3},
            {2, 2, 2, 2, 0, 3},
            {0, 0, 0, 0, 2, 3, 3},
            {1, 1, 1, 2, 2, 2, 4},
            {0, 0, 3, 3, 1, 1, 2, 2},
            {0, 4, 2, 1, 1, 1, 1},
            {0, 0, 2, 2, 1, 1, 1},
            {0, 0, 0, 3, 3, 4, 2, 1},
            {0, 1, 3, 4, 2, 2},
            {0, 0, 1, 1, 2, 2},
            {1, 3, 2, 2, 4, 4},
            {0, 0, 1, 1, 2, 3, 4},
            {3, 3, 1, 1, 2, 4},
            {0, 0, 4, 4, 4, 2},
            {0, 0, 0, 4, 4, 4, 2},
            {0, 0, 0, 4, 4, 4, 2, 1},
            {0, 0, 0, 1, 1, 1},
            {0, 0, 0, 1, 1, 1, 4},
            {0, 0, 0, 1, 1, 3},
            {0, 0, 0, 1, 1, 3, 3},
            {2, 2, 2, 2, 0, 3, 4},
            {2, 2, 2, 2, 0, 0, 1},
            {4, 4, 4, 4, 0, 2},
            {4, 4, 4, 4, 1, 1},
            {4, 4, 4, 4, 0, 0, 2},
            {0, 0, 0, 0, 1, 3},
            {0, 0, 0, 0, 1, 1, 4},
            {0, 2, 2, 2, 2, 2},
            {4, 4, 4, 4, 4},
            {3, 3, 3, 3, 3, 0, 2},
            {0, 2, 2, 3, 4, 4, 1},
            {3, 2, 2, 1, 4}};

    public static List<Integer> gateStars = new ArrayList<Integer>();
}
