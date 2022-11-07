package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.*;


public class GameScreen implements Screen {
    private Game game;
    private SpriteBatch batch;
    private MyInputProcessor myInputProcessor;
    private Player player;
    private MyCamera camera;
    private Physics physics;
    private TiledMap map;
    private Body playerBody;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Box box;


    public GameScreen(Game game) {
        this.game = game;
        map = new TmxMapLoader().load("map/mp.tmx");
        physics = new Physics();
        physics.createPhysObj(map.getLayers().get("textures"));
        playerBody = physics.createBodyPlayer(map.getLayers().get("player"));
        myInputProcessor = new MyInputProcessor();
        Gdx.input.setInputProcessor(myInputProcessor);
        batch = new SpriteBatch();
        player = new Player(myInputProcessor, playerBody, physics, batch);
        camera = new MyCamera(batch, playerBody, physics);
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        box = new Box(physics);
        box.test();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update();
        float dt = Gdx.graphics.getDeltaTime();
        ScreenUtils.clear(0.2235f, 0.6823f, 1f, 0);
        camera.render();
        mapRenderer.setView(camera.getOrthographicCamera());
        mapRenderer.render();
        batch.begin();
        box.render(batch, physics);
        player.render(batch, dt);
        batch.end();
        physics.step();
        physics.debugDraw(camera.getOrthographicCamera());
        escExitToMenu();
    }

    private void update() { //вынес в отдельный метод потому как решил что проблема может быть в этом, еще игра стала подвисать из за этого иногда
        for (int i = 0; i < physics.getMyContList().getDestroyObj().size(); i++) {
            physics.destroyBody(physics.getMyContList().getDestroyObj().get(i));
        }
        physics.getMyContList().getDestroyObj().clear();
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
        player.dispose();
        batch.dispose();
        physics.dispose();
        map.dispose();
        mapRenderer.dispose();
    }
}
