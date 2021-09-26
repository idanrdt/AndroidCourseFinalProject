package com.idanandben.finalapplicationproject.utilities;

public class ConstProperties {
    public static String USER_SETTINGS_MSG = "UserSettings";
    public static String USERS_TABLE_MSG = "UsersTable";
    public static String CURRENT_LEVEL_MSG = "CurrentLevel";
    public static String CURRENT_DIFFICULTY_MSG = "CurrentDifficulty";
    public static String MAX_ALLOWED_LEVEL_MSG = "MaxAllowedLevel";

    public static String[] DIFFICULTIES = new String[] {"Easy", "Medium" ,"Hard"};

    public static int MAX_LEVEL_EXIST = 3;

    public static int EASY_LIFE_AMOUNT = 5;
    public static int MEDIUM_LIFE_AMOUNT = 3;
    public static int HARD_LIFE_AMOUNT = 1;

    public static int EASY_TIME_MINUTES = 1;
    public static int EASY_TIME_SECONDS = 30;

    public static int MEDIUM_TIME_MINUTES = 1;
    public static int MEDIUM_TIME_SECONDS = 0;

    public static int HARD_TIME_MINUTES = 0;
    public static int HARD_TIME_SECONDS = 45;

    public static int EASY_POINTS_MULTIPLIER = 1;
    public static double MEDIUM_POINTS_MULTIPLIER = 1.5;
    public static int HARD_POINTS_MULTIPLIER = 3;


    //Level 1 constants
    public static int EASY_LEVEL1_BLOCK_AMOUNT = 10;
    public static int MEDIUM_LEVEL1_BLOCK_AMOUNT = 15;
    public static int HARD_LEVEL1_BLOCK_AMOUNT = 10;

    public static String LEVEL1_INSTRUCTIONS = "1";

    //Level 2 constants
    public static int EASY_LEVEL2_BLOCK_AMOUNT = 15;
    public static int EASY_LEVEL2_COLOR_GROUPS = 3;
    public static int MEDIUM_LEVEL2_BLOCK_AMOUNT = 15;
    public static int MEDIUM_LEVEL2_COLOR_GROUPS = 5;
    public static int HARD_LEVEL2_BLOCK_AMOUNT = 15;
    public static int HARD_LEVEL2_COLOR_GROUPS = 8;

    public static String LEVEL2_INSTRUCTIONS = "1";

    //Level 3 constants
    public static int EASY_LEVEL3_BLOCK_AMOUNT = 10;
    public static int MEDIUM_LEVEL3_BLOCK_AMOUNT = 15;
    public static int HARD_LEVEL3_BLOCK_AMOUNT = 10;

    public static String LEVEL3_INSTRUCTIONS = "1";

}




/*

Level 1 - place element
    Easy: time 01:30, 5 life, points x1, bank have all info, 10 blocks from wanted (1:2 select block)
    Medium: time 01:00, 3 life, points x1.5, bank have only color, 15 blocks from wanted (1:3 select block)
    Hard: time 00:45, 1 life, points x3, bank just symbol, 10 blocks from table (1:5 to select block)

level 2 - select color
    Easy: time 01:30, 5 life, points x1, bank contains 3 groups, 15 blocks from wanted, life recharge
    Medium: time 01:00, 3 life, points x1.5, bank contains 5 groups, 15 blocks from wanted, no life recharge
    Hard: time 00:45, 1 life, points x3, bank contains all groups , 15 blocks from table ,no life recharge

level 3 - place name
    Easy: time 02:00, 5 life, points x1, bank have all info, 10 blocks from wanted, life recharge
    Medium: time 01:30, 3 life, points x1.5, bank have color and symbol, 15 blocks from wanted, no life recharge
    Hard: time 01:00, 1 life, points x3, bank have symbol, 10 blocks from table (1:5 to select block), no life recharge

* */