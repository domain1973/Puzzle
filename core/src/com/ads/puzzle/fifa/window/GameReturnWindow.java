package com.ads.puzzle.fifa.window;

import com.ads.puzzle.fifa.Assets;
import com.ads.puzzle.fifa.Puzzle;
import com.ads.puzzle.fifa.screen.LevelScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Administrator on 2014/7/20.
 */
public class GameReturnWindow extends BaseWindow {

    private Puzzle game;
    private float w;
    private float x;
    private float y;
    private float space;
    private float yspace;
    private Image[] star_nulls;
    private Image[] stars;
    private Stage stage;

    public GameReturnWindow(Puzzle game, Stage stage, BitmapFont font) {
        super("",  new Window.WindowStyle(font, Color.WHITE, new TextureRegionDrawable(
                Assets.winBg)));
        this.game = game;
        this.stage = stage;
        create();
    }

    public void create() {
        stage.addActor(layerBg);
        w = (float) Gdx.graphics.getWidth() * 4 / 5;
        x = (Gdx.graphics.getWidth() - w) / 2;
        y = (Gdx.graphics.getHeight() - w) / 2;
        setBounds(x, y, w, w);
        space = w / 12;
        yspace = w * 0.6f;

        float star_w = space * 3f;
        float ym = w * 0.2f;
        float v = (w - star_w) / 2;
        ImageButton suspend = new ImageButton(new TextureRegionDrawable(Assets.suspendBtn), new TextureRegionDrawable(Assets.suspendBtn));
        suspend.setBounds(v - star_w, 0, star_w, star_w);
        ImageButton refresh = new ImageButton(new TextureRegionDrawable(Assets.musicBtn), new TextureRegionDrawable(Assets.musicBtn));
        refresh.setBounds(v, 0, star_w, star_w);
        ImageButton next = new ImageButton(new TextureRegionDrawable(Assets.nextBtn), new TextureRegionDrawable(Assets.nextBtn));
        next.setBounds(v + star_w, 0, star_w, star_w);
        Gdx.input.setInputProcessor(stage);
        suspend.addListener(new InputListener() {
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
        addStars();
        addActor(suspend);
        addActor(refresh);
        addActor(next);
    }

    private void addStars() {
        float star_w = space * 2f;
        float v = (w - star_w) / 2;
        star_nulls = new Image[5];
        stars = new Image[5];
        for (int i = 0; i < 5; i++) {
            star_nulls[i] = new Image(Assets.star_null);
            stars[i] = new Image(Assets.star);
            int t = i - 2;
            star_nulls[i].setBounds(v + t * star_w, w * 3 / 5, star_w, star_w);
            stars[i].setBounds(v + t * star_w, w * 3 / 5, star_w, star_w);
            addActor(star_nulls[i]);
        }
    }
}
