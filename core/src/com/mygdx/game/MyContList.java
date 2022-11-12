package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.*;
import lombok.Getter;
import lombok.Setter;

@Getter
public class MyContList implements ContactListener {
    private int countFootOnGround;
    @Setter
    private Player player;
    private static final Sound TAKE_COIN_SOUND = Gdx.audio.newSound(Gdx.files.internal("money.mp3"));
    private static final Sound LOSS_SOUND = Gdx.audio.newSound(Gdx.files.internal("loss.mp3"));

    public boolean isOnGround() {
        return countFootOnGround > 0;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if (a.getUserData() != null && b.getUserData() != null) {
            PhysBody physBodyA = (PhysBody) a.getBody().getUserData();
            PhysBody physBodyB = (PhysBody) b.getBody().getUserData();
            String tmpA = (String) a.getUserData();
            String tmpB = (String) b.getUserData();
            if (tmpA.equals("rightFoot") && tmpB.equals("texture") || tmpB.equals("rightFoot") && tmpA.equals("texture")) {
                countFootOnGround++;
            }
            if (tmpA.equals("leftFoot") && tmpB.equals("texture") || tmpB.equals("leftFoot") && tmpA.equals("texture")) {
                countFootOnGround++;
            }
            if (tmpA.equals("rightFoot") && tmpB.equals("box") || tmpB.equals("rightFoot") && tmpA.equals("box")) {
                countFootOnGround++;
            }
            if (tmpA.equals("leftFoot") && tmpB.equals("box") || tmpB.equals("leftFoot") && tmpA.equals("box")) {
                countFootOnGround++;
            }
            if (tmpA.equals("bullet") && tmpB.equals("texture") || tmpB.equals("box") && tmpA.equals("bullet")) {
                physBodyA.setActive(false);
            }
            if (tmpB.equals("bullet") && tmpA.equals("texture") || tmpA.equals("box") && tmpB.equals("bullet")) {
                physBodyB.setActive(false);
            }
            if (tmpA.equals("Hero") && tmpB.equals("coin")) {
                player.incrementCoins();
                TAKE_COIN_SOUND.play();
                physBodyB.setActive(false);
            }
            if (tmpB.equals("Hero") && tmpA.equals("coin")) {
                player.incrementCoins();
                TAKE_COIN_SOUND.play();
                physBodyA.setActive(false);
            }
            if (tmpA.equals("Hero") && tmpB.equals("water")|| tmpB.equals("Hero") && tmpA.equals("water")) {
                LOSS_SOUND.play();
                player.setDeath(true);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if (a.getUserData() != null && b.getUserData() != null) {
            String tmpA = (String) a.getUserData();
            String tmpB = (String) b.getUserData();
            if (tmpA.equals("rightFoot") && tmpB.equals("texture") || tmpB.equals("rightFoot") && tmpA.equals("texture")) {
                countFootOnGround--;
            }
            if (tmpA.equals("leftFoot") && tmpB.equals("texture") || tmpB.equals("leftFoot") && tmpA.equals("texture")) {
                countFootOnGround--;
            }
            if (tmpA.equals("rightFoot") && tmpB.equals("box") || tmpB.equals("rightFoot") && tmpA.equals("box")) {
                countFootOnGround--;
            }
            if (tmpA.equals("leftFoot") && tmpB.equals("box") || tmpB.equals("leftFoot") && tmpA.equals("box")) {
                countFootOnGround--;
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
