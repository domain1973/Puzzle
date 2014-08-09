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

    public GateScreen(Puzzle game, int level) {
        super(game);
        this.level = level;
    }

    @Override
    public void show() {
        super.show();
        createBtns();

        float gateBtnSize = Assets.WIDTH / 5;
        for (int i = 0; i < GATE_MAX; i++) {
            int gateNum = level * GATE_MAX + i;
            TextureRegion gateTRegion = null;
            if (game.getPassGateNum() >= gateNum || gateNum == 0) {
                switch (Answer.gateStars.get(gateNum)) {
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
                    if (gate.getGateNum() <= game.getPassGateNum()) {
                        game.setScreen(new GameScreen(game, level, gate.getGateNum()));
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
                game.setScreen(new LevelScreen(game));
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        font.draw(batch, "0/36", Gdx.graphics.getWidth() - 2 * Assets.TOPBAR_HEIGHT, Gdx.graphics.getHeight() - 10);
        batch.end();
    }
}
