package com.ads.puzzle.fifa.window;

import com.ads.puzzle.fifa.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

/**
 * Created by Administrator on 2014/7/30.
 */
public class BaseWindow extends Window {

    protected Image layerBg;

    public BaseWindow(String title, WindowStyle style) {
        super(title, style);
        layerBg = new Image(Assets.layerBg);
        layerBg.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
}
