package com.ads.puzzle.fifa.window;

import com.ads.puzzle.fifa.Assets;
import com.ads.puzzle.fifa.Puzzle;
import com.ads.puzzle.fifa.screen.AboutScreen;
import com.ads.puzzle.fifa.screen.GameScreen;
import com.ads.puzzle.fifa.screen.GateScreen;
import com.ads.puzzle.fifa.screen.LevelScreen;
import com.ads.puzzle.fifa.screen.ReadmeScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Administrator on 2014/7/20.
 */
public class SupsendWin extends BaseWindow {

    private Puzzle game;
    private GameScreen gameScreen;
    private float win_H;
    private float win_Y;
    private Stage stage;
    private int level;

    public SupsendWin(Puzzle game, Stage stage, BitmapFont font, GameScreen gameScreen, int level) {
        super("暂停",  new Window.WindowStyle(font, Color.WHITE, new TextureRegionDrawable(
                Assets.gameBg)));
        this.game = game;
        this.stage = stage;
        this.gameScreen = gameScreen;
        this.level = level;
        create();
    }

    public void create() {
        stage.addActor(layerBg);
        float btnSize = Assets.WIDTH/6;
        win_H = btnSize* 3;
        win_Y = (Assets.HEIGHT - win_H) / 2;
        setBounds(0, win_Y, Assets.WIDTH, win_H);
        float ym = win_H /4;
        float v = (Assets.WIDTH - btnSize*3)/2;
        float v1 = Assets.WIDTH/5;

        ImageButton gate = new ImageButton(new TextureRegionDrawable(Assets.gate), new TextureRegionDrawable(Assets.gate));
        gate.setBounds(v, ym-btnSize/2, btnSize, btnSize);
        ImageButton share = new ImageButton(new TextureRegionDrawable(Assets.share), new TextureRegionDrawable(Assets.share));
        share.setBounds(v+btnSize, ym-btnSize/2, btnSize, btnSize);
        ImageButton next = new ImageButton(new TextureRegionDrawable(Assets.continueTr), new TextureRegionDrawable(Assets.continueTr));
        next.setBounds(v + btnSize*2, ym-btnSize/2, btnSize, btnSize);

        ImageButton music = new ImageButton(new TextureRegionDrawable(Assets.music), new TextureRegionDrawable(Assets.music));
        music.setBounds(v1, ym * 2, btnSize, btnSize);
        ImageButton sound = new ImageButton(new TextureRegionDrawable(Assets.sound), new TextureRegionDrawable(Assets.sound));
        sound.setBounds(v1 + btnSize, ym * 2, btnSize, btnSize);
        ImageButton about = new ImageButton(new TextureRegionDrawable(Assets.about), new TextureRegionDrawable(Assets.about));
        about.setBounds(v1 + btnSize*2, ym * 2, btnSize, btnSize);
        ImageButton help = new ImageButton(new TextureRegionDrawable(Assets.help), new TextureRegionDrawable(Assets.help));
        help.setBounds(v1 + btnSize*3, ym * 2, btnSize, btnSize);

        Gdx.input.setInputProcessor(stage);
        about.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new AboutScreen(game, gameScreen));
                super.touchUp(event, x, y, pointer, button);
            }
        });
        help.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new ReadmeScreen(game, gameScreen));
                super.touchUp(event, x, y, pointer, button);
            }
        });
        gate.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GateScreen(game, level));
                super.touchUp(event, x, y, pointer, button);
            }
        });

        addActor(music);
        addActor(sound);
        addActor(about);
        addActor(help);
        addActor(gate);
        addActor(share);
        addActor(next);
    }
}
