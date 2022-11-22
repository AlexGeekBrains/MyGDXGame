package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public class Blood {

    private MyAtlasAnim blood;

    public Blood() {
        blood = new MyAtlasAnim("atlas/unnamed.atlas", "1", 30, Animation.PlayMode.NORMAL);
    }

    public boolean render(SpriteBatch batch, float dt, Rectangle rectangle) {
        blood.setTime(dt);
        batch.draw(blood.draw(), rectangle.x, rectangle.y);
        if (blood.isAnimationOver()) {
            blood.resetTime();
            return true;
        }
        return false;
    }
}