package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public class MyCamera {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Body body;
    private Physics physics;

    public MyCamera(SpriteBatch batch, Body body,Physics physics) {
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.batch = batch;
        this.body = body;
        this.physics = physics;

        camera.update();
    }

    public void render() {
        camera.position.x = body.getPosition().x* physics.getPPM();
        camera.position.y = body.getPosition().y* physics.getPPM()+150;
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

    public OrthographicCamera getOrthographicCamera() {
        return camera;
    }
}
