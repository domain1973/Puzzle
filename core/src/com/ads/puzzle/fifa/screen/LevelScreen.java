package com.ads.puzzle.fifa.screen;

import com.ads.puzzle.fifa.Assets;
import com.ads.puzzle.fifa.Puzzle;
import com.ads.puzzle.fifa.Settings;
import com.ads.puzzle.fifa.listener.LevelDetector;
import com.ads.puzzle.fifa.listener.LevelListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

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

    public LevelScreen(Puzzle game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        createBtns();
        setListens();
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        levelListener = new LevelListener(stage, game);
        multiplexer.addProcessor(new LevelDetector(stage, levelListener));
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
        addActor(leftBtn);
        addActor(rightBtn);
    }

    private void setListens() {
        leftBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                if (level != 0) {
                    level--;
                    levelListener.setPosition( - 480*level);
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
                    levelListener.setPosition( - 480*level);
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
                game.setScreen(game.getMainScreen());
                super.touchUp(event, x, y, pointer, button);
            }
        });
    }

    float targetposition;

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        float position = levelListener.getPosition();
        for (int i = 0; i < Assets.levels.size(); i++) {
            batch.draw(Assets.levels.get(i), position + i * levelSize,(Assets.HEIGHT - levelSize) / 2, levelSize, levelSize);
        }
        //(Assets.WIDTH - levelBtnSize) / 2, (Assets.HEIGHT - levelBtnSize) / 2
        font.draw(batch, "总计: 0", Assets.WIDTH - 3 * Assets.TOPBAR_HEIGHT, Assets.HEIGHT - 10);
        batch.end();
        //用户放手时才进行回弹
        if (!Gdx.input.isTouched()) {
            //巧妙的用一个取整数的函数来表示一张图片翻过一半的时候就应该跳转到下一张图片的效果
            targetposition = (int) ((position - 0.5f * levelSize) / levelSize) * levelSize;
            float v = 20 * levelSize / 480f;
            if (targetposition < position) {
                //注意不能越界，min/480f是一个因子，用于分辨率适配，让回弹速度和分辨率搭配得协调一些

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
        }
        if (!backHasTouched && Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            backHasTouched = true;
            game.setScreen(game.getMainScreen());
        }
    }

    @Override
    public void pause() {
        Settings.save();
    }

}
