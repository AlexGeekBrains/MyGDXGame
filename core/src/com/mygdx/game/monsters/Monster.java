package com.mygdx.game.monsters;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Destroyed;
import com.mygdx.game.MyAtlasAnim;
import com.mygdx.game.Physics;
import com.mygdx.game.Player;
import com.mygdx.game.physbody.PhysBodyMonster;


public abstract class Monster implements Destroyed {
    protected Body bodyMonster;
    protected Player player;
    protected PhysBodyMonster physBody;
    protected float health;
    protected boolean isLive;
    protected boolean isRightOrient;
    protected boolean isLeftOrient;
    protected MyAtlasAnim currentDraw, walkRight, walkLeft, deathLeft, deathRight, leftFight, rightFight, damageRight, damageLeft;
    protected int correctY;
    protected int correctX;
    protected float dScale;
    protected boolean isSoundMissHit;
    protected boolean isSoundHit;

    public void checkHealth() {
        if (health <= 0) {
            isLive = false;
        }
    }

    public void checkBulletContact(float bulletDamage) {
        if (physBody.isContactWithBullet() && isLeftOrient && isLive) {
            currentDraw = damageLeft;
            if (currentDraw.isAnimationOver()) {
                currentDraw.resetTime();
                physBody.setContactWithBullet(false);
                health -= bulletDamage;
            }
        }
        if (physBody.isContactWithBullet() && isRightOrient && isLive) {
            currentDraw = damageRight;
            if (currentDraw.isAnimationOver()) {
                currentDraw.resetTime();
                physBody.setContactWithBullet(false);
                health -= bulletDamage;
            }
        }
    }

    public abstract void correctPolygonShape(Physics physics, float width, float height);

    public void checkMove(float xForceToCenter, float maxSped) {
        if (!physBody.isContactWithBullet() && isLive) {
            if (isRightOrient) {
                if (bodyMonster.getLinearVelocity().x < maxSped)
                    bodyMonster.applyForceToCenter(new Vector2(xForceToCenter, 0), true);
            }
            if (isLeftOrient) {
                if (bodyMonster.getLinearVelocity().x > -maxSped)
                    bodyMonster.applyForceToCenter(new Vector2(-xForceToCenter, 0), true);
            }
            if (bodyMonster.getLinearVelocity().x > 0 && !physBody.isLeftAttack() && !physBody.isRightAttack()) {
                currentDraw = walkRight;
            }
            if (bodyMonster.getLinearVelocity().x < 0 && !physBody.isLeftAttack() && !physBody.isRightAttack()) {
                currentDraw = walkLeft;
            }
        }
    }

    public void checkEndTexture() {
        if (!physBody.isLeftFootContact() && isLeftOrient && isLive) {
            isRightOrient = true;
            isLeftOrient = false;
        }
        if (!physBody.isRightFootContact() && isRightOrient && isLive) {
            isRightOrient = false;
            isLeftOrient = true;
        }
    }

    public void checkDeath() {
        if (!isLive && isRightOrient) {
            currentDraw = deathRight;
            physBody.setContactWithHero(false);
            if (currentDraw.isAnimationOver()) {
                physBody.setActive(false);
            }
        }
        if (!isLive && isLeftOrient) {
            currentDraw = deathLeft;
            physBody.setContactWithHero(false);
            if (currentDraw.isAnimationOver()) {
                physBody.setActive(false);
            }
        }
    }

    public void checkAttack(Sound hitMiss, Sound hit, float damage) {
        if (physBody.isLeftAttack() && physBody.isLeftFootContact() && !physBody.isContactWithBullet() && isLive) {
            currentDraw = leftFight;
            isRightOrient = false;
            isLeftOrient = true;
            if (isSoundMissHit) {
                hitMiss.play();
                isSoundMissHit = false;
            }
            if (currentDraw.isAnimationOver()) {
                currentDraw.resetTime();
                isSoundMissHit = true;
                isSoundHit = true;
            }
        }
        if (physBody.isRightAttack() && physBody.isRightFootContact() && !physBody.isContactWithBullet() && isLive) {
            currentDraw = rightFight;
            isRightOrient = true;
            isLeftOrient = false;
            if (isSoundMissHit) {
                hitMiss.play();
                isSoundMissHit = false;
            }
            if (currentDraw.isAnimationOver()) {
                currentDraw.resetTime();
                isSoundMissHit = true;
                isSoundHit = true;
            }
        }
        if (physBody.isContactWithHero() && isLive) {
            hitMiss.stop();
            if (isSoundHit) {
                hit.play();
                isSoundHit = false;
            }
            player.decreaseHealth(damage);
        }
    }

    public void checkBangContact(float damage) {
        if (physBody.isContactWithBang()) {
            health -= damage;
            System.out.println(health);
        }
    }

    public abstract void update();

    public void render(SpriteBatch batch, Physics physics, float dt) {
        if (bodyMonster != null) {
            update();
            currentDraw.setTime(dt);
            float width = currentDraw.draw().getRegionWidth() * dScale - correctX;
            float height = currentDraw.draw().getRegionHeight() * dScale - correctY;
            float x = bodyMonster.getPosition().x * physics.getPPM() - width / 2;
            float y = bodyMonster.getPosition().y * physics.getPPM() - height / 2;
            correctPolygonShape(physics, width, height);
            batch.draw(currentDraw.draw(), x, y - correctY, currentDraw.draw().getRegionWidth() * dScale, currentDraw.draw().getRegionHeight() * dScale);
        }
    }

    public void dispose() {
        walkRight.dispose();
        walkLeft.dispose();
        rightFight.dispose();
        leftFight.dispose();
        deathLeft.dispose();
        deathRight.dispose();
        damageLeft.dispose();
        damageRight.dispose();
    }
}