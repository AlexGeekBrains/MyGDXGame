package com.mygdx.game.physbody;

import com.badlogic.gdx.physics.box2d.Body;
import lombok.Getter;

public class PhysBodyBang extends PhysBody {

    @Getter
    private int radius;

    public PhysBodyBang(String name, Body body, int radius) {
        super(name, body);
        super.isActive = true;
        this.radius = radius;
    }
}