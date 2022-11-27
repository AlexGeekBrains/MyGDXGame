package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;


public class Coin implements Destroyed {
    private MyAtlasAnim coin;
    private Body bodyCoin;
    private final static float D_SCALE = 3;

    public Coin(Body body) {
        this.bodyCoin = body;
        coin = new MyAtlasAnim("atlas/unnamed.atlas", "coin", 10, Animation.PlayMode.LOOP);
    }

    @Override
    public Body getBody() {
        return bodyCoin;
    }

    public void render(SpriteBatch batch, Physics physics, float dt) {
        if (bodyCoin != null) {
            coin.setTime(dt);
            float wight = coin.draw().getRegionWidth() / D_SCALE;
            float height = coin.draw().getRegionHeight() / D_SCALE;
            float x = bodyCoin.getPosition().x * physics.getPPM() - wight / 2;
            float y = bodyCoin.getPosition().y * physics.getPPM() - height / 2;
            ((PolygonShape) bodyCoin.getFixtureList().get(0).getShape()).setAsBox(wight / 2 / physics.getPPM(), height / 2 / physics.getPPM());
            batch.draw(coin.draw(), x, y, wight, height);
        }
    }
}