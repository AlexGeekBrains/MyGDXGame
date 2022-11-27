package com.mygdx.game.physbody;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

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

    public boolean isLeftFootContact() {
        return leftFootContact;
    }

    public boolean isRightFootContact() {
        return rightFootContact;
    }

    public boolean isLeftAttack() {
        return leftAttack;
    }

    public boolean isRightAttack() {
        return rightAttack;
    }

    public boolean isContactWithHero() {
        return isContactWithHero;
    }

    public boolean isContactWithBullet() {
        return isContactWithBullet;
    }

    public boolean isContactWithBang() {
        return isContactWithBang;
    }

    public void setLeftFootContact(boolean leftFootContact) {
        this.leftFootContact = leftFootContact;
    }

    public void setRightFootContact(boolean rightFootContact) {
        this.rightFootContact = rightFootContact;
    }

    public void setLeftAttack(boolean leftAttack) {
        this.leftAttack = leftAttack;
    }

    public void setRightAttack(boolean rightAttack) {
        this.rightAttack = rightAttack;
    }

    public void setContactWithHero(boolean contactWithHero) {
        isContactWithHero = contactWithHero;
    }

    public void setContactWithBullet(boolean contactWithBullet) {
        isContactWithBullet = contactWithBullet;
    }

    public void setContactWithBang(boolean contactWithBang) {
        isContactWithBang = contactWithBang;
    }
}
