package com.ads.puzzle.fifa.actors;

import com.ads.puzzle.fifa.Answer;
import com.ads.puzzle.fifa.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.List;

/**
 * Created by Administrator on 2014/7/5.
 */
public class Challenge extends Group {
    private boolean isDraw;

    public Challenge(int level, int gateNum) {
        float space = Assets.WIDTH / 6;
        Integer[] bmpIds = Answer.challenges[gateNum];
        List<Sprite> sprites = Assets.levelSpriteMap.get(level);
        float x_off = Assets.WIDTH / 40;
        float y_off = Assets.TOPBAR_HEIGHT;
        for (int i = 0; i < bmpIds.length; i++) {
            Image image = new Image(sprites.get(bmpIds[i]));
            float y = y_off + i / 3 * space;
            float x = x_off + i % 3 * space;
            image.setPosition(x, y);
            addActor(image);
        }
        isDraw = true;
    }

    public void setDraw(boolean isDraw) {
        this.isDraw = isDraw;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isDraw) {
            super.draw(batch, parentAlpha);
        }
    }
}
