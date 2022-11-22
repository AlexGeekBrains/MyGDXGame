package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HeadsUpDisplay {
    private final Label label;
    private Player player;
    private MyCamera camera;
    private TextureRegion regionHealth, regionCoin, regionBullet;

    public HeadsUpDisplay(Player player, MyCamera camera) {
        label = new Label(30, Color.WHITE);
        this.player = player;
        this.camera = camera;
        regionHealth = MyAtlasAnim.getAtlas().findRegion("shot");
        regionCoin = MyAtlasAnim.getAtlas().findRegion("coin");
        regionBullet = MyAtlasAnim.getAtlas().findRegion("bullet");
    }

    public void draw(SpriteBatch batch) {
        float upperLeftCornerX = camera.getOrthographicCamera().position.x - camera.getOrthographicCamera().viewportWidth / 2;
        float upperLeftCornerY = camera.getOrthographicCamera().position.y + camera.getOrthographicCamera().viewportHeight / 2;
        batch.draw(regionHealth, upperLeftCornerX, upperLeftCornerY - label.getSize() + 5, label.getSize(), label.getSize());
        label.draw(batch, "x" + player.getHealth(), upperLeftCornerX + label.getSize(), upperLeftCornerY);
        batch.draw(regionCoin, upperLeftCornerX +label.getSize()*4, upperLeftCornerY -label.getSize()/1.3f, label.getSize()/1.3f,label.getSize()/1.3f);
        label.draw(batch, "x" + player.getCoins(), upperLeftCornerX +label.getSize()*5,upperLeftCornerY);
        batch.draw(regionBullet,upperLeftCornerX +label.getSize()*7, upperLeftCornerY -label.getSize()/1.3f, label.getSize()/1.3f,label.getSize()/1.3f);
        label.draw(batch, "x" + player.getBulletsInClip(), upperLeftCornerX +label.getSize()*8,upperLeftCornerY);
    }

    public void dispose(){
        label.dispose();
    }

}
