package com.mygdx.game.physbody;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhysBodyBox extends PhysBody {

    private boolean isTnt;
    private boolean isBang;

    public PhysBodyBox(String name, Vector2 size, Body body, boolean isTnt) {
        super(name, size, body);
        super.isActive = true;
        this.isTnt = isTnt;
    }

    public PhysBodyBox(String name, Vector2 size, Body body) {
        super(name, size, body);
        super.isActive = true;
    }
}
