package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.physbody.PhysBodyBox;


public class Box implements Destroyed {
    private TextureRegion box;
    private Body bodyBox;

    private PhysBodyBox physBodyBox;
    private final static float D_SCALE = 2;

    public Box(Body bodyBox) {
        this.bodyBox = bodyBox;
        physBodyBox = (PhysBodyBox) bodyBox.getUserData();
        if (physBodyBox.isTnt()) {
            box = MyAtlasAnim.getAtlas().findRegion("boxtnt");
        } else {
            box = MyAtlasAnim.getAtlas().findRegion("box");
        }
    }

    public void render(SpriteBatch batch, Physics physics, float dt) {
        float wight = box.getRegionWidth() / D_SCALE;
        float height = box.getRegionHeight() / D_SCALE;
        float x = bodyBox.getPosition().x * physics.getPPM() - wight / 2;
        float y = bodyBox.getPosition().y * physics.getPPM() - height / 2;
        ((PolygonShape) bodyBox.getFixtureList().get(0).getShape()).setAsBox(wight / 2 / physics.getPPM(), height / 2 / physics.getPPM());
        if (bodyBox != null && !physBodyBox.isTnt()) {
            batch.draw(box, x, y, wight, height);
        }
        if (bodyBox != null && !physBodyBox.isBang() && physBodyBox.isTnt()) {
            batch.draw(box, x, y, wight, height);
        }
    }

    public boolean checkBoom() {
        return physBodyBox.isBang();
    }

    @Override
    public Body getBody() {
        return bodyBox;
    }

    public TextureRegion getBox() {
        return box;
    }

    public PhysBodyBox getPhysBodyBox() {
        return physBodyBox;
    }
}
