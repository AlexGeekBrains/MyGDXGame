package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public interface Destroyed {
    void render(SpriteBatch batch, Physics physics, float dt);

    Body getBody();
}
