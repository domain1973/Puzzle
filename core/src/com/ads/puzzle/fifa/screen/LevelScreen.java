package com.ads.puzzle.fifa.screen;

import com.ads.puzzle.fifa.Answer;
import com.ads.puzzle.fifa.Assets;
import com.ads.puzzle.fifa.Puzzle;
import com.ads.puzzle.fifa.Settings;
import com.ads.puzzle.fifa.listener.LevelDetector;
import com.ads.puzzle.fifa.listener.LevelListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2014/6/22.
 */
public class LevelScreen extends OtherScreen {
    private ImageButton leftBtn;
    private ImageButton rightBtn;
    private int level = 0;
    private boolean backHasTouched = false;
    private float levelSize;
    private LevelListener levelListener;
    private float targetposition;

    public LevelScreen(Puzzle game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        createBtns();
        setListens();
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(getStage());
        levelListener = new LevelListener(getStage(), getPuzzle());
        multiplexer.addProcessor(new LevelDetector(getStage(), levelListener));
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    protected void createBtns() {
        super.createBtns();
        float btnSize = Assets.WIDTH / 5;
        leftBtn = new ImageButton(new TextureRegionDrawable(Assets.levelPreBtn));
        leftBtn.setBounds(0, (Assets.HEIGHT - btnSize) / 2, btnSize, btnSize);
        rightBtn = new ImageButton(new TextureRegionDrawable(Assets.levelNextBtn));
        rightBtn.setBounds(Assets.WIDTH - btnSize, (Assets.HEIGHT - btnSize) / 2, btnSize, btnSize);
        levelSize = Assets.LEVEL_IMAGE_SIZE;
        btnVisiableHandle();
    }

    private void setListens() {
        leftBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                if (level != 0) {
                    level--;
                    levelListener.setPosition( - Assets.WIDTH * level);
                }
                return true;
            }
        });
        rightBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                if (level != Assets.LEVEL_MAX) {
                    level++;
                    levelListener.setPosition( - Assets.WIDTH * level);
                }
                return true;
            }
        });
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
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            getPuzzle().setScreen(getPuzzle().getMainScreen());
        } else {
            super.render(delta);
            getBatch().begin();
            float position = levelListener.getPosition();
            for (int i = 0; i < Assets.levels.size(); i++) {
                getBatch().draw(Assets.levels.get(i), position + i * levelSize, (Assets.HEIGHT - levelSize) / 2, levelSize, levelSize);
            }
            getFont().draw(getBatch(), "总计: " + getStarNum(), x_num, y_num);
            getBatch().end();
            //用户放手时才进行回弹
            if (!Gdx.input.isTouched()) {
                //巧妙的用一个取整数的函数来表示一张图片翻过一半的时候就应该跳转到下一张图片的效果
                targetposition = (int) ((position - 0.5f * levelSize) / levelSize) * levelSize;
                float v = 20 * levelSize / Assets.WIDTH;
                if (targetposition < position) {
                    //注意不能越界，min/Assets.WIDTH是一个因子，用于分辨率适配，让回弹速度和分辨率搭配得协调一些
                    if (position - v < targetposition) {
                        position = targetposition;
                    } else {
                        position -= v;
                    }
                }
                if (targetposition > position) {
                    //注意不能越界
                    if (position + v > targetposition)
                        position = targetposition;
                    else
                        position += v;
                }
                levelListener.setPosition(position);
                level = (int) Math.abs(position / Assets.WIDTH);
            }
            if (!backHasTouched && Gdx.input.isKeyPressed(Input.Keys.BACK)) {
                backHasTouched = true;
                getPuzzle().setScreen(getPuzzle().getMainScreen());
            }
        }
    }

    @Override
    public void pause() {
        Settings.save();
    }

    private void btnVisiableHandle() {
        ScheduledExecutorService execut = Executors.newSingleThreadScheduledExecutor();
        execut.scheduleAtFixedRate(new Runnable() {
            public void run() {
                computer();
            }

            private void computer() {
                if (level == 0) {
                    leftBtn.remove();
                    if (rightBtn.getStage() == null) {
                        addActor(rightBtn);
                    }
                } else if (level == Assets.LEVEL_MAX) {
                    rightBtn.remove();
                } else {
                    if (rightBtn.getStage() == null) {
                        addActor(rightBtn);
                    }
                    if (leftBtn.getStage() == null) {
                        addActor(leftBtn);
                    }
                }
            }
        }, 0, 200, TimeUnit.MILLISECONDS);
    }
}
