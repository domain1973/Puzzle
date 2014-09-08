package com.ads.puzzle.fifa.screen;

import com.ads.puzzle.fifa.Assets;
import com.ads.puzzle.fifa.Puzzle;
import com.ads.puzzle.fifa.Settings;
import com.ads.puzzle.fifa.window.DefaultDialog;
import com.ads.puzzle.fifa.window.GameExitDialog;
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

    public MainScreen(Puzzle game) {
        super(game);
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        Image theme = new Image(Assets.theme);
        theme.setPosition((Assets.WIDTH - theme.getWidth()) / 2, Assets.HEIGHT / 2);

        float btnSize = Assets.WIDTH *2/ 5;
        float btnPlayX = (Assets.WIDTH - btnSize) / 2;
        float btnPlayY = Assets.HEIGHT / 4;
        ImageButton playBtn = new ImageButton(new TextureRegionDrawable(Assets.playBtn), new TextureRegionDrawable(Assets.playBtnDown));
        playBtn.setBounds(btnPlayX, btnPlayY, btnSize, btnSize);

        float btnSmallSize = Assets.WIDTH / 5;
        float btnPlayXLeft = btnPlayX - btnSmallSize;
        ImageButton settingBtn = new ImageButton(new TextureRegionDrawable(Assets.playBtn), new TextureRegionDrawable(Assets.playBtnDown));
        settingBtn.setBounds(btnPlayXLeft, btnPlayY, btnSmallSize, btnSmallSize);

        ImageButton aboutBtn = new ImageButton(new TextureRegionDrawable(Assets.playBtn), new TextureRegionDrawable(Assets.playBtnDown));
        aboutBtn.setBounds(btnPlayX + btnSize, btnPlayY, btnSmallSize, btnSmallSize);

        ImageButton helpBtn = new ImageButton(new TextureRegionDrawable(Assets.settingBtn), new TextureRegionDrawable(Assets.settingBtnDown));
        helpBtn.setBounds(btnPlayX, btnPlayY - btnSmallSize, btnSmallSize, btnSmallSize);

        ImageButton exitBtn = new ImageButton(new TextureRegionDrawable(Assets.settingBtn), new TextureRegionDrawable(Assets.settingBtnDown));
        exitBtn.setBounds(btnPlayX + btnSmallSize, btnPlayY - btnSmallSize, btnSmallSize, btnSmallSize);

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
        exitBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                addActor(new GameExitDialog(MainScreen.this,
                        "你确定要退出吗?"));
                super.touchUp(event, x, y, pointer, button);
            }
        });
        addActor(theme);
        addActor(playBtn);
        addActor(settingBtn);
        addActor(helpBtn);
        addActor(exitBtn);
        addActor(aboutBtn);
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
