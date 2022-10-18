package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    private MyAtlasAnim stand, run, fight, tmpA;
    private MyInputProcessor myInputProcessor;
    private Sound sound;
    private float x;
    private float y;
    private float speed;

    public Player(MyInputProcessor myInputProcessor) {
        this.stand = new MyAtlasAnim("atlas/unnamed.atlas", "stay", 10);
        this.run = new MyAtlasAnim("atlas/unnamed.atlas", "run", 10);
        this.fight = new MyAtlasAnim("atlas/unnamed.atlas", "fight", 20);
        tmpA = stand;
        this.myInputProcessor = myInputProcessor;
        x = 0f;
        y = 0f;
        speed = 200f;
        sound = Gdx.audio.newSound(Gdx.files.internal("hit.mp3"));
    }

    public void render(SpriteBatch batch, float dt) {
        tmpA.setTime(dt);
        batch.draw(tmpA.draw(), x, y);
    }

    public void update(float dt) {
        checkMovement(dt);
    }

    public void checkMovement(float dt) {
        tmpA = stand;
        if (myInputProcessor.getOutString().contains("A")) {
            if (run.draw().isFlipX()) {
                run.draw().flip(true, false);
            }
            tmpA = run;
            x -= speed * dt;
        }
        if (myInputProcessor.getOutString().contains("D")) {
            if (!run.draw().isFlipX()) {
                run.draw().flip(true, false);
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
            tmpA = fight;
        }
        if(Gdx.input.isKeyJustPressed(62))sound.play();
    }

    public void dispose() {
        stand.dispose();
        run.dispose();
        fight.dispose();
        tmpA.dispose();
        sound.dispose();
    }
}