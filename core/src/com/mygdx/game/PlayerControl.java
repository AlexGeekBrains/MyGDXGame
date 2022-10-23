package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class PlayerControl {

    private MyInputProcessor myInputProcessor;
    private Body body;
    private MyAtlasAnim stand, run, fight, currentDraw;
    private boolean isRightOrientation;
    private Sound sound;


    public PlayerControl(MyInputProcessor myInputProcessor, Body body) {
        this.stand = new MyAtlasAnim("atlas/unnamed.atlas", "stay", 10, Animation.PlayMode.LOOP);
        this.run = new MyAtlasAnim("atlas/unnamed.atlas", "run", 10, Animation.PlayMode.LOOP);
        this.fight = new MyAtlasAnim("atlas/unnamed.atlas", "fight", 20, Animation.PlayMode.LOOP);
        currentDraw = stand;
        this.myInputProcessor = myInputProcessor;
        this.body = body;
        sound = Gdx.audio.newSound(Gdx.files.internal("hit.mp3"));
    }

    public void checkMovement() {
        changeStandOrFightOrientation(stand);
        currentDraw = stand;
        if (myInputProcessor.getOutString().contains("A")) {
            if (run.draw().isFlipX()) {
                run.draw().flip(true, false);
                isRightOrientation = false;
            }
            currentDraw = run;
            body.applyForceToCenter(new Vector2(-10f, 0), true);
        }
        if (myInputProcessor.getOutString().contains("D")) {
            if (!run.draw().isFlipX()) {
                run.draw().flip(true, false);
                isRightOrientation = true;
            }
            currentDraw = run;
            body.applyForceToCenter(new Vector2(10f, 0), true);
        }
        if (myInputProcessor.getOutString().contains("W")) {
            body.applyForceToCenter(new Vector2(0, 10), true);
        }
        if (myInputProcessor.getOutString().contains("S")) {
            currentDraw = run;
        }
        if (myInputProcessor.getOutString().contains("pace")) {
            changeStandOrFightOrientation(fight);
            currentDraw = fight;
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

    public MyAtlasAnim getCurrentDraw() {
        return currentDraw;
    }

    public Body getBody() {
        return body;
    }

    public void dispose() {
        stand.dispose();
        run.dispose();
        fight.dispose();
        currentDraw.dispose();
        sound.dispose();
    }
}
