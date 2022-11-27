package com.mygdx.game.physbody;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class PhysBody {


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

     public boolean isActive() {
        return isActive;
    }

    public Vector2 getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public Body getBody() {
        return body;
    }

    public void setActive(boolean active) {
        isActive = active;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setBody(Body body) {
        this.body = body;
    }
}