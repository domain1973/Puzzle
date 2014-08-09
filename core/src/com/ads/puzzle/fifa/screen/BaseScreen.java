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
    protected Stage stage;
    protected Batch batch;
    protected Puzzle game;
    protected BitmapFont font;
    protected float y_bar;

    public BaseScreen(Puzzle game) {
        stage = new Stage();
        this.game = game;
        y_bar = Gdx.graphics.getHeight() - Assets.TOPBAR_HEIGHT;
        batch = stage.getBatch();
        font = new BitmapFont(Gdx.files.internal("other.fnt"),
                Gdx.files.internal("other.png"), false);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage); // 设置输入接收器
        Image bgImg = new Image(Assets.gameBg);
        bgImg.setScale(Assets.WIDTH / (float) Assets.gameBg.getRegionWidth(), Assets.HEIGHT / (float) Assets.gameBg.getRegionHeight());
        stage.addActor(bgImg);
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

    public Puzzle getGame() {
        return game;
    }

    public BitmapFont getFont() {
        return font;
    }

    public Stage getStage() {
        return stage;
    }
}
