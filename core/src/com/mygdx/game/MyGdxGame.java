package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    GameAnimation bang;

    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("bang.png");
        bang = new GameAnimation("bang.png", 8, 4, 30);
    }

    @Override
    public void render() {
        ScreenUtils.clear(1, 1, 1, 0);
        bang.setTime(Gdx.graphics.getDeltaTime());
        float x = Gdx.input.getX() - bang.draw().getRegionWidth() / 2;
        float y = Gdx.graphics.getHeight() - (Gdx.input.getY() + bang.draw().getRegionHeight() / 2);
        batch.begin();
        batch.draw(bang.draw(), x, y);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        bang.dispose();
    }
}