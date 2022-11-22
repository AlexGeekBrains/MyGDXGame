package com.mygdx.game.monsters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Destroyed;
import com.mygdx.game.MyAtlasAnim;
import com.mygdx.game.Physics;
import com.mygdx.game.Player;
import com.mygdx.game.physbody.PhysBodyMonster;

public class Skeleton extends Monster implements Destroyed {
    private boolean isRightOrient;
    private boolean isLeftOrient;
    private boolean isLive;
    private static final Sound HIT_AIR = Gdx.audio.newSound(Gdx.files.internal("korotkiy-moschnyiy-zamah.mp3"));
    private static final Sound HIT = Gdx.audio.newSound(Gdx.files.internal("zvuk-udara.mp3"));
    private MyAtlasAnim runAnim, fightAnim, deathAnim, currentDraw, damage, leftDamage, leftDeath, leftFight, leftWalk;
    private final static int CORRECT_Y = 18;
    private final static int CORRECT_X = 18;
    private final static float D_SCALE = 2;
    private boolean isSoundHitAir;
    private boolean isSoundHit;

    public Skeleton(Body bodySkeleton, Player player) {
        super.bodyMonster = bodySkeleton;
        super.player = player;
        super.physBody = (PhysBodyMonster) bodySkeleton.getUserData();
        isRightOrient = true;
        isLeftOrient = false;
        leftDamage = new MyAtlasAnim("atlas/unnamed.atlas", "leftDamage", 15, Animation.PlayMode.NORMAL);
        leftDeath = new MyAtlasAnim("atlas/unnamed.atlas", "leftDeath", 10, Animation.PlayMode.NORMAL);
        leftFight = new MyAtlasAnim("atlas/unnamed.atlas", "leftFight", 15, Animation.PlayMode.LOOP);
        leftWalk = new MyAtlasAnim("atlas/unnamed.atlas", "leftWalk", 10, Animation.PlayMode.LOOP);
        runAnim = new MyAtlasAnim("atlas/unnamed.atlas", "scwalk", 10, Animation.PlayMode.LOOP);
        fightAnim = new MyAtlasAnim("atlas/unnamed.atlas", "schit", 15, Animation.PlayMode.LOOP);
        deathAnim = new MyAtlasAnim("atlas/unnamed.atlas", "scdeath", 20, Animation.PlayMode.NORMAL);
        damage = new MyAtlasAnim("atlas/unnamed.atlas", "scdamage", 15, Animation.PlayMode.NORMAL);
        currentDraw = fightAnim;
        isLive = true;
        super.health = 100;
        isSoundHitAir = true;
        isSoundHit = true;
    }

    public void update() {
        checkMove();
        checkBulletContact(20);
        checkEndTexture();
        checkAttack();
        checkHealth();
        checkDeath();
    }

    public void checkMove() {
        if (isRightOrient && isLive) {
            if (bodyMonster.getLinearVelocity().x < 2)
                bodyMonster.applyForceToCenter(new Vector2(0.28f, 0), true);
        }
        if (isLeftOrient && isLive) {
            if (bodyMonster.getLinearVelocity().x > -2)
                bodyMonster.applyForceToCenter(new Vector2(-0.28f, 0), true);
        }
        if (bodyMonster.getLinearVelocity().x > 0 && !physBody.isLeftAttack() && !physBody.isRightAttack() && !physBody.isContactWithBullet() && isLive) {
            currentDraw = runAnim;
        }
        if (bodyMonster.getLinearVelocity().x < 0 && !physBody.isLeftAttack() && !physBody.isRightAttack() && !physBody.isContactWithBullet() && isLive) {
            currentDraw = leftWalk;
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

    public void checkAttack() {
        if (physBody.isLeftAttack() && physBody.isLeftFootContact() && !physBody.isContactWithBullet() && isLive) {
            currentDraw = leftFight;
            isRightOrient = false;
            isLeftOrient = true;
            if (isSoundHitAir) {
                HIT_AIR.play();
                isSoundHitAir = false;
            }
            if (currentDraw.isAnimationOver()) {
                currentDraw.resetTime();
                isSoundHitAir = true;
                isSoundHit = true;
            }
        }
        if (physBody.isRightAttack() && physBody.isRightFootContact() && !physBody.isContactWithBullet() && isLive) {
            currentDraw = fightAnim;
            isRightOrient = true;
            isLeftOrient = false;
            if (isSoundHitAir) {
                HIT_AIR.play();
                isSoundHitAir = false;
            }
            if (currentDraw.isAnimationOver()) {
                currentDraw.resetTime();
                isSoundHitAir = true;
                isSoundHit = true;
            }
        }
        if (physBody.isContactWithHero() && isLive) {
            HIT_AIR.stop();
            if (isSoundHit) {
                HIT.play();
                isSoundHit = false;
            }
            player.decreaseHealth(0.6f);
        }
    }

    public void checkBulletContact(float bulletDamage) {
        if (physBody.isContactWithBullet() && isLeftOrient && isLive) {
            currentDraw = leftDamage;
            if (currentDraw.isAnimationOver()) {
                currentDraw.resetTime();
                physBody.setContactWithBullet(false);
                health -= bulletDamage;
            }
        }
        if (physBody.isContactWithBullet() && isRightOrient && isLive) {
            currentDraw = damage;
            if (currentDraw.isAnimationOver()) {
                currentDraw.resetTime();
                physBody.setContactWithBullet(false);
                health -= bulletDamage;
            }
        }
    }


    public void checkHealth() {
        if (health <= 0) {
            isLive = false;
        }
    }

    public void checkDeath() {
        if (!isLive && isRightOrient) {
            currentDraw = deathAnim;
            if (currentDraw.isAnimationOver()) {
                physBody.setActive(false);
            }
        }
        if (!isLive && isLeftOrient) {
            currentDraw = leftDeath;
            if (currentDraw.isAnimationOver()) {
                physBody.setActive(false);
            }
        }
    }

    public void render(SpriteBatch batch, Physics physics, float dt) {
        if (bodyMonster != null) {
            update();
            currentDraw.setTime(dt);
            float width = currentDraw.draw().getRegionWidth() * D_SCALE - CORRECT_X;
            float height = currentDraw.draw().getRegionHeight() * D_SCALE - CORRECT_Y;
            float x = bodyMonster.getPosition().x * physics.getPPM() - width / 2;
            float y = bodyMonster.getPosition().y * physics.getPPM() - height / 2;
            correctPolygonShape(physics, width, height);
            batch.draw(currentDraw.draw(), x, y - 18, currentDraw.draw().getRegionWidth() * D_SCALE, currentDraw.draw().getRegionHeight() * D_SCALE);
        }
    }

    public void correctPolygonShape(Physics physics, float width, float height) {
        float bigWidth = currentDraw.draw().getRegionWidth() * D_SCALE * 3;
        Array<Fixture> fixtureArray = bodyMonster.getFixtureList();
        for (int i = 0; i < fixtureArray.size; i++) {
            if (fixtureArray.get(i).getUserData().equals("skeleton")) {
                ((PolygonShape) fixtureArray.get(i).getShape()).setAsBox((width - CORRECT_X) / 2 / physics.getPPM(), height / 2 / physics.getPPM());
            }
            if (fixtureArray.get(i).getUserData().equals("monsterLeftFoot")) {
                ((PolygonShape) fixtureArray.get(i).getShape()).setAsBox(width / 20 / physics.getPPM(), height / 20 / physics.getPPM(), new Vector2((-(width - CORRECT_X) / 2 - 5f) / physics.getPPM(), -(height / 2) / physics.getPPM()), 0);
            }
            if (fixtureArray.get(i).getUserData().equals("monsterRightFoot")) {
                ((PolygonShape) fixtureArray.get(i).getShape()).setAsBox(width / 20 / physics.getPPM(), height / 20 / physics.getPPM(), new Vector2(((width - CORRECT_X) / 2 + 5f) / physics.getPPM(), -(height / 2) / physics.getPPM()), 0);
            }
            if (fixtureArray.get(i).getUserData().equals("monsterSeeLeftSide")) {
                if (isRightOrient) {
                    ((PolygonShape) fixtureArray.get(i).getShape()).setAsBox(width / 2 / physics.getPPM(), height / 3 / physics.getPPM(), new Vector2((-(width - CORRECT_X) / 2) / physics.getPPM(), (0) / physics.getPPM()), 0);
                }
                if (isLeftOrient) {
                    ((PolygonShape) fixtureArray.get(i).getShape()).setAsBox(bigWidth / 2 / physics.getPPM(), height / 3 / physics.getPPM(), new Vector2((-(bigWidth - CORRECT_X) / 2) / physics.getPPM(), (0) / physics.getPPM()), 0);
                }
            }
            if (fixtureArray.get(i).getUserData().equals("monsterSeeRightSide")) {
                if (isRightOrient) {
                    ((PolygonShape) fixtureArray.get(i).getShape()).setAsBox(bigWidth / 2 / physics.getPPM(), height / 3 / physics.getPPM(), new Vector2(((bigWidth + CORRECT_X) / 2) / physics.getPPM(), (0) / physics.getPPM()), 0);
                }
                if (isLeftOrient) {
                    ((PolygonShape) fixtureArray.get(i).getShape()).setAsBox(width / 2 / physics.getPPM(), height / 3 / physics.getPPM(), new Vector2(((width + CORRECT_X) / 2) / physics.getPPM(), (0) / physics.getPPM()), 0);
                }
            }
            if (fixtureArray.get(i).getUserData().equals("monsterDamage")) {
                ((PolygonShape) fixtureArray.get(i).getShape()).setAsBox((width - CORRECT_X) / 1.3f / physics.getPPM(), height / 3 / physics.getPPM());
            }
        }
    }


    @Override
    public Body getBody() {
        return bodyMonster;
    }
}
