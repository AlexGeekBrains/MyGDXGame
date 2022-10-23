package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public class MyCamera {
    private OrthographicCamera camera;
    private Player player;
    private SpriteBatch batch;
    private Body body;


    public MyCamera(Player player, SpriteBatch batch, Body body) {
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        this.batch = batch;
        this.player=player;
        this.body = body;
        camera.position.x = body.getPosition().x;
        camera.position.y =body.getPosition().y;
        camera.update();
    }

    public void render(){
        camera.position.x= body.getPosition().x;
        camera.position.y= body.getPosition().y;
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

    public OrthographicCamera getOrthographicCamera() {
        return camera;
    }
}
