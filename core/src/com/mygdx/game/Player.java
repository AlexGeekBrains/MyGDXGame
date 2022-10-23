package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.MyAtlasAnim;
import com.mygdx.game.MyInputProcessor;

public class Player {
    private MyAtlasAnim stand, run, fight, tmpA;
    private MyInputProcessor myInputProcessor;
    private Sound sound;
    private float x;
    private float y;
    private float speed;
    private boolean isRightOrientation;
    private Body body;

    public Player(MyInputProcessor myInputProcessor, Body body) {
        this.stand = new MyAtlasAnim("atlas/unnamed.atlas", "stay", 10, Animation.PlayMode.LOOP);
        this.run = new MyAtlasAnim("atlas/unnamed.atlas", "run", 10, Animation.PlayMode.LOOP);
        this.fight = new MyAtlasAnim("atlas/unnamed.atlas", "fight", 20, Animation.PlayMode.LOOP);
        tmpA = stand;
        this.myInputProcessor = myInputProcessor;
        this.body = body;
        x = this.body.getPosition().x;
        y = this.body.getPosition().y;
        speed = 200f;
        sound = Gdx.audio.newSound(Gdx.files.internal("hit.mp3"));
    }

    public void render(SpriteBatch batch, float dt) {
        tmpA.setTime(dt);
        checkMovement(dt);
        batch.draw(tmpA.draw(), body.getPosition().x, body.getPosition().y);
    }

    public void checkMovement(float dt) {
        changeStandOrFightOrientation(stand);
        tmpA = stand;
        if (myInputProcessor.getOutString().contains("A")) {
            if (run.draw().isFlipX()) {
                run.draw().flip(true, false);
                isRightOrientation = false;
            }
            tmpA = run;
            x -= speed * dt;
        }
        if (myInputProcessor.getOutString().contains("D")) {
            if (!run.draw().isFlipX()) {
                run.draw().flip(true, false);
                isRightOrientation = true;
            }
            tmpA = run;
            x += speed * dt;
        }
        if (myInputProcessor.getOutString().contains("W")) {
            y += speed * dt;
            tmpA = run;
        }
        if (myInputProcessor.getOutString().contains("S")) {
            y -= speed * dt;
            tmpA = run;
        }
        if (myInputProcessor.getOutString().contains("pace")) {
            changeStandOrFightOrientation(fight);
            tmpA = fight;
        }
        if (Gdx.input.isKeyJustPressed(62)) {
            sound.play();
        }
    }

    private void changeStandOrFightOrientation(MyAtlasAnim anim) {
        if (isRightOrientation && !anim.draw().isFlipX()) {
            anim.draw().flip(true, false);
        }
        if (!isRightOrientation && anim.draw().isFlipX()) {
            anim.draw().flip(true, false);
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void dispose() {
        stand.dispose();
        run.dispose();
        fight.dispose();
        tmpA.dispose();
        sound.dispose();
    }
}
