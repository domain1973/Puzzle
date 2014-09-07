package com.ads.puzzle.fifa.screen;

import com.ads.puzzle.fifa.Answer;
import com.ads.puzzle.fifa.Assets;
import com.ads.puzzle.fifa.Puzzle;
import com.ads.puzzle.fifa.actors.Gate;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Administrator on 2014/6/22.
 */
public class GateScreen extends OtherScreen {
    private static final int GATE_MAX = 12;
    private int level;
    private int starNum;
    private float x_num;
    private float y_num;

    public GateScreen(Puzzle puzzle, int level) {
        super(puzzle);
        this.level = level;
        x_num = Assets.WIDTH - 2 * Assets.TOPBAR_HEIGHT;
        y_num = Assets.HEIGHT - 5;
    }

    @Override
    public void show() {
        super.show();
        createBtns();

        float gateBtnSize = Assets.WIDTH / 5;
        for (int i = 0; i < GATE_MAX; i++) {
            int gateNum = level * GATE_MAX + i;
            TextureRegion gateTRegion = null;
            if (getPuzzle().getPassGateNum() >= gateNum || gateNum == 0) {
                int num = Answer.gateStars.get(gateNum);
                starNum = starNum + num;
                switch (num) {
                    case 0:
                        gateTRegion = Assets.gate_0star;
                        break;
                    case 1:
                        gateTRegion = Assets.gate_1star;
                        break;
                    case 2:
                        gateTRegion = Assets.gate_2star;
                        break;
                    case 3:
                        gateTRegion = Assets.gate_3star;
                        break;
                }
            } else {
                gateTRegion = Assets.gate_lock;
            }
            TextureRegionDrawable imageUp = new TextureRegionDrawable(gateTRegion);
            imageUp.setMinWidth(gateBtnSize);
            imageUp.setMinHeight(gateBtnSize);
            final Gate gate = new Gate(imageUp, i, gateNum);
            gate.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y,
                                         int pointer, int button) {
                    if (gate.getGateNum() <= getPuzzle().getPassGateNum()) {
                        getPuzzle().setScreen(new GameScreen(getPuzzle(), level, gate.getGateNum()));
                    }
                    return true;
                }
            });
            addActor(gate);
        }
        returnBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                getPuzzle().setScreen(new LevelScreen(getPuzzle()));
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        getBatch().begin();
        getFont().draw(getBatch(), starNum + "/36", x_num, y_num);
        getBatch().end();
    }
}
