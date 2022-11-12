package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;

public class GameOverScreen implements Screen {
    private Game game;
    private Texture buttonExit, buttonRestart, gameOver;
    private SpriteBatch batch;
    private int x, y;
    private Rectangle rectangleRestart, rectangleExit;
//    private ShapeRenderer shape;

    public GameOverScreen(Game game) {
        this.game = game;
        gameOver = new Texture("screen/game over.png");
        buttonExit = new Texture("screen/exit.png");
        buttonRestart = new Texture("screen/restart.png");
        x = Gdx.graphics.getWidth() / 2 - buttonExit.getWidth() / 2;
        y = Gdx.graphics.getHeight() / 2 - buttonExit.getHeight() / 2;
        rectangleRestart = new Rectangle(x, y + buttonRestart.getHeight(), buttonRestart.getWidth(), buttonExit.getHeight());
        rectangleExit = new Rectangle(x, y, buttonExit.getWidth(), buttonExit.getHeight());
//        shape = new ShapeRenderer();
        batch = new SpriteBatch();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(gameOver, Gdx.graphics.getWidth() / 2 - gameOver.getWidth() / 2, Gdx.graphics.getHeight() - gameOver.getHeight());
        batch.draw(buttonExit, x, y);
        batch.draw(buttonRestart, x, y + buttonRestart.getHeight());
        batch.end();
//        shape.begin(ShapeRenderer.ShapeType.Filled);  // оставил для дебага.
//        shape.rect(rectangleRestart.x, rectangleRestart.y, rectangleRestart.width,rectangleRestart.height);
//        shape.rect(rectangleExit.x, rectangleExit.y, rectangleExit.width,rectangleExit.height);
//        shape.end();
        checkPushRestart();
        checkPushExit();
    }

    private void checkPushRestart() {
        if (Gdx.input.isTouched()) {
            if (rectangleRestart.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                game.setScreen(new GameScreen(game));
                dispose();
            }
        }
    }

    private void checkPushExit() {
        if (Gdx.input.isTouched()) {
            if (rectangleExit.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                game.setScreen(new MenuScreen(game));
                dispose();
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
        this.buttonExit.dispose();
        this.gameOver.dispose();
        this.buttonExit.dispose();
        this.buttonRestart.dispose();
        this.batch.dispose();
//        this.shape.dispose();
    }
}
