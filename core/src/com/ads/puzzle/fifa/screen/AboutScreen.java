package com.ads.puzzle.fifa.screen;

import com.ads.puzzle.fifa.Assets;
import com.ads.puzzle.fifa.Puzzle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Administrator on 2014/9/1.
 */
public class AboutScreen extends OtherScreen {
    private Image aboutImage;
    private GameScreen gameScreen;

    private AboutScreen(Puzzle game) {
        super(game);
        aboutImage = new Image(Assets.aboutInfo);
        aboutImage.setPosition(0, (Assets.HEIGHT - aboutImage.getHeight())/2);
    }

    public AboutScreen(Puzzle game, GameScreen gs) {
        this(game);
        gameScreen = gs;
    }

    @Override
    public void show() {
        super.show();
        addActor(aboutImage);
        returnBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                getPuzzle().setScreen(gameScreen);
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
