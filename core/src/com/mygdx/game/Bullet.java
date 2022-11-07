package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Bullet {
    private int lifetime;
    private boolean isActive;
    private TextureRegion bulletRightImg, bulletLeftImg;

    private Body bodyBullet;
    private boolean isRightOrient;

    public Bullet(Player player, Physics physics, Vector2 vector2) {
        this.lifetime = 180;
        bodyBullet = physics.createBodyBullet(player);
        isActive = true;
        bulletLeftImg = new TextureRegion(MyAtlasAnim.getAtlas().findRegion("bullet"));
        bulletRightImg = new TextureRegion(MyAtlasAnim.getAtlas().findRegion("bullet"));
        bulletLeftImg.flip(true, false);
        if (player.isRightOrientation()) {
            isRightOrient = true;
            bodyBullet.applyForceToCenter(vector2, true);
        } else {
            bodyBullet.applyForceToCenter(-vector2.x, vector2.y, true);
            isRightOrient = false;
        }
    }

    public void render(SpriteBatch batch, Physics physics) {
        PhysBody physBody = (PhysBody) bodyBullet.getUserData();
        if (bodyBullet != null && physBody !=null) {
//            PhysBody physBody = (PhysBody) bodyBullet.getUserData();
            float x = bodyBullet.getPosition().x * physics.getPPM() - physBody.getSize().x;
            float y = bodyBullet.getPosition().y * physics.getPPM() - physBody.getSize().y;
            if (isRightOrient) {
                batch.draw(bulletRightImg, x, y);
            } else {
                batch.draw(bulletLeftImg, x, y);
            }
        }
    }

    private void deactivate() {
        isActive = false;
    }

}


