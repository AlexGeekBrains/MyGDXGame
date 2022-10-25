package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Music music;
    private MyInputProcessor myInputProcessor;
    private Player player;

    @Override
    public void create() {
        myInputProcessor = new MyInputProcessor();
        music = Gdx.audio.newMusic(Gdx.files.internal("Tetris_1984.mp3"));
        music.setVolume(0.2f);
        music.play();
        Gdx.input.setInputProcessor(myInputProcessor);
        batch = new SpriteBatch();
        player = new Player(myInputProcessor);
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);
        ScreenUtils.clear(1, 1, 1, 0);
        batch.begin();
        System.out.println(myInputProcessor.getOutString());
        player.render(batch, dt);
        batch.end();
    }

    public void update(float dt) {
        player.update(dt);
    }

    @Override
    public void dispose() {
        player.dispose();
        batch.dispose();
    }
}