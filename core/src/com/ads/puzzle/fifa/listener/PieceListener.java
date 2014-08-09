package com.ads.puzzle.fifa.listener;

import com.ads.puzzle.fifa.Assets;
import com.ads.puzzle.fifa.actors.Area;
import com.ads.puzzle.fifa.actors.Piece;
import com.ads.puzzle.fifa.controller.AreaController;
import com.ads.puzzle.fifa.controller.IController;
import com.ads.puzzle.fifa.controller.PieceController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.SnapshotArray;

import static com.ads.puzzle.fifa.Assets.SMALL_PIECE_SIZE;
import static com.ads.puzzle.fifa.Assets.PIECE_SIZE;
/**
 * Created by Administrator on 2014/7/4.
 */
public class PieceListener extends GestureDetector.GestureAdapter {
    private Stage stage;
    private Vector3 touchPoint;
    private AreaController areaController;
    private PieceController pieceCtrl;
    private final Area[] areaActors;
    private final SnapshotArray<Actor> pieceActors;
    private Piece downActor;
    private float raw_x;
    private float raw_y;
    private float w;
    private float h;

    public PieceListener(Stage stage) {
        this.stage = stage;
        touchPoint = new Vector3();
        areaController = (AreaController) stage.getRoot().findActor(IController.AREA_CTRL);
        areaActors = areaController.getAreas();
        pieceCtrl = (PieceController) stage.getRoot().findActor(IController.PIECE_CTRL);
        pieceActors = pieceCtrl.getChildren();
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        stage.getCamera().unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
        for (int i = 0; i < 4; i++) {
            Piece piece = (Piece) pieceActors.get(i);
            Rectangle bounds = new Rectangle(piece.getX(), piece.getY(), piece.getWidth(), piece.getHeight());
            if (bounds.contains(touchPoint.x, touchPoint.y)) {
                downActor = piece;
                raw_x = downActor.getX();
                raw_y = downActor.getY();
                w =  downActor.getWidth();
                h = downActor.getHeight();
                break;
            }
        }
        return super.touchDown(x, y, pointer, button);
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        stage.getCamera().unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0)); // 坐标转化
        for (Actor actor : pieceActors) {
            Piece piece = (Piece) actor;
            Rectangle bounds = new Rectangle(piece.getX(), piece.getY(), piece.getWidth(), piece.getHeight());
            if (bounds.contains(touchPoint.x, touchPoint.y) && piece.getArea() > -1) {
                piece.changeOrientation();//快速点击改变块方位
                break;
            }
        }
        return super.tap(x, y, count, button);
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        if (downActor != null) {
            downActor.setPosition(downActor.getX() + deltaX, downActor.getY() - deltaY);
            if (pieceCtrl.getFixAreaBounds().contains(downActor.getX(), downActor.getY())) {
                downActor.setWidth(SMALL_PIECE_SIZE);
                downActor.setHeight(SMALL_PIECE_SIZE);
            } else {
                downActor.setWidth(PIECE_SIZE);
                downActor.setHeight(PIECE_SIZE);
            }
            if (areaController.getBounds().contains(downActor.getX(), downActor.getY())) {
                downActor.setWidth(PIECE_SIZE);
                downActor.setHeight(PIECE_SIZE);
            } else {
                downActor.setWidth(SMALL_PIECE_SIZE);
                downActor.setHeight(SMALL_PIECE_SIZE);
            }
        }
        return super.pan(x, y, deltaX, deltaY);
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        if (downActor != null) {
            stage.getCamera().unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0)); // 坐标转化
            for (Area distArea : areaActors) {
                Rectangle bounds = new Rectangle(distArea.getX(), distArea.getY(), distArea.getWidth(), distArea.getHeight());
                if (bounds.contains(touchPoint.x, touchPoint.y)) {
                    int distPieceId = distArea.getPieceId();
                    int resAreaId = downActor.getArea();
                    if (resAreaId > -1) {
                        Area resArea = areaActors[resAreaId];
                        resArea.setPieceId(distPieceId);
                        if (distPieceId > -1) {
                            Piece piece = (Piece) pieceActors.get(distPieceId);
                            piece.setBounds(resArea.getX(), resArea.getY(), resArea.getWidth(), resArea.getHeight());
                            piece.setArea(resArea.getId());
                        }
                    } else {
                        if (distPieceId > -1) {
                            Piece piece = (Piece) pieceActors.get(distPieceId);
                            piece.return2BeginArea();
                        }
                        downActor.setOrientation(0);
                    }

                    downActor.setArea(distArea.getId());
                    downActor.setBounds(distArea.getX(), distArea.getY(), distArea.getWidth(), distArea.getHeight());
                    distArea.setPieceId(downActor.getId());
                    return super.fling(velocityX, velocityY, button);
                }
            }
            if (pieceCtrl.getFixAreaBounds().contains(touchPoint.x, touchPoint.y) &&
                    downActor.getArea() > -1) {
                //1 从大块移动到小块
                areaActors[downActor.getArea()].setPieceId(-1);
                downActor.return2BeginArea();
                return super.fling(velocityX, velocityY, button);
            }
            downActor.setBounds(raw_x, raw_y, w, h);
        }
        return super.fling(velocityX, velocityY, button);
    }
}
