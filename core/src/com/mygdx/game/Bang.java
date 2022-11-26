package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.physbody.PhysBodyBang;

public class Bang implements Destroyed {

    private MyAtlasAnim bang;
    private Body bodyBang;
    private PhysBodyBang physBodyBang;
    private Physics physics;
    private static final Sound BOOM = Gdx.audio.newSound(Gdx.files.internal("boom.mp3"));

    public Bang(Physics physics) {
        bang = new MyAtlasAnim("atlas/unnamed.atlas", "bang", 100, Animation.PlayMode.NORMAL);
        this.physics = physics;
    }

    public Bang start(Box box) {
        bodyBang = physics.createBang(box);
        physBodyBang = (PhysBodyBang) bodyBang.getUserData();
        BOOM.play();
        return this;
    }

    @Override
    public void render(SpriteBatch batch, Physics physics, float dt) {
        if (bodyBang != null) {
            bang.setTime(dt);
            batch.draw(bang.draw(), bodyBang.getPosition().x * physics.getPPM() - physBodyBang.getRadius(), bodyBang.getPosition().y * physics.getPPM() - physBodyBang.getRadius());
            if (bang.isAnimationOver()) {
                physBodyBang.setActive(false);
            }
        }
    }

    @Override
    public Body getBody() {
        return bodyBang;
    }
}
