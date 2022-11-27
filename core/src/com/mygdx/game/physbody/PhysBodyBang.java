package com.mygdx.game.physbody;

import com.badlogic.gdx.physics.box2d.Body;


public class PhysBodyBang extends PhysBody {
    private int radius;

    public PhysBodyBang(String name, Body body, int radius) {
        super(name, body);
        super.isActive = true;
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }
}