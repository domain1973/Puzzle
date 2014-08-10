package com.ads.puzzle.fifa.screen;

import com.ads.puzzle.fifa.Answer;
import com.ads.puzzle.fifa.Assets;
import com.ads.puzzle.fifa.Puzzle;
import com.ads.puzzle.fifa.Settings;
import com.ads.puzzle.fifa.actors.Area;
import com.ads.puzzle.fifa.actors.Piece;
import com.ads.puzzle.fifa.controller.AreaController;
import com.ads.puzzle.fifa.controller.ChallengeController;
import com.ads.puzzle.fifa.controller.IController;
import com.ads.puzzle.fifa.controller.PieceController;
import com.ads.puzzle.fifa.listener.PieceDetector;
import com.ads.puzzle.fifa.listener.PieceListener;
import com.ads.puzzle.fifa.window.GameReturnWindow;
import com.ads.puzzle.fifa.window.HelpStarWindow;
import com.ads.puzzle.fifa.window.ResultWindow;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2014/6/24.
 */
public class GameScreen extends BaseScreen {
    private int level;
    private int gateNum;
    private AreaController areaCtrl;
    private PieceController pieceCtrl;
    private ChallengeController challengeCtrl;
    private ScheduledExecutorService executor;
    private PieceDetector gestureDetector;
    private boolean openResultWin;
    private boolean isPass;
    private GameReturnWindow gameWindow;
    private InputMultiplexer multiplexer;
    private Actor[] pieces;

    private boolean isUsedHelp;
    private boolean isUsingHelp = true;
    private ScheduledExecutorService executStarCount;
    private ParticleEffect effect;
    private int areaId;
    private String timeStr;

    public GameScreen(Puzzle game, int level, int gateNum) {
        super(game);
        this.level = level;
        this.gateNum = gateNum;
        executor = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void show() {
        super.show();
        timeStr = "00:00";
        areaCtrl = new AreaController(level, IController.AREA_CTRL);
        addActor(areaCtrl);
        pieceCtrl = new PieceController(IController.PIECE_CTRL);
        addActor(pieceCtrl); // 添加块组到舞台
        pieces = pieceCtrl.getChildren().begin();
        challengeCtrl = new ChallengeController(level, gateNum, IController.CHALLENGE_CTRL);
        addActor(challengeCtrl);

        multiplexer = new InputMultiplexer(); // 多输入接收器
        gestureDetector = new PieceDetector(stage, new PieceListener(stage));
        multiplexer.addProcessor(gestureDetector); // 添加手势识别
        multiplexer.addProcessor(stage); // 添加舞台
        Gdx.input.setInputProcessor(multiplexer); // 设置多输入接收器为接收器

        createTopBar();
        initEffect();
        countTime();
    }

    private void createTopBar() {
//        ImageButton help = new ImageButton(new TextureRegionDrawable(Assets.help));
//        help.setBounds(0, y_bar, Assets.TOPBAR_HEIGHT, Assets.TOPBAR_HEIGHT);
//        addActor(help);
        ImageButton unlock = new ImageButton(new TextureRegionDrawable(Assets.unlock));
        unlock.setBounds(Gdx.graphics.getWidth() - 3 * Assets.TOPBAR_HEIGHT, y_bar, Assets.TOPBAR_HEIGHT, Assets.TOPBAR_HEIGHT);
        unlock.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                addActor(new HelpStarWindow(GameScreen.this));
                super.touchUp(event, x, y, pointer, button);
            }
        });
        addActor(unlock);
        ImageButton refresh = new ImageButton(new TextureRegionDrawable(Assets.refresh));
        refresh.setBounds(Gdx.graphics.getWidth() - 2 * Assets.TOPBAR_HEIGHT, y_bar, Assets.TOPBAR_HEIGHT, Assets.TOPBAR_HEIGHT);
        addActor(refresh);
        ImageButton menu = new ImageButton(new TextureRegionDrawable(Assets.menu));
        menu.setBounds(Gdx.graphics.getWidth() - Assets.TOPBAR_HEIGHT, y_bar, Assets.TOPBAR_HEIGHT, Assets.TOPBAR_HEIGHT);
        menu.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gameWindow = new GameReturnWindow(game, stage, font);
                addActor(gameWindow);
                super.touchUp(event, x, y, pointer, button);
            }
        });
        addActor(menu);
    }

    private void initEffect() {
        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("data/test.p"), Gdx.files.internal("data/"));
    }
    private void changeStar() {
        executStarCount = Executors.newSingleThreadScheduledExecutor();
        executStarCount.scheduleAtFixedRate( new Runnable() {
            public void run() {
                areaId++;
            }
        }, 1000, 2000, TimeUnit.MILLISECONDS);
    }
    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        font.draw(batch, timeStr, 0, Assets.HEIGHT);
        batch.end();
        handleHelp();
        handlePass();
    }

    private void handleHelp() {
        if (isUsedHelp) {
            if (isUsingHelp) {
                changeStar();
                isUsingHelp = false;
            }
            if (areaId < 3) {
                int temp = areaId;//防止定时器修改值不同步
                Area area = (Area) areaCtrl.getChildren().get(areaId);
                batch.begin();
                effect.setPosition(area.getX() + area.getWidth() / 2, area.getY() + area.getHeight() / 2);
                effect.draw(batch, Gdx.graphics.getDeltaTime());
                batch.end();
                //TODO get answer
                String[] answers = Answer.values[gateNum].split(",");
                String answer = answers[temp];
                int pieceId = answer.charAt(0) - 48;
                Piece piece = (Piece)pieces[pieceId];
                piece.setBounds(area.getX(), area.getY(), Assets.PIECE_SIZE, Assets.PIECE_SIZE);
                piece.setOrientation(answer.charAt(1)-48);
                piece.setArea(temp);
                area.setPieceId(pieceId);
            } else {
                isUsedHelp = false;
                areaId = -1;
                executStarCount.shutdown();
            }
        }
    }

    private void handlePass() {
        if (!isPass) {
            Gdx.input.setInputProcessor(multiplexer);
            handleGate();
        }
        if (openResultWin) {
            addActor(new ResultWindow(this));
            openResultWin = false;
        }
    }

    private void handleGate() {
        if (gestureDetector.isPass(challengeCtrl.getGateNum())) {
            Gdx.input.setInputProcessor(null);
            executTime.shutdown();
            Runnable runner = new Runnable() {
                public void run() {
                    openResultWin = true; //关卡结束
                }
            };
            executor.schedule(runner, 1500, TimeUnit.MILLISECONDS);
            isPass = true;
        }
    }

    private int seconds;
    private ScheduledExecutorService executTime;
    private void countTime() {
        seconds = 0;
        executTime = Executors.newSingleThreadScheduledExecutor();
        executTime.scheduleAtFixedRate(new Runnable() {
            public void run() {
                seconds++; //
                buildTimeStr();
            }

            private void buildTimeStr() {
                String str0 = "%d";
                String str1 = "%d";
                int minute = seconds / 60;
                int second = seconds % 60;
                if (minute < 10) {
                    str0 = "0%d";
                }
                if (second < 10) {
                    str1 = "0%d";
                }
                timeStr = String.format(str0 + ":" + str1, minute, second);
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    @Override
    public void pause() {
        Settings.save();
    }

    public int getLevel() {
        return level;
    }

    public void return2init() {
        isPass = false;
        countTime();
    }

    public void useHelp() {
        isUsedHelp = true;
    }

    public int getSeconds() {
        return seconds;
    }
}
