package com.ads.puzzle.fifa.window;

import com.ads.puzzle.fifa.Assets;
import com.ads.puzzle.fifa.controller.AreaController;
import com.ads.puzzle.fifa.controller.ChallengeController;
import com.ads.puzzle.fifa.controller.IController;
import com.ads.puzzle.fifa.controller.PieceController;
import com.ads.puzzle.fifa.screen.GameScreen;
import com.ads.puzzle.fifa.screen.GateScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2014/7/20.
 */
public class ResultWindow extends BaseWindow {
    private GameScreen gameScreen;
    private float w;
    private float x;
    private float y;
    private float space;
    private Image[] star_nulls;
    private Image[] stars;
    private ScheduledExecutorService executStarCount;
    private ParticleEffect effect;
    private int starIndex;
    private int starNum = 5;
    private boolean exeTimer = false;
    private boolean end = false;
    private ImageButton suspend;
    private ImageButton refresh;
    private ImageButton next;

    public ResultWindow(GameScreen gameScreen) {
        super("你是天才!", new Window.WindowStyle(gameScreen.getFont(), Color.WHITE, new TextureRegionDrawable(
                Assets.resultBg)));
        this.gameScreen = gameScreen;
        create();
    }

    public void create() {
        gameScreen.getStage().addActor(layerBg);
        w = Assets.WIDTH * 4 / 5;
        x = (Assets.WIDTH - w) / 2;
        y = (Assets.HEIGHT - w) / 2;
        setBounds(x, y, w, w);
        addButtons();
        addListeners();
        addStars();
        addActor(suspend);
        addActor(refresh);
        addActor(next);
        initEffect();
        Gdx.input.setInputProcessor(gameScreen.getStage());
    }

    private void addButtons() {
        space = w / 12;
        float star_w = space * 3f;
        float v = (w - star_w) / 2;
        suspend = new ImageButton(new TextureRegionDrawable(Assets.suspendBtn), new TextureRegionDrawable(Assets.suspendBtn));
        suspend.setBounds(v - star_w, 0, star_w, star_w);
        refresh = new ImageButton(new TextureRegionDrawable(Assets.musicBtn), new TextureRegionDrawable(Assets.musicBtn));
        refresh.setBounds(v, 0, star_w, star_w);
        next = new ImageButton(new TextureRegionDrawable(Assets.nextBtn), new TextureRegionDrawable(Assets.nextBtn));
        next.setBounds(v + star_w, 0, star_w, star_w);
    }

    private void addListeners() {
        suspend.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                layerBg.remove();
                gameScreen.getGame().setScreen(new GateScreen(gameScreen.getGame(), gameScreen.getLevel()));
                super.touchUp(event, x, y, pointer, button);
            }
        });
        next.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                layerBg.remove();
                Group group = gameScreen.getStage().getRoot();
                ((AreaController) group.findActor(IController.AREA_CTRL)).handler();
                ChallengeController challengeController = (ChallengeController) group.findActor(IController.CHALLENGE_CTRL);
                challengeController.handler();
                ((PieceController) group.findActor(IController.PIECE_CTRL)).handler();
                int gateNum = challengeController.getGateNum();
                if (gateNum > gameScreen.getGame().getPassGateNum()) {
                    gameScreen.getGame().setPassGateNum(gateNum);
                }
                ResultWindow.this.remove();
                gameScreen.return2init();
                super.touchUp(event, x, y, pointer, button);
            }
        });
    }

    private void initEffect() {
        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("data/test.p"), Gdx.files.internal("data/"));
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

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (!end) {
            if (!exeTimer) {
                changeStar();
                exeTimer = true;
            }
            if (starIndex < starNum) {
                Image star = stars[starIndex];
                float space = star.getWidth() / 2;
                effect.setPosition(star.getX() + getX() + space, star.getY() + getY() + space);
                effect.draw(batch, Gdx.graphics.getDeltaTime());
                addActor(star);
            } else {
                end = true;
            }
        }
    }

    private void changeStar() {
        executStarCount = Executors.newSingleThreadScheduledExecutor();
        executStarCount.scheduleAtFixedRate( new Runnable() {
            public void run() {
                starIndex++; //
            }
        }, 1000, 1000, TimeUnit.MILLISECONDS);
    }
}
