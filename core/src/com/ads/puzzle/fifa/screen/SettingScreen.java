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
    Puzzle game;
    private float btn_font_x;
    private float btn_font_x1;
    private float themeSize;
    private float btn_font_y;
    private float btn_font_y1;
    private float btn_font_y2;
    private float btn_font_y3;

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

        ImageButton playBtn = new ImageButton(new TextureRegionDrawable(Assets.playBtn), new TextureRegionDrawable(Assets.playBtnDown));
        float x = (Assets.WIDTH - playBtn.getWidth()) / 2;
        btn_font_x = x + playBtn.getWidth() / 3 - 30;
        btn_font_x1 = x + playBtn.getWidth() * 2 / 3;
        float btn_y = Assets.HEIGHT * 2 / 3;
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
        ImageButton aboutBtn = new ImageButton(new TextureRegionDrawable(Assets.settingBtn), new TextureRegionDrawable(Assets.settingBtnDown));
        float btn_y3 = btn_y - playBtn.getHeight() * 9/2;
        aboutBtn.setPosition(x, btn_y3);
        btn_font_y3 = btn_y3 + playBtn.getHeight();

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
        helpBtn.addListener(new InputListener() {
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
        addActor(playBtn);
        addActor(settingBtn);
        addActor(helpBtn);
        addActor(aboutBtn);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        getBatch().begin();
        getFont().draw(getBatch(), "总计: " + getStarNum(), x_num, y_num);
        getFont().draw(getBatch(), "音效", btn_font_x, btn_font_y);
        getFont().draw(getBatch(), "关", btn_font_x1, btn_font_y);
        getFont().draw(getBatch(), "音乐", btn_font_x, btn_font_y1);
        getFont().draw(getBatch(), "关", btn_font_x1, btn_font_y1);
        getFont().draw(getBatch(), "重置", btn_font_x, btn_font_y2);
        getFont().draw(getBatch(), "游戏", btn_font_x1, btn_font_y2);
        getFont().draw(getBatch(), "关", btn_font_x, btn_font_y3);
        getFont().draw(getBatch(), "于", btn_font_x1, btn_font_y3);
        getBatch().end();
    }
}
