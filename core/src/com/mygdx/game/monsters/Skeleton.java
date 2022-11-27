package com.mygdx.game.monsters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
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
    private static final Sound HIT_MISS = Gdx.audio.newSound(Gdx.files.internal("korotkiy-moschnyiy-zamah.mp3"));
    private static final Sound HIT = Gdx.audio.newSound(Gdx.files.internal("zvuk-udara.mp3"));


    public Skeleton(Body bodySkeleton, Player player) {
        super.bodyMonster = bodySkeleton;
        super.player = player;
        super.physBody = (PhysBodyMonster) bodySkeleton.getUserData();
        super.isRightOrient = true;
        super.isLeftOrient = false;
        super.damageLeft = new MyAtlasAnim("atlas/unnamed.atlas", "leftDamage", 15, Animation.PlayMode.NORMAL);
        super.deathLeft = new MyAtlasAnim("atlas/unnamed.atlas", "leftDeath", 10, Animation.PlayMode.NORMAL);
        super.leftFight = new MyAtlasAnim("atlas/unnamed.atlas", "leftFight", 15, Animation.PlayMode.LOOP);
        super.walkLeft = new MyAtlasAnim("atlas/unnamed.atlas", "leftWalk", 10, Animation.PlayMode.LOOP);
        super.walkRight = new MyAtlasAnim("atlas/unnamed.atlas", "scwalk", 10, Animation.PlayMode.LOOP);
        super.rightFight = new MyAtlasAnim("atlas/unnamed.atlas", "schit", 15, Animation.PlayMode.LOOP);
        super.deathRight = new MyAtlasAnim("atlas/unnamed.atlas", "scdeath", 10, Animation.PlayMode.NORMAL);
        super.damageRight = new MyAtlasAnim("atlas/unnamed.atlas", "scdamage", 15, Animation.PlayMode.NORMAL);
        super.currentDraw = rightFight;
        super.isLive = true;
        super.health = 100;
        super.isSoundMissHit = true;
        super.isSoundHit = true;
        super.correctY = 18;
        super.correctX = 18;
        super.dScale = 2;
    }

    @Override
    public void update() {
        checkMove(0.28f, 2f); //
        checkBulletContact(20);
        checkBangContact(0.5f);
        checkEndTexture();
        checkAttack(HIT_MISS, HIT, 0.1f);
        checkHealth();
        checkDeath();
    }

    @Override
    public void correctPolygonShape(Physics physics, float width, float height) {
        float bigWidth = currentDraw.draw().getRegionWidth() * dScale * 3;
        Array<Fixture> fixtureArray = bodyMonster.getFixtureList();
        for (int i = 0; i < fixtureArray.size; i++) {
            if (fixtureArray.get(i).getUserData().equals("skeleton")) {
                ((PolygonShape) fixtureArray.get(i).getShape()).setAsBox((width - correctX) / 2 / physics.getPPM(), height / 2 / physics.getPPM());
            }
            if (fixtureArray.get(i).getUserData().equals("monsterLeftFoot")) {
                ((PolygonShape) fixtureArray.get(i).getShape()).setAsBox(width / 20 / physics.getPPM(), height / 20 / physics.getPPM(), new Vector2((-(width - correctX) / 2 - 5f) / physics.getPPM(), -(height / 2) / physics.getPPM()), 0);
            }
            if (fixtureArray.get(i).getUserData().equals("monsterRightFoot")) {
                ((PolygonShape) fixtureArray.get(i).getShape()).setAsBox(width / 20 / physics.getPPM(), height / 20 / physics.getPPM(), new Vector2(((width - correctX) / 2 + 5f) / physics.getPPM(), -(height / 2) / physics.getPPM()), 0);
            }
            if (fixtureArray.get(i).getUserData().equals("monsterSeeLeftSide")) {
                if (isRightOrient) {
                    ((PolygonShape) fixtureArray.get(i).getShape()).setAsBox(width / 2 / physics.getPPM(), height / 3 / physics.getPPM(), new Vector2((-(width - correctX) / 2) / physics.getPPM(), (0) / physics.getPPM()), 0);
                }
                if (isLeftOrient) {
                    ((PolygonShape) fixtureArray.get(i).getShape()).setAsBox(bigWidth / 2 / physics.getPPM(), height / 3 / physics.getPPM(), new Vector2((-(bigWidth - correctX) / 2) / physics.getPPM(), (0) / physics.getPPM()), 0);
                }
            }
            if (fixtureArray.get(i).getUserData().equals("monsterSeeRightSide")) {
                if (isRightOrient) {
                    ((PolygonShape) fixtureArray.get(i).getShape()).setAsBox(bigWidth / 2 / physics.getPPM(), height / 3 / physics.getPPM(), new Vector2(((bigWidth + correctX) / 2) / physics.getPPM(), (0) / physics.getPPM()), 0);
                }
                if (isLeftOrient) {
                    ((PolygonShape) fixtureArray.get(i).getShape()).setAsBox(width / 2 / physics.getPPM(), height / 3 / physics.getPPM(), new Vector2(((width + correctX) / 2) / physics.getPPM(), (0) / physics.getPPM()), 0);
                }
            }
            if (fixtureArray.get(i).getUserData().equals("monsterDamage")) {
                ((PolygonShape) fixtureArray.get(i).getShape()).setAsBox((width - correctX) / 1.3f / physics.getPPM(), height / 3 / physics.getPPM());
            }
        }
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
