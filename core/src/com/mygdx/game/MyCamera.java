package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public class MyCamera {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Physics physics;
    private Body playerBody;

    public MyCamera(SpriteBatch batch, Body playerBody, Physics physics) {
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.batch = batch;
        this.physics = physics;
        this.playerBody = playerBody;
        camera.update();
    }

    public void render() {
        camera.position.x = playerBody.getPosition().x * physics.getPPM();
        camera.position.y = playerBody.getPosition().y * physics.getPPM();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

    public OrthographicCamera getOrthographicCamera() {
        return camera;
    }
}
