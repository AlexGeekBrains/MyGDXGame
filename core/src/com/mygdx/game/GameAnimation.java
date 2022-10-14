package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameAnimation {
    private Texture img;
    private Animation<TextureRegion> animation;
    private float time;

    public GameAnimation(String name, int col, int row, float fps) {
        img = new Texture(name);
        TextureRegion region = new TextureRegion(img);
        TextureRegion[][] regions = region.split(img.getWidth() / col, img.getHeight() / row);
        animation = new Animation<>(1 / fps, convertTwoDimToOneDimArr(regions));
        animation.setPlayMode(Animation.PlayMode.LOOP);
    }

    private TextureRegion[] convertTwoDimToOneDimArr(TextureRegion[][] regions) {
        int cnt = 0;
        TextureRegion[] tmp = new TextureRegion[regions.length * regions[0].length];
        for (int i = 0; i < regions.length; i++) {
            for (int j = 0; j < regions[0].length; j++) {
                tmp[cnt++] = regions[i][j];
            }
        }
        return tmp;
    }

    public TextureRegion draw() {
        return animation.getKeyFrame(time);
    }

    public void setTime(float dt) {
        time += dt;
    }

    public void dispose() {
        this.img.dispose();
    }
}