package com.mygdx.game.physbody;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PhysBodyMonster extends PhysBody {
    private boolean leftFootContact;
    private boolean rightFootContact;
    private boolean leftAttack;
    private boolean rightAttack;
    private boolean isContactWithHero;
    private boolean isContactWithBullet;
    private boolean isContactWithBang;

    public PhysBodyMonster(String name, Vector2 size, Body body) {
        super(name, size, body);
    }
}
