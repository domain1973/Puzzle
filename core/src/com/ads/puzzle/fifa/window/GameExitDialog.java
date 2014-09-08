package com.ads.puzzle.fifa.window;

import com.ads.puzzle.fifa.Assets;
import com.ads.puzzle.fifa.controller.IController;
import com.ads.puzzle.fifa.controller.PieceController;
import com.ads.puzzle.fifa.screen.BaseScreen;
import com.ads.puzzle.fifa.screen.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Administrator on 2014/7/20.
 * 退出确认对话框
 */
public class GameExitDialog extends  DefaultDialog {

    public GameExitDialog(BaseScreen gs, String mess) {
        super(gs, mess);
    }

    @Override
    protected void addListeners() {
        btnCancel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                layerBg.remove();
                GameExitDialog.this.remove();
                super.touchUp(event, x, y, pointer, button);
            }
        });
        btnOk.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                super.touchUp(event, x, y, pointer, button);
            }
        });
    }
}
