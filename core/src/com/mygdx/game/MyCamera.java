package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public class MyCamera {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Body body;

    public MyCamera(SpriteBatch batch, Body body) {
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.batch = batch;
        this.body = body;
        camera.update();
    }

    public void render() {
        camera.position.x = body.getPosition().x;
        camera.position.y = body.getPosition().y + 150;
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

    public OrthographicCamera getOrthographicCamera() {
        return camera;
    }
}
