package com.ads.puzzle.fifa.window;

import com.ads.puzzle.fifa.Answer;
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
    private int starNum = 1;
    private boolean exeTimer = false;
    private boolean end = false;
    private ImageButton suspend;
    private ImageButton refresh;
    private ImageButton next;
    private Group group;
    private ChallengeController challengeCtrl;

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
        space = w / 5;
        setBounds(x, y, w, w);
        addButtons();
        addListeners();
        addStars();
        addActor(suspend);
        addActor(refresh);
        addActor(next);
        initEffect();
        group = gameScreen.getStage().getRoot();
        challengeCtrl = (ChallengeController) group.findActor(IController.CHALLENGE_CTRL);
        setStarNum(challengeCtrl);
        Gdx.input.setInputProcessor(gameScreen.getStage());
    }

    private void addButtons() {
        float btn_size = space;
        float v = (w - btn_size) / 2;
        suspend = new ImageButton(new TextureRegionDrawable(Assets.suspendBtn), new TextureRegionDrawable(Assets.suspendBtn));
        suspend.setBounds(v - btn_size, 0, btn_size, btn_size);
        refresh = new ImageButton(new TextureRegionDrawable(Assets.musicBtn), new TextureRegionDrawable(Assets.musicBtn));
        refresh.setBounds(v, 0, btn_size, btn_size);
        next = new ImageButton(new TextureRegionDrawable(Assets.nextBtn), new TextureRegionDrawable(Assets.nextBtn));
        next.setBounds(v + btn_size, 0, btn_size, btn_size);
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
                ((AreaController) group.findActor(IController.AREA_CTRL)).handler();
                challengeCtrl.handler();
                ((PieceController) group.findActor(IController.PIECE_CTRL)).handler();
                int gateNum = challengeCtrl.getGateNum();
                if (gateNum > gameScreen.getGame().getPassGateNum()) {
                    gameScreen.getGame().setPassGateNum(gateNum);
                }
                if (Answer.gateStars.size() <= gateNum) {
                    Answer.gateStars.add(0);
                } 
                ResultWindow.this.remove();
                gameScreen.return2init();
                super.touchUp(event, x, y, pointer, button);
            }
        });
    }

    private void setStarNum(ChallengeController challengeCtrl) {
        int minute = gameScreen.getSeconds()/60;
        if (minute <= 3) {
            starNum = 3;
        } else if (minute > 3 && minute <= 6) {
            starNum = 2;
        }
        Answer.gateStars.set(challengeCtrl.getGateNum(), starNum);
    }

    private void initEffect() {
        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("data/test.p"), Gdx.files.internal("data/"));
    }

    private void addStars() {
        float star_w = space;
        float v = (w - star_w) / 2;
        star_nulls = new Image[3];
        stars = new Image[3];
        for (int i = 0; i < 3; i++) {
            star_nulls[i] = new Image(Assets.star_null);
            stars[i] = new Image(Assets.star);
            int t = i - 1;
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
