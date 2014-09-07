package com.ads.puzzle.fifa.screen;

import com.ads.puzzle.fifa.Answer;
import com.ads.puzzle.fifa.Assets;
import com.ads.puzzle.fifa.Puzzle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Administrator on 2014/7/27.
 */
public class OtherScreen extends BaseScreen {

    protected ImageButton returnBtn;
    private ImageButton shareBtn;
    private Image star;
    protected float x_num;
    protected float y_num;

    public OtherScreen(Puzzle game) {
        super(game);
        x_num = Assets.WIDTH - 3 * Assets.TOPBAR_HEIGHT;
        y_num = Assets.HEIGHT - 5;
    }

    @Override
    public void show() {
        super.show();
        createBtns();
    }

    protected void createBtns() {
        returnBtn = new ImageButton(new TextureRegionDrawable(Assets.returnTr));
        returnBtn.setBounds(0, getY_bar(),Assets.TOPBAR_HEIGHT, Assets.TOPBAR_HEIGHT);
        shareBtn = new ImageButton(new TextureRegionDrawable(Assets.share));
        shareBtn.setBounds(Assets.TOPBAR_HEIGHT, getY_bar(), Assets.TOPBAR_HEIGHT, Assets.TOPBAR_HEIGHT);
        shareBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
            }
        });
        star = new Image(new TextureRegionDrawable(Assets.star));
        star.setBounds(Assets.WIDTH - Assets.TOPBAR_HEIGHT, getY_bar(), Assets.TOPBAR_HEIGHT, Assets.TOPBAR_HEIGHT);

        addActor(shareBtn);
        addActor(star);
        addActor(returnBtn);
    }

    protected int getStarNum() {
        int starNum = 0;
        for (int num : Answer.gateStars) {
            starNum = starNum + num;
        }
        return starNum;
    }
}
