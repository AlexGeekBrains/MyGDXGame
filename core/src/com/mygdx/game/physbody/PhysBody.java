package com.mygdx.game.physbody;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PhysBody {

    @Setter
    protected boolean isActive;
    private Vector2 size;
    protected String name;
    protected Body body;

    public PhysBody(String name, Vector2 size, Body body) {
        this.name = name;
        this.size = size;
        this.body = body;
        isActive = true;
    }

    public PhysBody(String name, Body body) {
        this.name = name;
        this.body = body;
        isActive = true;
    }
}