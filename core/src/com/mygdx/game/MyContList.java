package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import lombok.Getter;
import java.util.ArrayList;

public class MyContList implements ContactListener {
    @Getter
    private int count;
    @Getter
    private final ArrayList<Body>destroyObj = new ArrayList<>();

    public boolean isOnGround() {
        return count > 0;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if (a.getUserData() != null && b.getUserData() != null) {
            String tmpA = (String) a.getUserData();
            String tmpB = (String) b.getUserData();
            if (tmpA.equals("rightFoot") && tmpB.equals("texture") || tmpB.equals("rightFoot") && tmpA.equals("texture")) {
                count++;
            }
            if (tmpA.equals("leftFoot") && tmpB.equals("texture") || tmpB.equals("leftFoot") && tmpA.equals("texture")) {
                count++;
            }
            if (tmpA.equals("rightFoot") && tmpB.equals("box") || tmpB.equals("rightFoot") && tmpA.equals("box")) {
                count++;
            }
            if (tmpA.equals("leftFoot") && tmpB.equals("box") || tmpB.equals("leftFoot") && tmpA.equals("box")) {
                count++;
            }
            if (tmpA.equals("bullet") && tmpB.equals("texture")|| tmpB.equals("box") && tmpA.equals("bullet")){
                destroyObj.add(a.getBody());
            }
            if(tmpB.equals("bullet") && tmpA.equals("texture")|| tmpA.equals("box") && tmpB.equals("bullet")){
                destroyObj.add(b.getBody());
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
                count--;
            }
            if (tmpA.equals("leftFoot") && tmpB.equals("texture") || tmpB.equals("leftFoot") && tmpA.equals("texture")) {
                count--;
            }
            if (tmpA.equals("rightFoot") && tmpB.equals("box") || tmpB.equals("rightFoot") && tmpA.equals("box")) {
                count--;
            }
            if (tmpA.equals("leftFoot") && tmpB.equals("box") || tmpB.equals("leftFoot") && tmpA.equals("box")) {
                count--;
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
