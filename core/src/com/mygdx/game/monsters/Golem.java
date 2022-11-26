package com.mygdx.game.monsters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyAtlasAnim;
import com.mygdx.game.Physics;
import com.mygdx.game.Player;
import com.mygdx.game.physbody.PhysBodyMonster;

public class Golem extends Monster {
    private int countDmg;
    private static final Sound HIT_MISS = Gdx.audio.newSound(Gdx.files.internal("brosili-ogromnyiy-kamen.mp3"));
    private static final Sound HIT = Gdx.audio.newSound(Gdx.files.internal("gromkiy-otvratitelnyiy-hrust-kostey.mp3"));

    public Golem(Body bodyGolem, Player player) {
        super.bodyMonster = bodyGolem;
        super.player = player;
        super.physBody = (PhysBodyMonster) bodyGolem.getUserData();
        super.isRightOrient = true;
        super.isLeftOrient = false;
        super.walkRight = new MyAtlasAnim("atlas/unnamed.atlas", "GolemWalkRight", 30, Animation.PlayMode.LOOP);
        super.walkLeft = new MyAtlasAnim("atlas/unnamed.atlas", "GolemWalkLeft", 30, Animation.PlayMode.LOOP);
        super.deathRight = new MyAtlasAnim("atlas/unnamed.atlas", "GolemDeadRight", 10, Animation.PlayMode.NORMAL);
        super.deathLeft = new MyAtlasAnim("atlas/unnamed.atlas", "GolemDeadLeft", 10, Animation.PlayMode.NORMAL);
        super.leftFight = new MyAtlasAnim("atlas/unnamed.atlas", "GolemAtkLeft", 20, Animation.PlayMode.LOOP);
        super.rightFight = new MyAtlasAnim("atlas/unnamed.atlas", "GolemAtkRight", 20, Animation.PlayMode.LOOP);
        super.damageRight = new MyAtlasAnim("atlas/unnamed.atlas", "GolemStyleRight", 25, Animation.PlayMode.NORMAL);
        super.damageLeft = new MyAtlasAnim("atlas/unnamed.atlas", "GolemStyleLeft", 25, Animation.PlayMode.NORMAL);
        super.currentDraw = walkRight;
        super.isSoundMissHit = true;
        super.isSoundHit = true;
        super.isLive = true;
        super.health = 100;
        super.correctY = 1;
        super.correctX = 1;
        super.dScale = 1.2f;
        countDmg = 3;
    }

    @Override
    public void correctPolygonShape(Physics physics, float width, float height) {
        float bigWidth = 140f;
        Array<Fixture> fixtureArray = bodyMonster.getFixtureList();
        for (int i = 0; i < fixtureArray.size; i++) {
            if (fixtureArray.get(i).getUserData().equals("golem")) {
                ((PolygonShape) fixtureArray.get(i).getShape()).setAsBox((width - correctX) / 2 / physics.getPPM(), height / 2 / physics.getPPM());
            }
            if (fixtureArray.get(i).getUserData().equals("monsterLeftFoot")) {
                ((PolygonShape) fixtureArray.get(i).getShape()).setAsBox(width / 20 / physics.getPPM(), height / 1.5f / physics.getPPM(), new Vector2((-(width - correctX) / 2 - 5f) / physics.getPPM(), -(height) / physics.getPPM()), 0); //сделал большие ноги так как неровная анимация и при ударе ноги отрывались, а у меня на контакте ног с землей построен метод атаки
            }
            if (fixtureArray.get(i).getUserData().equals("monsterRightFoot")) {
                ((PolygonShape) fixtureArray.get(i).getShape()).setAsBox(width / 20 / physics.getPPM(), height / 1.5f / physics.getPPM(), new Vector2(((width - correctX) / 2 + 5f) / physics.getPPM(), -(height) / physics.getPPM()), 0);
            }
            if (fixtureArray.get(i).getUserData().equals("monsterSeeLeftSide")) {
                if (isRightOrient) {
                    ((PolygonShape) fixtureArray.get(i).getShape()).setAsBox(width / physics.getPPM(), height / 3 / physics.getPPM(), new Vector2((-(width - correctX)) / physics.getPPM(), (0) / physics.getPPM()), 0);
                }
                if (isLeftOrient) {
                    ((PolygonShape) fixtureArray.get(i).getShape()).setAsBox(bigWidth / physics.getPPM(), height / 3 / physics.getPPM(), new Vector2((-(bigWidth - correctX)) / physics.getPPM(), (0) / physics.getPPM()), 0);
                }
            }
            if (fixtureArray.get(i).getUserData().equals("monsterSeeRightSide")) {
                if (isRightOrient) {
                    ((PolygonShape) fixtureArray.get(i).getShape()).setAsBox(bigWidth / physics.getPPM(), height / 3 / physics.getPPM(), new Vector2(((bigWidth + correctX)) / physics.getPPM(), (0) / physics.getPPM()), 0);
                }
                if (isLeftOrient) {
                    ((PolygonShape) fixtureArray.get(i).getShape()).setAsBox(width / physics.getPPM(), height / 3 / physics.getPPM(), new Vector2(((width + correctX)) / physics.getPPM(), (0) / physics.getPPM()), 0);
                }
            }
            if (fixtureArray.get(i).getUserData().equals("monsterDamage")) {
                ((PolygonShape) fixtureArray.get(i).getShape()).setAsBox((width - correctX) / 1.3f / physics.getPPM(), height / 3 / physics.getPPM());
            }
        }
    }

    @Override
    public void checkBulletContact(float bulletDamage) {
        if (countDmg > 0 && (physBody.isContactWithBullet() || physBody.isContactWithBang()) && isLive) {
            if (isLeftOrient) {
                currentDraw = damageLeft;
                if (currentDraw.isAnimationOver()) {
                    currentDraw.resetTime();
                    physBody.setContactWithBullet(false);
                    health -= bulletDamage;
                    countDmg--;
                }
            }
            if (isRightOrient) {
                currentDraw = damageRight;
                if (currentDraw.isAnimationOver()) {
                    currentDraw.resetTime();
                    physBody.setContactWithBullet(false);
                    health -= bulletDamage;
                    countDmg--;
                }
            }
        }
        if (countDmg <= 0 && physBody.isContactWithBullet() && isLive) {
            health -= bulletDamage;
            physBody.setContactWithBullet(false);
            System.out.println(health);
        }
    }

    @Override
    public void update() {
        checkEndTexture();
        checkBulletContact(5f);
        checkMove(1.9f, 1.8f);
        checkBangContact(0.3f);
        checkHealth();
        checkDeath();
        checkAttack(HIT_MISS, HIT, 0.5f);
    }

    @Override
    public void dispose() {
        super.dispose();
        HIT.dispose();
        HIT_MISS.dispose();
    }

    @Override
    public Body getBody() {
        return bodyMonster;
    }
}


