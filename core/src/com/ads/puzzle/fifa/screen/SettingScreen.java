package com.ads.puzzle.fifa.screen;

import com.ads.puzzle.fifa.Assets;
import com.ads.puzzle.fifa.Puzzle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Administrator on 2014/6/22.
 */
public class SettingScreen extends OtherScreen {
    private Puzzle game;

    public SettingScreen(Puzzle game) {
        super(game);
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        returnBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                getPuzzle().setScreen(getPuzzle().getMainScreen());
                super.touchUp(event, x, y, pointer, button);
            }
        });

        float btnW = Assets.WIDTH /2;
        float btnH = btnW / 5.5f;
        ImageButton musicBtn = new ImageButton(new TextureRegionDrawable(Assets.musicOpenBtn), new TextureRegionDrawable(Assets.musicOpenBtn));
        float x = (Assets.WIDTH - btnW) / 2;
        float btn_y = Assets.HEIGHT * 2 / 3;
        musicBtn.setBounds(x, btn_y, btnW, btnH);
        ImageButton soundBtn = new ImageButton(new TextureRegionDrawable(Assets.soundOpenBtn), new TextureRegionDrawable(Assets.soundOpenBtn));
        float btn_y1 = btn_y -btnH * 3 / 2;
        soundBtn.setBounds(x, btn_y1, btnW, btnH);
        ImageButton resetGameBtn = new ImageButton(new TextureRegionDrawable(Assets.resetGameBtn), new TextureRegionDrawable(Assets.resetGameDownBtn));
        float btn_y2 = btn_y - btnH * 3;
        resetGameBtn.setBounds(x, btn_y2, btnW, btnH);
        ImageButton aboutBtn = new ImageButton(new TextureRegionDrawable(Assets.aboutBtn), new TextureRegionDrawable(Assets.aboutDownBtn));
        float btn_y3 = btn_y - btnH * 9/2;
        aboutBtn.setBounds(x, btn_y3, btnW, btnH);

        musicBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
            }
        });
        resetGameBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                getPuzzle().getPEvent().resetGame();
                super.touchUp(event, x, y, pointer, button);
            }
        });
        getStarLabel().setText("总计:" + getStarNum());
        addActor(musicBtn);
        addActor(soundBtn);
        addActor(resetGameBtn);
        addActor(aboutBtn);
    }
}
