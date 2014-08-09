package com.ads.puzzle.fifa.window;

import com.ads.puzzle.fifa.Assets;
import com.ads.puzzle.fifa.controller.IController;
import com.ads.puzzle.fifa.controller.PieceController;
import com.ads.puzzle.fifa.screen.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Administrator on 2014/7/20.
 */
public class HelpStarWindow extends BaseWindow {
    private GameScreen gameScreen;
    private float w;
    private float x;
    private float y;
    private float space;
    private ImageButton yesBtn;
    private ImageButton noBtn;

    public HelpStarWindow(GameScreen gameScreen) {
        super("你是天才!", new WindowStyle(gameScreen.getFont(), Color.WHITE, new TextureRegionDrawable(
                Assets.winBg)));
        this.gameScreen = gameScreen;
        create();
    }

    public void create() {
        gameScreen.getStage().addActor(layerBg);
        w = Assets.WIDTH * 4 / 5;
        x = (Assets.WIDTH - w) / 2;
        y = (Assets.HEIGHT - w) / 2;
        setBounds(x, y, w, w);
        addButtons();
        addListeners();
        addActor(yesBtn);
        addActor(noBtn);
        Gdx.input.setInputProcessor(gameScreen.getStage());
    }

    private void addButtons() {
        space = w / 12;
        float star_w = space * 3f;
        float v = (w - star_w) / 2;
        yesBtn = new ImageButton(new TextureRegionDrawable(Assets.suspendBtn), new TextureRegionDrawable(Assets.suspendBtn));
        yesBtn.setBounds(v - star_w, 0, star_w, star_w);
        noBtn = new ImageButton(new TextureRegionDrawable(Assets.nextBtn), new TextureRegionDrawable(Assets.nextBtn));
        noBtn.setBounds(v + star_w, 0, star_w, star_w);
    }

    private void addListeners() {
        yesBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                layerBg.remove();
                HelpStarWindow.this.remove();
                gameScreen.useHelp();
                Group group = gameScreen.getStage().getRoot();
                ((PieceController) group.findActor(IController.PIECE_CTRL)).handler();
                super.touchUp(event, x, y, pointer, button);
            }
        });
        noBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                layerBg.remove();
                HelpStarWindow.this.remove();
                super.touchUp(event, x, y, pointer, button);
            }
        });
    }
}
