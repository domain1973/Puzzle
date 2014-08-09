package com.ads.puzzle.fifa.screen;

import com.ads.puzzle.fifa.Puzzle;
import com.ads.puzzle.fifa.Settings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

/**
 * Created by Administrator on 2014/6/22.
 */
public class SettingScreen extends ScreenAdapter {
    Puzzle game;

    public SettingScreen(Puzzle game) {
        this.game = game;
    }

    public void draw() {
        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    }

    @Override
    public void render(float delta) {
        draw();
    }

    @Override
    public void pause() {
        Settings.save();
    }
}
