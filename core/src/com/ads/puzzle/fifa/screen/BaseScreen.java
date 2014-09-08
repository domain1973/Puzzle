package com.ads.puzzle.fifa.screen;

import com.ads.puzzle.fifa.Assets;
import com.ads.puzzle.fifa.Puzzle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Administrator on 2014/7/21.
 */
public class BaseScreen extends ScreenAdapter {
    private Stage stage;
    private Batch batch;
    private Puzzle puzzle;
    private BitmapFont font;
    private BitmapFont gameFont;
    private BitmapFont dlgFont;
    private float y_bar;

    public BaseScreen(Puzzle game) {
        stage = new Stage();
        puzzle = game;
        y_bar = Assets.HEIGHT - Assets.TOPBAR_HEIGHT;
        batch = stage.getBatch();
        font = new BitmapFont(Gdx.files.internal("other.fnt"),
                Gdx.files.internal("other.png"), false);
        gameFont = new BitmapFont(Gdx.files.internal("puzzle.fnt"),
                Gdx.files.internal("puzzle.png"), false);
        dlgFont = new BitmapFont(Gdx.files.internal("dlg.fnt"),
                Gdx.files.internal("dlg.png"), false);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage); // 设置输入接收器
        Image bg = new Image(Assets.gameBg);
        bg.setBounds(0, 0, Assets.WIDTH, Assets.HEIGHT);
        stage.addActor(bg);
    }

    protected void addActor(Actor actor) {
        stage.addActor(actor);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    public BitmapFont getFont() {
        return font;
    }

    public BitmapFont getGameFont() {
        return gameFont;
    }

    public BitmapFont getDlgFont() {
        return dlgFont;
    }

    public Stage getStage() {
        return stage;
    }

    public Batch getBatch() {
        return batch;
    }

    public float getY_bar() {
        return y_bar;
    }
}
