package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.physbody.PhysBody;

public class Box {
    private TextureRegion box;
    private Array<Body> boxes;

    public Box(Physics physics) {
        boxes = physics.getBodies("box");
        box = MyAtlasAnim.getAtlas().findRegion("box");
    }

    public void render(SpriteBatch batch, Physics physics) {
        for (int i = 0; i < boxes.size; i++) {
            PhysBody physBody = (PhysBody) boxes.get(i).getUserData();
            float x = boxes.get(i).getPosition().x * physics.getPPM() - physBody.getSize().x;
            float y = boxes.get(i).getPosition().y * physics.getPPM() - physBody.getSize().y;
            batch.draw(box, x, y, physBody.getSize().x * 2, physBody.getSize().y * 2);
        }
    }
}
