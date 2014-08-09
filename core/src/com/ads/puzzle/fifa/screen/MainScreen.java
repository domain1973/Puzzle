package com.ads.puzzle.fifa.screen;

import com.ads.puzzle.fifa.Assets;
import com.ads.puzzle.fifa.Puzzle;
import com.ads.puzzle.fifa.Settings;
import com.ads.puzzle.fifa.actors.AnimationPiece;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Ads on 2014/6/22.
 */
public class MainScreen extends ScreenAdapter {
    private Puzzle game;
    private Stage stage;
    private ImageButton playBtn;
    private ImageButton settingBtn;
    private BitmapFont font;
    private float mi_x;
    private float mi_y;
    private float btn_font_x;
    private float themeSize;
    private boolean isShow;

    public MainScreen(Puzzle game) {
        this.game = game;
        font = new BitmapFont(Gdx.files.internal("puzzle.fnt"),
                Gdx.files.internal("puzzle.png"), false);
    }

    @Override
    public void show() {
        if (!isShow) {
            Image bgImg = new Image(Assets.startBg);
            float w = Assets.WIDTH / (float) Assets.startBg.getRegionWidth();
            float h = Assets.HEIGHT / (float) Assets.startBg.getRegionHeight();
            bgImg.setScale(w, h);
            playBtn = new ImageButton(new TextureRegionDrawable(Assets.playBtn), new TextureRegionDrawable(Assets.playBtnDown));
            playBtn.setPosition((Assets.WIDTH - playBtn.getWidth()) / 2, Assets.HEIGHT / 3);
            settingBtn = new ImageButton(new TextureRegionDrawable(Assets.settingBtn), new TextureRegionDrawable(Assets.settingBtnDown));
            settingBtn.setPosition((Gdx.graphics.getWidth() - playBtn.getWidth()) / 2, Gdx.graphics.getHeight() / 4);

            stage = new Stage();
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
                    game.setScreen(new SettingScreen(game));
                    return true;
                }
            });
            Image themeImage = new Image(Assets.theme);
            themeSize = Assets.WIDTH / 2;
            themeImage.setBounds((Assets.WIDTH - themeSize) / 2, Assets.HEIGHT * 3 / 5, themeSize, themeSize);

            stage.addActor(bgImg);
            stage.addActor(themeImage);
            stage.addActor(playBtn);
            stage.addActor(settingBtn);

            mi_x = (Gdx.graphics.getWidth() - themeSize) / 2 - 80;
            mi_y = Gdx.graphics.getHeight() * 3 / 5 + themeSize;
            btn_font_x = (Gdx.graphics.getWidth() - playBtn.getWidth()) / 2 + 50;

            addAnimation();
            isShow = true;
        }
        Gdx.input.setInputProcessor(stage); // 设置输入接收器
    }

    private void addAnimation() {
        float duration = 4f;
        float maxwidth = Gdx.graphics.getWidth() - Assets.SMALL_PIECE_SIZE;
        float maxheight = Gdx.graphics.getHeight() - Assets.SMALL_PIECE_SIZE;
        for (TextureRegion textureRegion : Assets.cubes) {
            Image image = new Image(textureRegion);
            image.setPosition(MathUtils.random(0, maxwidth), MathUtils.random(0, Gdx.graphics.getHeight()));
            SequenceAction moveAction = Actions.sequence(Actions.moveTo(MathUtils.random(0,
                            maxwidth), MathUtils.random(0, maxheight), duration),
                    Actions.moveBy(MathUtils.random(0, maxwidth), MathUtils.random(0,
                            maxheight), duration), Actions.rotateTo(360, duration)
            ); //移动方向和地点随机
            //image.addAction(Actions.parallel(moveAction)); //所有action并行
            image.addAction(Actions.forever(moveAction));
            stage.addActor(image);
        }
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

        stage.getBatch().begin();
        font.setScale(1.0f);// 字体比例大小
        font.draw(stage.getBatch(), "迷", mi_x, mi_y);
        font.draw(stage.getBatch(), "宫", mi_x + themeSize, mi_y);
        font.setScale(0.25f);// 字体比例大小
        font.draw(stage.getBatch(), "开       始", btn_font_x, Gdx.graphics.getHeight() / 3 + 45);
        font.draw(stage.getBatch(), "选       项", btn_font_x, Gdx.graphics.getHeight() / 4 + 45);
        stage.getBatch().end();
    }

    @Override
    public void pause() {
        Settings.save();
    }

    @Override
    public void dispose() {
        font.dispose();
        stage.dispose();
    }
}
