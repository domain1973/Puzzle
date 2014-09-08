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
 * 缺省对话框
 */
public class DefaultDialog extends BaseWindow {
    protected ImageButton btnCancel;
    protected ImageButton btnOk;
    private BaseScreen gameScreen;
    private float y_str;
    private float btnH;
    private String message;

    public DefaultDialog(BaseScreen gs, String mess) {
        super("", new WindowStyle(gs.getFont(), Color.WHITE, new TextureRegionDrawable(
                Assets.winBg)));
        gameScreen = gs;
        message = mess;
        create();
    }

    public void create() {
        gameScreen.getStage().addActor(layerBg);
        float dlgH = Assets.HEIGHT/4;
        btnH = dlgH/4;
        float y = (Assets.HEIGHT - dlgH) / 2;
        y_str = dlgH /2;
        setBounds(0, y, Assets.WIDTH, dlgH);
        Image image = new Image(Assets.dlgBg);
        image.setBounds(0, 0, Assets.WIDTH, dlgH);
        addActor(image);
        addButtons();
        addLabels();
        addListeners();
        addActor(btnCancel);
        addActor(btnOk);
        Gdx.input.setInputProcessor(gameScreen.getStage());
    }

    private void addLabels() {
        float fontSize = 36;//default
        Label.LabelStyle labelStyle = new Label.LabelStyle(gameScreen.getDlgFont(), Color.BLACK); // 创建一个Label样式，使用默认黑色字体
        Label labProm = new Label("温馨提示", labelStyle);
        labProm.setPosition(Assets.WIDTH / 2 - 2*fontSize, Assets.HEIGHT/4 - fontSize);
        addActor(labProm);
        Label labCancel = new Label("取消", labelStyle);
        labCancel.setPosition(Assets.WIDTH / 8, 0);
        addActor(labCancel);
        Label labOk = new Label("确定", labelStyle);
        labOk.setPosition(Assets.WIDTH * 5 / 8, 0);
        addActor(labOk);
        Label labMess = new Label(message, labelStyle);
        labMess.setPosition(10, y_str);
        addActor(labMess);
    }

    private void addButtons() {
        TextureRegion ok = createBtnBmp(true);
        TextureRegion okdown = createBtnBmp(false);
        btnCancel = new ImageButton(new TextureRegionDrawable(ok), new TextureRegionDrawable(okdown));
        btnCancel.setBounds(0, 0, Assets.WIDTH / 2, btnH);
        btnOk = new ImageButton(new TextureRegionDrawable(ok), new TextureRegionDrawable(okdown));
        btnOk.setBounds(Assets.WIDTH / 2, 0, Assets.WIDTH / 2, btnH);
    }

    private TextureRegion createBtnBmp(boolean ok) {
        Pixmap pixmap = new Pixmap((int)Assets.WIDTH/2, (int)btnH, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.DARK_GRAY); // 设置颜色为灰色
        pixmap.drawRectangle(0, 0, (int)Assets.WIDTH/2, (int)btnH); // 绘制边框
        if (ok) {
            pixmap.setColor(213,209,236,0); // 设置颜色为灰色
        } else {
            pixmap.setColor(Color.ORANGE); // 设置颜色为灰色
        }
        pixmap.fillRectangle(0, 1, (int) Assets.WIDTH / 2, (int) btnH);
        TextureRegion pix = new TextureRegion(new Texture(pixmap));
        return pix;
    }

    protected void addListeners() {
    }
}
