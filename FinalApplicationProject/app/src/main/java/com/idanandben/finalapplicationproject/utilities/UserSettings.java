package com.idanandben.finalapplicationproject.utilities;

import android.os.Parcel;
import android.os.Parcelable;

public class UserSettings implements Parcelable {
    private int currentStage;
    private final int maxAllowedStage;
    private int difficulty;
    private String UserName;
    private int score;

    public UserSettings(int currentStage) {
        this(currentStage, 1);
    }

    public UserSettings(int currentStage, int difficulty) {
        this.currentStage = currentStage;
        this.maxAllowedStage = 2;
        this.difficulty = difficulty;
        this.score = 0;
    }

    protected UserSettings(Parcel in) {
        currentStage = in.readInt();
        maxAllowedStage = in.readInt();
        difficulty = in.readInt();
        UserName = in.readString();
        score = in.readInt();
    }

    public static final Creator<UserSettings> CREATOR = new Creator<UserSettings>() {
        @Override
        public UserSettings createFromParcel(Parcel in) {
            return new UserSettings(in);
        }

        @Override
        public UserSettings[] newArray(int size) {
            return new UserSettings[size];
        }
    };

    public int getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(int currentStage) {
        this.currentStage = currentStage;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(currentStage);
        dest.writeInt(maxAllowedStage);
        dest.writeInt(difficulty);
        dest.writeString(UserName);
        dest.writeInt(score);
    }
}
