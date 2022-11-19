package com.mygdx.game.monsters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Destroyed;
import com.mygdx.game.Physics;
import com.mygdx.game.Player;
import com.mygdx.game.physbody.PhysBodyMonster;


public abstract class Monster implements Destroyed {
    protected Body bodyMonster;
    protected Player player;
    protected PhysBodyMonster physBody;
    protected float health;

    public abstract void render(SpriteBatch batch, Physics physics, float dt);
}
