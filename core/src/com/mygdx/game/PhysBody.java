package com.mygdx.game;


import com.badlogic.gdx.math.Vector2;
import lombok.Getter;



@Getter
public class PhysBody {
    private Vector2 size;
    private String name;
    public PhysBody(String name, Vector2 size) {
        this.name = name;
        this.size = size;
    }
}
