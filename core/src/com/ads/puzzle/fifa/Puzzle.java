package com.ads.puzzle.fifa;

import com.ads.puzzle.fifa.screen.MainScreen;
import com.badlogic.gdx.Game;

public class Puzzle extends Game {
    private int passGateNum;
    private MainScreen mainScreen;
    private PayEvent payEvent;

    public Puzzle(PayEvent pe) {
        payEvent = pe;
    }

    @Override
    public void create() {
        Settings.load();
        Assets.load();
        mainScreen = new MainScreen(this);
        setScreen(mainScreen);
    }

    public MainScreen getMainScreen() {
        return mainScreen;
    }

    public int getPassGateNum() {
        return passGateNum;
    }

    public void setPassGateNum(int passGateNum) {
        this.passGateNum = passGateNum;
    }

    public PayEvent getPayEvent() {
        return payEvent;
    }
}
