package com.mygdx.game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Player {

    private PlayerControl playerControl;

    public Player(PlayerControl playerControl) {
        this.playerControl = playerControl;
    }

    public void render(SpriteBatch batch, float dt) {
        playerControl.getCurrentDraw().setTime(dt);
        playerControl.checkMovement();
        batch.draw(playerControl.getCurrentDraw().draw(), playerControl.getBody().getPosition().x, playerControl.getBody().getPosition().y);
    }
}
