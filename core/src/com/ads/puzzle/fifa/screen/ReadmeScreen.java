package com.ads.puzzle.fifa.screen;

import com.ads.puzzle.fifa.Assets;
import com.ads.puzzle.fifa.Puzzle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Administrator on 2014/9/1.
 */
public class ReadmeScreen extends OtherScreen {
    private Image readmeImage;
    private ReadmeScreen readmeScreen;
    private MainScreen mainScreen;
    private GameScreen gameScreen;

    private ReadmeScreen(Puzzle game) {
        super(game);
        readmeImage = new Image(Assets.readme);
        readmeImage.setPosition(0, (Assets.HEIGHT - readmeImage.getHeight())/2);
    }

    public ReadmeScreen(Puzzle game, ReadmeScreen rs) {
        this(game);
        readmeScreen = rs;
    }

    public ReadmeScreen(Puzzle game, MainScreen ms) {
        this(game);
        mainScreen = ms;
    }

    public ReadmeScreen(Puzzle game, GameScreen gs) {
        this(game);
        gameScreen = gs;
    }

    @Override
    public void show() {
        super.show();
        addActor(readmeImage);
        returnBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (gameScreen != null) {
                    getPuzzle().setScreen(gameScreen);
                } else if (mainScreen != null) {
                    getPuzzle().setScreen(mainScreen);
                } else if (gameScreen != null) {
                    getPuzzle().setScreen(gameScreen);
                }
                super.touchUp(event, x, y, pointer, button);
            }
        });
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        getBatch().begin();
        getFont().draw(getBatch(), "总计: " + getStarNum(), x_num, y_num);
        getBatch().end();
    }
}
