package com.ads.puzzle.fifa.screen;

import com.ads.puzzle.fifa.Assets;
import com.ads.puzzle.fifa.Puzzle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


/**
 * Created by Ads on 2014/6/22.
 */
public class MainScreen extends BaseScreen {
    private Puzzle puzzle;

    public MainScreen(Puzzle p) {
        super(p);
        puzzle = p;
    }

    @Override
    public void show() {
        super.show();
        Image theme = new Image(Assets.theme);
        float themeSize = Assets.WIDTH * 3 / 4;
        theme.setBounds((Assets.WIDTH - themeSize) / 2, Assets.HEIGHT / 2, themeSize, themeSize);

        float btnW = Assets.WIDTH / 2;
        float btnH = btnW / 5.5f;
        float btnPlayX = (Assets.WIDTH - btnW) / 2;
        float btnPlayY = Assets.HEIGHT / 3;
        ImageButton playBtn = new ImageButton(new TextureRegionDrawable(Assets.playBtn), new TextureRegionDrawable(Assets.playDownBtn));
        playBtn.setBounds(btnPlayX, btnPlayY, btnW, btnH);
        ImageButton helpBtn = new ImageButton(new TextureRegionDrawable(Assets.helpBtn), new TextureRegionDrawable(Assets.helpDownBtn));
        helpBtn.setBounds(btnPlayX, btnPlayY - btnH * 3 / 2, btnW, btnH);

        ImageButton settingBtn = new ImageButton(new TextureRegionDrawable(Assets.settingBtn), new TextureRegionDrawable(Assets.settingDownBtn));
        settingBtn.setBounds(btnPlayX, btnPlayY - 3 * btnH, btnW, btnH);
        ImageButton exitBtn = new ImageButton(new TextureRegionDrawable(Assets.exitBtn), new TextureRegionDrawable(Assets.exitDownBtn));
        exitBtn.setBounds(btnPlayX, btnPlayY - 9 * btnH / 2, btnW, btnH);

        playBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                puzzle.setScreen(new LevelScreen(puzzle));
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
                puzzle.setScreen(new SettingScreen(puzzle));
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
                puzzle.setScreen(new ReadmeScreen(puzzle, MainScreen.this));
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
                puzzle.getPEvent().exit();
                super.touchUp(event, x, y, pointer, button);
            }
        });

        addActor(theme);
        addActor(playBtn);
        addActor(settingBtn);
        addActor(helpBtn);
        addActor(exitBtn);
    }

    @Override
    public void pause() {
    }

    @Override
    public void dispose() {
        getGameFont().dispose();
        getStage().dispose();
    }
}
