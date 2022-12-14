package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class MyAtlasAnim {

    private static TextureAtlas atlas;

    private Animation<TextureAtlas.AtlasRegion> animation;

    private float time;

    public MyAtlasAnim(String atlas, String name, float fps, Animation.PlayMode playMode) {
        time = 0;
        MyAtlasAnim.atlas = new TextureAtlas(atlas);
        animation = new Animation<>(1 / fps, this.atlas.findRegions(name));
        animation.setPlayMode(playMode);
    }

    public TextureRegion draw() {
        return animation.getKeyFrame(time);
    }

    public float getAnimationDuration(){
        return animation.getAnimationDuration();
    }

    public void setTime(float dt) {
        time += dt;
    }

    public void resetTime() {
        time = 0;
    }

    public boolean isAnimationOver() {
        return animation.isAnimationFinished(time);
    }

    public void dispose() {
        this.atlas.dispose();
    }

    public static TextureAtlas getAtlas() {
        return atlas;
    }

    public Animation<TextureAtlas.AtlasRegion> getAnimation() {
        return animation;
    }


    public void setAnimation(Animation<TextureAtlas.AtlasRegion> animation) {
        this.animation = animation;
    }
}