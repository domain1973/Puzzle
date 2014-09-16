package com.ads.puzzle.fifa;

import com.ads.puzzle.fifa.screen.GameScreen;

/**
 * Created by Administrator on 2014/9/10.
 */
public abstract  class PEvent {

    public abstract void pay();

    public abstract void exit();

    public abstract void sos(GameScreen gs);

    public abstract void invalidateSos();

    public abstract void resetGame();

}
