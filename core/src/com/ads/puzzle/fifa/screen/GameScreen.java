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
import com.ads.puzzle.fifa.window.DefaultDialog;
import com.ads.puzzle.fifa.window.SupsendWin;
import com.ads.puzzle.fifa.window.ResultWindow;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
    private boolean openLightWin;
    private SupsendWin gameWindow;
    private InputMultiplexer multiplexer;
    private Actor[] pieces;

    private boolean isUsedHelp;
    private boolean isUsingHelp = true;
    private ScheduledExecutorService executStarCount;
    private ScheduledExecutorService executTime;
    private ParticleEffect effect;
    private int areaId;
    private int seconds;
    private String timeStr;
    private float x_light;
    private boolean isShow;
    private Image nosos;

    public GameScreen(Puzzle puzzle, int level, int gateNum) {
        super(puzzle);
        this.level = level;
        this.gateNum = gateNum;
        executor = Executors.newSingleThreadScheduledExecutor();
        isShow = true;
        nosos = new Image(Assets.nosos);
    }

    @Override
    public void show() {
        if (isShow) {
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
            gestureDetector = new PieceDetector(getStage(), new PieceListener(getStage()));
            multiplexer.addProcessor(gestureDetector); // 添加手势识别
            multiplexer.addProcessor(getStage()); // 添加舞台
            Gdx.input.setInputProcessor(multiplexer); // 设置多输入接收器为接收器

            createTopBar();
            initEffect();
            countTime();
            Image challenge = new Image(Assets.challenge);
            challenge.setBounds(Assets.spriteSize*3/2, Assets.spriteSize * 3 + Assets.TOPBAR_HEIGHT, Assets.spriteSize, Assets.spriteSize);
            addActor(challenge);
            isShow = false;
        }
    }

    private void createTopBar() {
        ImageButton help = new ImageButton(new TextureRegionDrawable(Assets.help));
        help.setBounds(Assets.WIDTH - 4 * Assets.TOPBAR_HEIGHT, getY_bar(), Assets.TOP_BTN_SIZE, Assets.TOP_BTN_SIZE);
        help.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                getPuzzle().setScreen(new ReadmeScreen(getPuzzle(), GameScreen.this));
                super.touchUp(event, x, y, pointer, button);
            }
        });
        addActor(help);

        ImageButton light = new ImageButton(new TextureRegionDrawable(Assets.light));
        x_light = Assets.WIDTH - 3 * Assets.TOPBAR_HEIGHT;
        light.setBounds(x_light, getY_bar(), Assets.TOP_BTN_SIZE, Assets.TOP_BTN_SIZE);
        light.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (Settings.getLights() > 0) {
                    Settings.sublight();
                    openLightWin = true;
                    addActor(new DefaultDialog(GameScreen.this));
                } else {
                    addActor(nosos);
                    autoCloseSos();
                }
                super.touchUp(event, x, y, pointer, button);
            }
        });
        addActor(light);
        nosos.setPosition((Assets.WIDTH - nosos.getWidth()) / 2, getY_bar() - 2*Assets.TOPBAR_HEIGHT);

        ImageButton share = new ImageButton(new TextureRegionDrawable(Assets.share));
        share.setBounds(Assets.WIDTH - 2 * Assets.TOPBAR_HEIGHT, getY_bar(), Assets.TOP_BTN_SIZE, Assets.TOP_BTN_SIZE);
        share.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Settings.addlight();
                super.touchUp(event, x, y, pointer, button);
            }
        });
        addActor(share);

        ImageButton suspend = new ImageButton(new TextureRegionDrawable(Assets.suspend));
        suspend.setBounds(Assets.WIDTH - Assets.TOPBAR_HEIGHT, getY_bar(), Assets.TOP_BTN_SIZE, Assets.TOP_BTN_SIZE);
        suspend.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gameWindow = new SupsendWin(getPuzzle(), getStage(), getFont(), GameScreen.this, level);
                addActor(gameWindow);
                super.touchUp(event, x, y, pointer, button);
            }
        });
        addActor(suspend);
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
        handleHelp();
        handlePass();
        getBatch().begin();
        if (!isPass && !openLightWin) {
            getGameFont().draw(getBatch(), Settings.getLights()+"", x_light, Assets.HEIGHT - 10);
            getGameFont().draw(getBatch(), "次", x_light + 12, Assets.HEIGHT - 30);
            getGameFont().draw(getBatch(), timeStr, 0, Assets.HEIGHT - 5);
        }
        getBatch().end();
    }

    private void handleHelp() {
        if (isUsedHelp) {
            if (isUsingHelp) {
                changeStar();
                reset();
                isUsingHelp = false;
            }
            if (areaId < 3) {
                int temp = areaId;//防止定时器修改值不同步
                Area area = (Area) areaCtrl.getChildren().get(areaId);
                getBatch().begin();
                effect.setPosition(area.getX() + area.getWidth() / 2, area.getY() + area.getHeight() / 2);
                effect.draw(getBatch(), Gdx.graphics.getDeltaTime());
                getBatch().end();
                String[] answers = Answer.VALUES[gateNum].split(",");
                String answer = answers[temp];
                int pieceId = answer.charAt(0) - 48;//Aciis转成int
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

    private void reset() {
        areaCtrl.handler();
        pieceCtrl.handler();
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

    private void autoCloseSos() {
        ScheduledExecutorService executTime = Executors.newSingleThreadScheduledExecutor();
        executTime.schedule(new Runnable() {
            public void run() {
                nosos.remove();
            }
        }, 3000, TimeUnit.MILLISECONDS);
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

    public void setOpenLightWin(boolean openLightWin) {
        this.openLightWin = openLightWin;
    }
}
