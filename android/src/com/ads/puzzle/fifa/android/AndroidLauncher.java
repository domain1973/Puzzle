package com.ads.puzzle.fifa.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.ads.puzzle.fifa.Puzzle;

import net.youmi.android.AdManager;
import net.youmi.android.AdManager;
import net.youmi.android.AdService;
import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.banner.AdViewListener;

import android.os.Bundle;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class AndroidLauncher extends AndroidApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        // 创建libgdx视图
        View gameView = initializeForView(new Puzzle(), config);
        // 创建布局
        RelativeLayout layout = new RelativeLayout(this);
        // 添加libgdx视图
        layout.addView(gameView);
        setContentView(layout);
        //banner广告
        AdManager.getInstance(this).init("002926c4da44b2d9", "e684a29c58a762bd", true);
        // 实例化LayoutParams(重要)
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        // 设置广告条的悬浮位置
        layoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT; // 这里示例为右下角
        // 调用Activity的addContentView函数
        addContentView(new AdView(this, AdSize.FIT_SCREEN), layoutParams);
    }

    @Override
    public void onBackPressed() {
    }
}
