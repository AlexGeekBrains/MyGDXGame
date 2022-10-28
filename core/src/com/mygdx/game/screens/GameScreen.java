package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.*;

public class GameScreen implements Screen {
    private Game game;
    private SpriteBatch batch;
    private MyInputProcessor myInputProcessor;
    private Player player;
    private PlayerControl playerControl;
    private MyCamera camera;
    private Physics physics;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    public GameScreen(Game game) {
        this.game = game;
        map = new TmxMapLoader().load("map/mp.tmx");
        physics = new Physics(map);
        myInputProcessor = new MyInputProcessor();
        Gdx.input.setInputProcessor(myInputProcessor);
        batch = new SpriteBatch();
        playerControl = new PlayerControl(myInputProcessor, physics.getBody());
        player = new Player(playerControl, physics);
        camera = new MyCamera(batch, physics.getBody(), physics);
        mapRenderer = new OrthogonalTiledMapRenderer(map);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        float dt = Gdx.graphics.getDeltaTime();
        ScreenUtils.clear(1, 1, 1, 0);
        camera.render();
        mapRenderer.setView(camera.getOrthographicCamera());
        mapRenderer.render();
        batch.begin();
        System.out.println(myInputProcessor.getOutString());
        player.render(batch, dt);
        batch.end();
        physics.step();
        physics.debugDraw(camera.getOrthographicCamera());
        escExitToMenu();
    }

    private void escExitToMenu() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            dispose();
            game.setScreen(new MenuScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.getOrthographicCamera().viewportWidth = width;
        camera.getOrthographicCamera().viewportHeight = height;
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
        playerControl.dispose();
        batch.dispose();
        physics.dispose();
        map.dispose();
        mapRenderer.dispose();
    }
}
