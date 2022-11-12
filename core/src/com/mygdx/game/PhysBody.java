package com.mygdx.game;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PhysBody {

    @Setter
    @Getter
    private boolean isActive;
    private Vector2 size;
    private String name;
    private Body body;

    public PhysBody(String name, Vector2 size, Body body) {
        this.name = name;
        this.size = size;
        this.body = body;
        isActive = true;
    }
}
