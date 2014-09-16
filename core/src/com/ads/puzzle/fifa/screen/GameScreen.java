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
import com.ads.puzzle.fifa.window.ResultWindow;
import com.ads.puzzle.fifa.window.SupsendWin;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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

    private PieceDetector gestureDetector;
    private boolean openResultWin;
    private boolean isPass;
    private boolean isSuspend;
    private SupsendWin gameWindow;
    private InputMultiplexer multiplexer;
    private Actor[] pieces;
    private boolean isUsedHelp;
    private boolean isUsingHelp;

    private int areaId;
    private int seconds;
    private String timeStr;
    private float x_light;
    private boolean isShow;
    private int starNum;
    private Label labTime;
    private Label labCount;

    private ScheduledExecutorService executorGateEnd;
    private ScheduledExecutorService executStarCount;
    private ScheduledExecutorService executTime;
    private ParticleEffect effect;

    public GameScreen(Puzzle puzzle, int lv, int num) {
        super(puzzle);
        level = lv;
        gateNum = num;
        executorGateEnd = Executors.newSingleThreadScheduledExecutor();
        isShow = true;
        isUsingHelp = true;
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
            addLabels();
            initEffect();
            createTimer();
            isShow = false;
        }
    }

    private void createTopBar() {
        super.createBtns();
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

        ImageButton sos = new ImageButton(new TextureRegionDrawable(Assets.light));
        x_light = Assets.WIDTH - 3 * Assets.TOPBAR_HEIGHT - 5;
        sos.setBounds(x_light, getY_bar(), Assets.TOP_BTN_SIZE, Assets.TOP_BTN_SIZE);
        sos.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (Settings.helpNum > 0) {
                    getPuzzle().getPEvent().sos(GameScreen.this);
                } else {
                    getPuzzle().getPEvent().invalidateSos();
                }
                super.touchUp(event, x, y, pointer, button);
            }
        });
        addActor(sos);

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
                Settings.helpNum++;
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
                suspendTimer();
                gameWindow = new SupsendWin(getPuzzle(), getGameFont(), GameScreen.this, level);
                addActor(gameWindow);
                super.touchUp(event, x, y, pointer, button);
            }
        });
        addActor(suspend);

        returnBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                getPuzzle().setScreen(new GateScreen(getPuzzle(), level));
                return true;
            }
        });
    }

    private void addLabels() {
        float fontSize = 36;//default
        BitmapFont gameFont = getGameFont();
        int scale = (int)(Assets.TOPBAR_HEIGHT / fontSize);
        gameFont.setScale(scale);
        labTime = new Label("", new Label.LabelStyle(gameFont, Color.YELLOW));
        labTime.setPosition((Assets.WIDTH  - Assets.PIECE_SIZE/2)/2, Assets.HEIGHT - Assets.TOPBAR_HEIGHT - Assets.PIECE_SIZE);
        addActor(labTime);
        labCount = new Label("", new Label.LabelStyle(gameFont, Color.RED));
        labCount.setPosition(x_light, Assets.HEIGHT - Assets.TOPBAR_HEIGHT/2);
        addActor(labCount);
        Label labC = new Label("挑战", new Label.LabelStyle(gameFont, Color.WHITE));
        labC.setPosition(Assets.SPRITESIZE *3/2,  Assets.PIECE_SIZE + Assets.TOPBAR_HEIGHT);
        addActor(labC);
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
        labTime.setText(timeStr);
        labCount.setText(Settings.helpNum +"");
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
                multiplexer.addProcessor(gestureDetector); // 添加手势识别
                multiplexer.addProcessor(getStage());
            }
        }
    }

    private void reset() {
        areaId = 0;
        areaCtrl.handler();
        pieceCtrl.handler();
    }

    private void handlePass() {
        if (!isPass) {
            Gdx.input.setInputProcessor(multiplexer);
            handleGate();
        }
        if (openResultWin) {
            computerStarNum();
            addActor(new ResultWindow(this, starNum));
            openResultWin = false;
        }
    }

    private void computerStarNum() {
        int minute = getSeconds()/60;
        if (minute <= Answer.GRADE_1) {
            starNum = 3;
        } else if (minute > Answer.GRADE_1 && minute <= Answer.GRADE_2) {
            starNum = 2;
        } else if (minute > Answer.GRADE_3 && minute <= Answer.GRADE_4){
            starNum = 1;
        } else {
            starNum = 0;
        }
        Answer.gateStars.set(challengeCtrl.getGateNum(), starNum);
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
            executorGateEnd.schedule(runner, 1500, TimeUnit.MILLISECONDS);
            isPass = true;
        }
    }

    private void suspendTimer() {
        isSuspend = true;
    }

    public void resumeTimer() {
        isSuspend = false;
    }

    private void createTimer() {
        isSuspend = false;
        seconds = 0;
        executTime = Executors.newSingleThreadScheduledExecutor();
        executTime.scheduleAtFixedRate(new Runnable() {
            public void run() {
                if (!isSuspend) {
                    seconds++; //
                    buildTimeStr();
                }
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
    }

    public int getLevel() {
        return level;
    }

    public void return2init() {
        isPass = false;
        if (!executTime.isShutdown()) {
            executTime.shutdown();
        }
        createTimer();
    }

    public void refreshGame() {
        return2init();
        pieceCtrl.handler();
    }

    public int getSeconds() {
        return seconds;
    }

    public void useSos() {
        multiplexer.removeProcessor(gestureDetector);
        multiplexer.removeProcessor(getStage());
        isUsingHelp = true;
        isUsedHelp = true;
        pieceCtrl.handler();
    }
}
