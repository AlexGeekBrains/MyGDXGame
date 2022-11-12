package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyAtlasAnim;

import java.awt.*;

public class WinScreen implements Screen {
    private Game game;
    private Texture buttonExit, buttonRestart, gameWin, background;
    private SpriteBatch batch;
    private int x, y;
    private Rectangle rectangleRestart, rectangleExit;
    private MyAtlasAnim firework;
//    private ShapeRenderer shape;

    public WinScreen(Game game) {
        this.game = game;
        firework = new MyAtlasAnim("atlas/unnamed.atlas", "firework", 8, Animation.PlayMode.LOOP);
        gameWin = new Texture("screen/win.png");
        buttonExit = new Texture("screen/exit.png");
        buttonRestart = new Texture("screen/restart.png");
        background = new Texture("screen/background.png");
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
        ScreenUtils.clear(0.2235f, 0.6823f, 1f, 0);
        batch.begin();
        batch.draw(gameWin, Gdx.graphics.getWidth() / 2 - gameWin.getWidth() / 2, Gdx.graphics.getHeight() - gameWin.getHeight());
        firework.setTime(delta);
        batch.draw(firework.draw(), x + rectangleRestart.height * 2, Gdx.graphics.getHeight() / 2);
        batch.draw(firework.draw(), x - rectangleExit.width, Gdx.graphics.getHeight() / 2 - rectangleExit.height);
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
        this.gameWin.dispose();
        this.buttonExit.dispose();
        this.buttonRestart.dispose();
        this.batch.dispose();
//                this.shape.dispose();
    }
}
