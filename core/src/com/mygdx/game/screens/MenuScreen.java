package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

import java.awt.*;

public class MenuScreen implements Screen {
    private Game game;
    private Texture background, buttonStart, buttonMusic, buttonOption;
    private SpriteBatch batch;
    private int x, y;
    private Rectangle rectangleStart, rectangleOptions;
    private Circle circleMusic;
    private ShapeRenderer shape;
    private Music music;

    public MenuScreen(Game game) {
        this.game = game;
        background = new Texture("screen/background.png");
        buttonStart = new Texture("screen/play.png");
        buttonMusic = new Texture("screen/melody.png");
        buttonOption = new Texture("screen/options.png");
        x = Gdx.graphics.getWidth() / 2 - buttonStart.getWidth() / 2;
        y = Gdx.graphics.getHeight() / 2 - buttonStart.getHeight() / 2;
        rectangleStart = new Rectangle(x, y, buttonOption.getWidth(), buttonStart.getHeight());
        rectangleOptions = new Rectangle(x, y + buttonOption.getHeight(), buttonOption.getWidth(), buttonOption.getHeight());
        circleMusic = new Circle(buttonMusic.getWidth() / 2, Gdx.graphics.getHeight() - buttonMusic.getHeight() / 2, buttonMusic.getHeight() / 2);
//        shape = new ShapeRenderer();
        batch = new SpriteBatch();
        music = Gdx.audio.newMusic(Gdx.files.internal("Tetris_1984.mp3"));
        music.setVolume(0.2f);
//        music.play();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(buttonStart, x, y);
        batch.draw(buttonOption, x, y + buttonOption.getHeight());
        batch.draw(buttonMusic, 0, circleMusic.y - buttonMusic.getHeight() / 2);
        batch.end();
//        shape.begin(ShapeRenderer.ShapeType.Filled);
//        shape.rect(rectangleStart.x, rectangleStart.y, rectangleStart.width,rectangleStart.height);
//        shape.rect(rectangleOptions.x, rectangleOptions.y, rectangleOptions.width,rectangleOptions.height);
//        shape.circle(circleMusic.x,circleMusic.y,circleMusic.radius);
//        shape.end();

        MusicOnOff();
        checkPushStart();
    }

    private void checkPushStart() {
        if (Gdx.input.isTouched()) {
            if (rectangleStart.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                game.setScreen(new GameScreen(game));
                dispose();
            }
        }
    }

    private void MusicOnOff() {
        if (Gdx.input.justTouched()) {
            if (circleMusic.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY()) && music.isPlaying()) {
                music.stop();
            } else {
                music.play();
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        this.buttonStart.dispose();
        this.background.dispose();
        this.batch.dispose();
        this.music.dispose();
//        this.shape.dispose();
    }
}
