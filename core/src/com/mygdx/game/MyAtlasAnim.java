package com.mygdx.game;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyAtlasAnim {
    private TextureAtlas atlas;
    private Animation<TextureAtlas.AtlasRegion> animation;
    private float time;
    public MyAtlasAnim(String atlas, String name, float fps,Animation.PlayMode playMode) {
        time = 0;
        this.atlas = new TextureAtlas(atlas);
        animation = new Animation<>(1 / fps, this.atlas.findRegions(name));
        animation.setPlayMode(playMode);
    }

    public TextureRegion draw() {
        return animation.getKeyFrame(time);
    }
    public void setTime(float dt) {
        time += dt;
    }

    public void resetTime(){
        time=0;
    }

    public void dispose() {
        this.atlas.dispose();
    }
}