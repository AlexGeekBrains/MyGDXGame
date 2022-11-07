package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import lombok.Getter;

import java.util.ArrayList;

public class Player {
    private Physics physics;
    private MyInputProcessor myInputProcessor;
    @Getter
    private Body playerBody;
    private MyAtlasAnim stand, run, shot, currentDraw;
    @Getter
    private boolean isRightOrientation;
    private Sound sound;
    private boolean fire;
    private float xPhisOk = 0.250f;
    private float yPhisOk = 30.2f;
    private float xPhisDebug = 20000f;
    private float yPhisDebug = 90000f;
    private SpriteBatch batch;
    private ArrayList<Bullet> bullets;

    public Player(MyInputProcessor myInputProcessor, Body playerBody, Physics physics, SpriteBatch batch) {
        this.physics = physics;
        this.stand = new MyAtlasAnim("atlas/unnamed.atlas", "stay", 10, Animation.PlayMode.LOOP);
        this.run = new MyAtlasAnim("atlas/unnamed.atlas", "run", 10, Animation.PlayMode.LOOP);
        this.shot = new MyAtlasAnim("atlas/unnamed.atlas", "shot", 20, Animation.PlayMode.NORMAL);
        currentDraw = stand;
        this.myInputProcessor = myInputProcessor;
        this.playerBody = playerBody;
        this.physics = physics;
        sound = Gdx.audio.newSound(Gdx.files.internal("hit.mp3"));
        this.batch = batch;
        bullets = new ArrayList<>();
    }

    public void render(SpriteBatch batch, float dt) {
        currentDraw.setTime(dt);
        checkMovement();
        for (Bullet bullet : bullets) {
            bullet.render(batch, physics);
        }

        float correctX = 18f;//Корректировка, что бы картинка персонажа рисовалась в центре физической фигуры
        float correctY = 5f;
        PhysBody physBody = (PhysBody) playerBody.getUserData();
        batch.draw(currentDraw.draw(), (playerBody.getPosition().x * physics.getPPM() - physBody.getSize().x - correctX), (playerBody.getPosition().y * physics.getPPM()) - physBody.getSize().y - correctY);
    }

    public void checkMovement() {
        if (!fire) {
            changeStandOrShotOrientation(stand);
            currentDraw = stand;
            if (myInputProcessor.getOutString().contains("A")) {
                if (run.draw().isFlipX()) {
                    run.draw().flip(true, false);
                    isRightOrientation = false;
                }
                currentDraw = run;
                playerBody.applyForceToCenter(new Vector2(-xPhisOk, 0), true);
            }
            if (myInputProcessor.getOutString().contains("D")) {
                if (!run.draw().isFlipX()) {
                    run.draw().flip(true, false);
                    isRightOrientation = true;
                }
                currentDraw = run;
                playerBody.applyForceToCenter(new Vector2(xPhisOk, 0), true);
            }
//            if (myInputProcessor.getOutString().contains("W") && physics.getMyContList().isOnGround()) {
//                playerBody.applyForceToCenter(new Vector2(0, yPhisOk), true);
//            }
            if (Gdx.input.isKeyJustPressed(51) && physics.getMyContList().isOnGround()) {
                playerBody.applyForceToCenter(new Vector2(0, yPhisOk), true);
            }
            if (myInputProcessor.getOutString().contains("S")) {
                currentDraw = run;
            }
            if (Gdx.input.isKeyJustPressed(62)) {
                fire = true;
                sound.play();
                bullets.add(new Bullet(this, physics, new Vector2(50, 0)));
            }
        } else {
            changeStandOrShotOrientation(shot);
            currentDraw = shot;

            if (currentDraw.isAnimationOver()) {
                currentDraw.resetTime();
                fire = false;
            }
        }
    }

    private void changeStandOrShotOrientation(MyAtlasAnim anim) {
        if (isRightOrientation && !anim.draw().isFlipX()) {
            anim.draw().flip(true, false);
        }
        if (!isRightOrientation && anim.draw().isFlipX()) {
            anim.draw().flip(true, false);
        }
    }

    public void dispose() {
        stand.dispose();
        run.dispose();
        shot.dispose();
        currentDraw.dispose();
        sound.dispose();

    }
}