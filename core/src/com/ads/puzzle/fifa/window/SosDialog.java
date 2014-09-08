package com.ads.puzzle.fifa.window;

import com.ads.puzzle.fifa.controller.IController;
import com.ads.puzzle.fifa.controller.PieceController;
import com.ads.puzzle.fifa.screen.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by Administrator on 2014/7/20.
 * 退出确认对话框
 */
public class SosDialog extends  DefaultDialog {
    private GameScreen gameScreen;

    public SosDialog(GameScreen gs, String mess) {
        super(gs, mess);
        this.gameScreen = gs;
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
                SosDialog.this.remove();
                gameScreen.setOpenLightWin(false);
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
                layerBg.remove();
                SosDialog.this.remove();
                gameScreen.setOpenLightWin(false);
                gameScreen.useHelp();
                Group group = gameScreen.getStage().getRoot();
                ((PieceController) group.findActor(IController.PIECE_CTRL)).handler();
                super.touchUp(event, x, y, pointer, button);
            }
        });
    }
}
