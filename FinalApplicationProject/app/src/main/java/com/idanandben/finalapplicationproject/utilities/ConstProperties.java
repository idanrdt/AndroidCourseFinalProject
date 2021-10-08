package com.idanandben.finalapplicationproject.utilities;

public class ConstProperties {
    public static String USER_SETTINGS_MSG = "UserSettings";

    public static String USERS_TABLE = "UsersTable";
    public static String CURRENT_LEVEL_PREFERENCES = "CurrentLevel";
    public static String CURRENT_DIFFICULTY_PREFERENCES = "CurrentDifficulty";
    public static String MAX_ALLOWED_LEVEL_PREFERENCES = "MaxAllowedLevel";
    public static String SCORES_PREFERENCES = "Scores";
    public static String MUSIC_ENABLE_PREFERENCES = "MusicEnable";

    public static int[] LIFE_AMOUNT_BY_DIFFICULTY = new int[] {5, 3, 1};

    public static int[] TIME_MINUTES_BY_DIFFICULTY = new int[] {1, 1, 0};
    public static int[] TIME_SECONDS_BY_DIFFICULTY = new int[] {30, 0, 45};

    public static int[] BLOCK_AMOUNT_BY_DIFFICULTY = new int[] {8, 12, 16};
    public static int[] COLOR_GROUPS_BY_DIFFICULTY_LEVEL2 = new int[] {3, 5, 8};
    public static int[] POINTS_MULTIPLIER_BY_DIFFICULTY = new int[] {1, 2, 5};

    public static int MAX_LEVEL_EXIST = 3;
    public static int GENERIC_COLOR = 0xFFFFFFFF;

    //Level 1 constants
    public static String LEVEL1_INSTRUCTIONS = "Place the elements in their positions";

    //Level 2 constants
    public static String LEVEL2_INSTRUCTIONS = "Pick the element Family Group";

    //Level 3 constants
    public static String LEVEL3_INSTRUCTIONS = "Match the element name to their symbol";

}