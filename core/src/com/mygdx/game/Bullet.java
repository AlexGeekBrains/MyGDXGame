package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.physbody.PhysBody;

public class Bullet implements Destroyed {
    private int lifetime;
    private Player player;
    private TextureRegion bulletRightImg, bulletLeftImg;
    private Body bodyBullet;
    private boolean isRightOrient;
    private PhysBody physBody;

    public Bullet(Player player) {
        this.player = player;
        bulletLeftImg = new TextureRegion(MyAtlasAnim.getAtlas().findRegion("bullet"));
        bulletRightImg = new TextureRegion(MyAtlasAnim.getAtlas().findRegion("bullet"));
        bulletLeftImg.flip(true, false);
    }

    public Bullet shot(Vector2 vector2, Physics physics) {
        lifetime = 60;
        bodyBullet = physics.createBodyBullet(player);
        physBody = (PhysBody) bodyBullet.getUserData();
        if (player.isRightOrientation()) {
            isRightOrient = true;
            bodyBullet.applyForceToCenter(vector2, true);
        } else {
            bodyBullet.applyForceToCenter(-vector2.x, vector2.y, true);
            isRightOrient = false;
        }
        return this;
    }

    @Override
    public Body getBody() {
        return bodyBullet;
    }

    @Override
    public void render(SpriteBatch batch, Physics physics, float dt) {
        if (bodyBullet != null) {
            checkAndDeactivate();
            if (physBody.isActive()) {
                float x = bodyBullet.getPosition().x * physics.getPPM() - physBody.getSize().x;
                float y = bodyBullet.getPosition().y * physics.getPPM() - physBody.getSize().y;
                if (isRightOrient) {
                    batch.draw(bulletRightImg, x, y);
                } else {
                    batch.draw(bulletLeftImg, x, y);
                }
                lifetime -= 1 * dt;
            }
        }
    }

    private void checkAndDeactivate() {
        if (lifetime <= 0) physBody.setActive(false);
    }

    public Player getPlayer() {
        return player;
    }


}
