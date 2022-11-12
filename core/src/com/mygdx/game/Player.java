package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Player {
    private Physics physics;
    private MyInputProcessor myInputProcessor;
    @Getter
    private Body playerBody;
    @Getter
    private MyAtlasAnim standAnim, runAnim, shotAnim, deathAnim, currentDraw;
    @Getter
    private boolean isRightOrientation;
    private static final Sound HIT_SOUND = Gdx.audio.newSound(Gdx.files.internal("hit.mp3"));
    ;
    private boolean fire;
    private static final float X_PHYS_OK = 0.250f;
    private static final float Y_PHYS_OK = 30.2f;
    private static final float X_PHYS_DEBUG = 20000f;
    private static final float Y_PHYS_DEBUG = 90000f;
    @Getter
    private int coins;
    @Setter
    private boolean isDeath;

    @Getter
    private ArrayList<Destroyed> bulletsForRender;


    public Player(MyInputProcessor myInputProcessor, Body playerBody, Physics physics) {
        this.physics = physics;
        standAnim = new MyAtlasAnim("atlas/unnamed.atlas", "stay", 10, Animation.PlayMode.LOOP);
        runAnim = new MyAtlasAnim("atlas/unnamed.atlas", "run", 10, Animation.PlayMode.LOOP);
        shotAnim = new MyAtlasAnim("atlas/unnamed.atlas", "shot", 20, Animation.PlayMode.NORMAL);
        deathAnim = new MyAtlasAnim("atlas/unnamed.atlas", "death", 20, Animation.PlayMode.NORMAL);
        currentDraw = standAnim;
        this.myInputProcessor = myInputProcessor;
        this.playerBody = playerBody;
        this.physics = physics;
        bulletsForRender = new ArrayList<>();
    }


    public void render(SpriteBatch batch, float dt) {
        currentDraw.setTime(dt);
        checkMovement();
        float correctY = 4f; //Корректировка, что бы картинка персонажа касалась земли (видимо анимация кривая если это убрать то как будто парит в воздухе немного
        Rectangle rectangle = getPlayerRect();
        ((PolygonShape) playerBody.getFixtureList().get(0).getShape()).setAsBox(rectangle.width / 2, rectangle.height / 2);
        ((PolygonShape) playerBody.getFixtureList().get(playerBody.getFixtureList().size - 1).getShape()).setAsBox(rectangle.width / 15, rectangle.height / 15, new Vector2((rectangle.width / 2) - 6f / physics.getPPM(), -rectangle.height / 2), 0);
        ((PolygonShape) playerBody.getFixtureList().get(playerBody.getFixtureList().size - 2).getShape()).setAsBox(rectangle.width / 15, rectangle.height / 15, new Vector2((-rectangle.width / 2) + 6f / physics.getPPM(), -rectangle.height / 2), 0);
        batch.draw(currentDraw.draw(), rectangle.x, rectangle.y - correctY, rectangle.width * physics.getPPM(), rectangle.height * physics.getPPM());
    }


    public void checkMovement() {
        if (!fire && !isDeath) {
            changeAnimOrientation(standAnim);
            currentDraw = standAnim;
            if (myInputProcessor.getOutString().contains("A")) {
                if (runAnim.draw().isFlipX()) {
                    runAnim.draw().flip(true, false);
                    isRightOrientation = false;
                }
                currentDraw = runAnim;
                playerBody.applyForceToCenter(new Vector2(-X_PHYS_OK, 0), true);
            }
            if (myInputProcessor.getOutString().contains("D")) {
                if (!runAnim.draw().isFlipX()) {
                    runAnim.draw().flip(true, false);
                    isRightOrientation = true;
                }
                currentDraw = runAnim;
                playerBody.applyForceToCenter(new Vector2(X_PHYS_OK, 0), true);
            }
            if (Gdx.input.isKeyJustPressed(51) && physics.getMyContList().isOnGround()) {
                playerBody.applyForceToCenter(new Vector2(0, Y_PHYS_OK), true);
            }
            if (myInputProcessor.getOutString().contains("S")) {
                currentDraw = runAnim;
            }
            if (Gdx.input.isKeyJustPressed(62)) {
                fire = true;
                HIT_SOUND.play();
                bulletsForRender.add(new Bullet(this).shot(new Vector2(10, 0), physics));

            }
        }
        checkFire();
        checkDeath();
    }

    public void checkFire() {
        if (fire) {
            changeAnimOrientation(shotAnim);
            currentDraw = shotAnim;
            if (currentDraw.isAnimationOver()) {
                currentDraw.resetTime();
                fire = false;
            }
        }
    }

    public void checkDeath() {
        if (isDeath) {
            changeAnimOrientation(deathAnim);
            currentDraw = deathAnim;
        }
    }

    public void incrementCoins() {
        coins++;
        System.out.println(coins);
    }

    public Rectangle getPlayerRect() {
        TextureRegion tr = currentDraw.draw();
        float x = playerBody.getPosition().x * physics.getPPM() - tr.getRegionWidth() / 2;
        float y = playerBody.getPosition().y * physics.getPPM() - tr.getRegionHeight() / 2;
        float w = tr.getRegionWidth() / physics.getPPM();
        float h = tr.getRegionHeight() / physics.getPPM();
        return new Rectangle(x, y, w, h);
    }

    private void changeAnimOrientation(MyAtlasAnim anim) {
        if (isRightOrientation && !anim.draw().isFlipX()) {
            anim.draw().flip(true, false);
        }
        if (!isRightOrientation && anim.draw().isFlipX()) {
            anim.draw().flip(true, false);
        }
    }

    public void dispose() {
        standAnim.dispose();
        runAnim.dispose();
        shotAnim.dispose();
        currentDraw.dispose();
        HIT_SOUND.dispose();
    }
}