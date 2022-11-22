package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.physbody.PhysBody;
import com.mygdx.game.physbody.PhysBodyMonster;
import lombok.Getter;
import lombok.Setter;

@Getter
public class MyContList implements ContactListener {
    private int countFootOnGround;

    @Setter
    private Player player;

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
                physBodyB.setActive(false);
            }
            if (tmpB.equals("Hero") && tmpA.equals("coin")) {
                player.incrementCoins();
                physBodyA.setActive(false);
            }
            if (tmpA.equals("Hero") && tmpB.equals("water") || tmpB.equals("Hero") && tmpA.equals("water")) {
                player.setDeath(true);
            }
            if (tmpA.equals("monsterLeftFoot") && tmpB.equals("texture")) {
                ((PhysBodyMonster) physBodyA).setLeftFootContact(true);
            }
            if (tmpB.equals("monsterLeftFoot") && tmpA.equals("texture")) {
                ((PhysBodyMonster) physBodyB).setLeftFootContact(true);
            }
            if (tmpA.equals("monsterRightFoot") && tmpB.equals("texture")) {
                ((PhysBodyMonster) physBodyA).setRightFootContact(true);
            }
            if (tmpB.equals("monsterRightFoot") && tmpA.equals("texture")) {
                ((PhysBodyMonster) physBodyB).setRightFootContact(true);
            }
            if (tmpA.equals("Hero") && tmpB.equals("monsterSeeLeftSide")) {
                ((PhysBodyMonster) physBodyA).setLeftAttack(true);
            }
            if (tmpB.equals("Hero") && tmpA.equals("monsterSeeLeftSide")) {
                ((PhysBodyMonster) physBodyA).setLeftAttack(true);
            }
            if (tmpA.equals("Hero") && tmpB.equals("monsterSeeRightSide")) {
                ((PhysBodyMonster) physBodyA).setRightAttack(true);
            }
            if (tmpB.equals("Hero") && tmpA.equals("monsterSeeRightSide")) {
                ((PhysBodyMonster) physBodyA).setRightAttack(true);
            }
            if (tmpA.equals("monsterDamage") && tmpB.equals("Hero")) {
                ((PhysBodyMonster) physBodyA).setContactWithHero(true);
                player.setDamage(true);
            }
            if (tmpB.equals("monsterDamage") && tmpA.equals("Hero")) {
                ((PhysBodyMonster) physBodyB).setContactWithHero(true);
                player.setDamage(true);
            }
            if (tmpA.equals("monsterDamage") && tmpB.equals("bullet")) {
                ((PhysBodyMonster) physBodyA).setContactWithBullet(true);
                physBodyB.setActive(false);
            }
            if (tmpB.equals("monsterDamage") && tmpA.equals("bullet")) {
                ((PhysBodyMonster) physBodyB).setContactWithBullet(true);
                physBodyA.setActive(false);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if (a.getUserData() != null && b.getUserData() != null) {
            PhysBody physBodyA = (PhysBody) a.getBody().getUserData();
            PhysBody physBodyB = (PhysBody) b.getBody().getUserData();
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
            if (tmpA.equals("monsterLeftFoot") && tmpB.equals("texture")) {
                ((PhysBodyMonster) physBodyA).setLeftFootContact(false);
            }
            if (tmpB.equals("monsterLeftFoot") && tmpA.equals("texture")) {
                ((PhysBodyMonster) physBodyB).setLeftFootContact(false);
            }
            if (tmpA.equals("monsterRightFoot") && tmpB.equals("texture")) {
                ((PhysBodyMonster) physBodyA).setRightFootContact(false);
            }
            if (tmpB.equals("monsterRightFoot") && tmpA.equals("texture")) {
                ((PhysBodyMonster) physBodyB).setRightFootContact(false);
            }
            if (tmpA.equals("monsterSeeLeftSide") && tmpB.equals("Hero")) {
                ((PhysBodyMonster) physBodyA).setLeftAttack(false);
            }
            if (tmpB.equals("monsterSeeLeftSide") && tmpA.equals("Hero")) {
                ((PhysBodyMonster) physBodyB).setLeftAttack(false);
            }
            if (tmpA.equals("monsterSeeRightSide") && tmpB.equals("Hero")) {
                ((PhysBodyMonster) physBodyA).setRightAttack(false);
            }
            if (tmpB.equals("monsterSeeRightSide") && tmpA.equals("Hero")) {
                ((PhysBodyMonster) physBodyB).setRightAttack(false);
            }
            if (tmpA.equals("monsterDamage") && tmpB.equals("Hero")) {
                ((PhysBodyMonster) physBodyA).setContactWithHero(false);
            }
            if (tmpB.equals("monsterDamage") && tmpA.equals("Hero")) {
                ((PhysBodyMonster) physBodyB).setContactWithHero(false);
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
