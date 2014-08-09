package com.ads.puzzle.fifa.controller;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by Administrator on 2014/7/4.
 */
public abstract class IController extends Group {
    public static final String AREA_CTRL = "a";
    public static final String CHALLENGE_CTRL = "b";
    public static final String PIECE_CTRL = "c";
    public static final String SMALLPIECE_CTRL = "d";

    public abstract void handler();
}
