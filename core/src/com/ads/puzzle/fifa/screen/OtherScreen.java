package com.ads.puzzle.fifa.screen;

import com.ads.puzzle.fifa.Assets;
import com.ads.puzzle.fifa.Puzzle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Administrator on 2014/7/27.
 */
public class OtherScreen extends BaseScreen {

    protected ImageButton returnBtn;
    private ImageButton buyBtn;
    private Image star;

    public OtherScreen(Puzzle game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        createBtns();
    }

    protected void createBtns() {
        buyBtn = new ImageButton(new TextureRegionDrawable(Assets.buy));
        buyBtn.setBounds(0, y_bar, Assets.TOPBAR_HEIGHT, Assets.TOPBAR_HEIGHT);
        star = new Image(new TextureRegionDrawable(Assets.star));
        star.setBounds(Assets.WIDTH - Assets.TOPBAR_HEIGHT, y_bar, Assets.TOPBAR_HEIGHT, Assets.TOPBAR_HEIGHT);
        returnBtn = new ImageButton(new TextureRegionDrawable(Assets.returnBtn));
        float returnBtnSize = Assets.WIDTH / 6;
        returnBtn.setBounds(0, 0, returnBtnSize, returnBtnSize);

        addActor(buyBtn);
        addActor(star);
        addActor(returnBtn);
    }
}
