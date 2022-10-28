package com.mygdx.game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Player {

    private PlayerControl playerControl;
    private Physics physics;

    public Player(PlayerControl playerControl, Physics physics) {
        this.playerControl = playerControl;
        this.physics = physics;

    }

    public void render(SpriteBatch batch, float dt) {
        playerControl.getCurrentDraw().setTime(dt);
        playerControl.checkMovement();
        batch.draw(playerControl.getCurrentDraw().draw(), (playerControl.getBody().getPosition().x * physics.getPPM())-25f, (playerControl.getBody().getPosition().y*physics.getPPM())-23f);
    }
}
