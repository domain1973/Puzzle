/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.ads.puzzle.fifa;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Assets {
    public static TextureRegion startBg;
    public static TextureRegion theme;
    public static TextureRegion gameBg;
    public static TextureRegion winBg;
    public static TextureRegion layerBg;
    public static TextureRegion resultBg;
    public static TextureRegion areaBg;
    public static TextureRegion buy;
    public static TextureRegion star;
    public static TextureRegion star_null;
    public static TextureRegion gatestar;
    public static TextureRegion gatestar_null;
    public static TextureRegion help;
    public static TextureRegion unlock;
    public static TextureRegion refresh;
    public static TextureRegion menu;
    public static TextureRegion playBtn;
    public static TextureRegion playBtnDown;
    public static TextureRegion settingBtn;
    public static TextureRegion settingBtnDown;
    public static TextureRegion levelPreBtn;
    public static TextureRegion levelNextBtn;
    public static TextureRegion returnBtn;
    public static TextureRegion gate_0star;
    public static TextureRegion gate_1star;
    public static TextureRegion gate_2star;
    public static TextureRegion gate_3star;
    public static TextureRegion gate_lock;
    public static TextureRegion nextBtn;
    public static TextureRegion musicBtn;
    public static TextureRegion suspendBtn;
    public static TextureRegion[] cubes;

    public static List<Sprite> levels;
    public static Map<Integer, List<Sprite>> levelSpriteMap;//关卡精灵图标
    public static float spriteSize;///关卡精灵图标大小
    public static Sprite[] pieces;
    public static float TOPBAR_HEIGHT;//顶部按钮条的高度
    public static float WIDTH;
    public static float HEIGHT;
    public static float PIECE_SIZE;
    public static float SMALL_PIECE_SIZE;
    public static float LEVEL_IMAGE_SIZE;
    public static float LEVEL_IMAGE_OFF_SIZE;
    public static int LEVEL_MAX = 6;
    public static float space = 10;

    private static int levelNum = 2;

    public static void load() {
        TextureAtlas atlas = new TextureAtlas("p.atlas");
        loadBmps(atlas);
        createLevels(atlas);
        createLevelSprite(atlas);
        creteMagicCubes(atlas);

        initConstants();
    }

    private static void initConstants() {
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();
        TOPBAR_HEIGHT = HEIGHT / 12;
        SMALL_PIECE_SIZE = WIDTH / 4;
        PIECE_SIZE = Gdx.graphics.getWidth() / 2 - space * 2;
        LEVEL_IMAGE_SIZE = WIDTH ;
        LEVEL_IMAGE_OFF_SIZE = WIDTH/7;
    }

    private static void loadBmps(TextureAtlas atlas) {
        startBg = atlas.findRegion("gamebg");
        layerBg = atlas.findRegion("layerbg");//图层背景,对话框使用
        winBg = atlas.findRegion("winbg");
        theme = atlas.findRegion("start");
        resultBg = atlas.findRegion("result");
        suspendBtn = atlas.findRegion("suspend");
        musicBtn = atlas.findRegion("music");
        nextBtn = atlas.findRegion("next");
        star = atlas.findRegion("star");
        star_null = atlas.findRegion("star_null");
        gatestar = atlas.findRegion("gatestar");
        gatestar_null = atlas.findRegion("gatestar_null");
        buy = atlas.findRegion("buy");
        help = atlas.findRegion("help");
        unlock = atlas.findRegion("unlock");
        refresh = atlas.findRegion("refresh");
        menu = atlas.findRegion("menu");
        gameBg = atlas.findRegion("gamebg");
        playBtnDown = atlas.findRegion("btn0");
        playBtn = atlas.findRegion("btn01");
        settingBtnDown = atlas.findRegion("btn0");
        settingBtn = atlas.findRegion("btn01");
        levelPreBtn = atlas.findRegion("levelpre");
        levelNextBtn = atlas.findRegion("levelnext");
        returnBtn = atlas.findRegion("returnMenu");
        gate_0star = atlas.findRegion("gate_0star");
        gate_1star = atlas.findRegion("gate_1star");
        gate_2star = atlas.findRegion("gate_2star");
        gate_3star = atlas.findRegion("gate_3star");
        gate_lock = atlas.findRegion("gate_lock");
        areaBg = atlas.findRegion("area");
    }

    private static void creteMagicCubes(TextureAtlas atlas) {
        cubes = new TextureRegion[4];
        pieces = new Sprite[4];
        for (int i = 0; i < 4; i++) {
            String name = "piece" + i;
            pieces[i] = atlas.createSprite(name);
            cubes[i] = atlas.findRegion(name);
        }
    }

    private static void createLevelSprite(TextureAtlas atlas) {
        levelSpriteMap = new HashMap<Integer, List<Sprite>>();
        for (int i = 1; i < levelNum; i++) {
            int m = 0;
            List<Sprite> sprites = new ArrayList<Sprite>();
            while (m < 6) {
                String spriteName = String.valueOf(i * 100 + m);
                Sprite s1 = atlas.createSprite(spriteName);
                if (s1 == null) break;
                sprites.add(s1);
                spriteSize = s1.getHeight();
                m++;
            }
            levelSpriteMap.put(i - 1, sprites);
        }
    }

    private static void createLevels(TextureAtlas atlas) {
        levels = new ArrayList<Sprite>();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            Sprite s = atlas.createSprite("s" + i);
            if (s == null) {
                break;
            }
            levels.add(s);
        }
    }

    public static void playSound(Sound sound) {
        if (Settings.soundEnabled) sound.play(1);
    }
}
