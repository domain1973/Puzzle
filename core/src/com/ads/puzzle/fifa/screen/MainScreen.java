package com.ads.puzzle.fifa.screen;

import com.ads.puzzle.fifa.Assets;
import com.ads.puzzle.fifa.Puzzle;
import com.ads.puzzle.fifa.Settings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Ads on 2014/6/22.
 */
public class MainScreen extends BaseScreen {
    private Puzzle game;
    private float mi_x;
    private float mi_y;
    private float btn_font_x;
    private float btn_font_x1;
    private float themeSize;
    private float btn_font_y;
    private float btn_font_y1;
    private float btn_font_y2;

    public MainScreen(Puzzle game) {
        super(game);
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        Image theme = new Image(Assets.theme);
        theme.setPosition((Assets.WIDTH - theme.getWidth()) / 2, Assets.HEIGHT / 2);

        ImageButton playBtn = new ImageButton(new TextureRegionDrawable(Assets.playBtn), new TextureRegionDrawable(Assets.playBtnDown));
        float x = (Assets.WIDTH - playBtn.getWidth()) / 2;
        btn_font_x = x + playBtn.getWidth() / 3 - 30;
        btn_font_x1 = x + playBtn.getWidth() * 2 / 3;
        float btn_y = Assets.HEIGHT / 3;
        playBtn.setPosition(x, btn_y);
        btn_font_y = btn_y + playBtn.getHeight();
        ImageButton settingBtn = new ImageButton(new TextureRegionDrawable(Assets.settingBtn), new TextureRegionDrawable(Assets.settingBtnDown));
        float btn_y1 = btn_y - playBtn.getHeight() * 3 / 2;
        settingBtn.setPosition(x, btn_y1);
        btn_font_y1 = btn_y1 + playBtn.getHeight();
        ImageButton helpBtn = new ImageButton(new TextureRegionDrawable(Assets.settingBtn), new TextureRegionDrawable(Assets.settingBtnDown));
        float btn_y2 = btn_y - playBtn.getHeight() * 3;
        helpBtn.setPosition(x, btn_y2);
        btn_font_y2 = btn_y2 + playBtn.getHeight();
        playBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new LevelScreen(game));
                super.touchUp(event, x, y, pointer, button);
            }
        });
        settingBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new SettingScreen(game));
                super.touchUp(event, x, y, pointer, button);
            }
        });
        helpBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new ReadmeScreen(game, MainScreen.this));
                super.touchUp(event, x, y, pointer, button);
            }
        });
        themeSize = Assets.WIDTH / 2;
        addActor(theme);
        addActor(playBtn);
        addActor(settingBtn);
        addActor(helpBtn);

        mi_x = (Gdx.graphics.getWidth() - themeSize) / 2 - 80;
        mi_y = Gdx.graphics.getHeight() * 3 / 5 + themeSize;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        getBatch().begin();
        getFont().draw(getBatch(), "开", btn_font_x, btn_font_y);
        getFont().draw(getBatch(), "始", btn_font_x1, btn_font_y);
        getFont().draw(getBatch(), "选", btn_font_x, btn_font_y1);
        getFont().draw(getBatch(), "项", btn_font_x1, btn_font_y1);
        getFont().draw(getBatch(), "帮", btn_font_x, btn_font_y2);
        getFont().draw(getBatch(), "助", btn_font_x1, btn_font_y2);
        getBatch().end();
    }

    @Override
    public void pause() {
        Settings.save();
    }

    @Override
    public void dispose() {
        getFont().dispose();
        getStage().dispose();
    }

}
