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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.*;
import com.mygdx.game.monsters.Skeleton;
import com.mygdx.game.physbody.PhysBody;

import java.util.ArrayList;


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
    private int winCondition;
    private ArrayList<Destroyed> coins;
    private ArrayList<Destroyed> skeletons;

    public GameScreen(Game game) {
        this.game = game;
        map = new TmxMapLoader().load("map/mp.tmx");
        physics = new Physics();
        physics.createPhysObj(map.getLayers().get("textures"));
        playerBody = physics.createPhysObj(map.getLayers().get("player"));

        myInputProcessor = new MyInputProcessor();
        Gdx.input.setInputProcessor(myInputProcessor);
        batch = new SpriteBatch();
        player = new Player(myInputProcessor, playerBody, physics);
        physics.getMyContList().setPlayer(player);
        camera = new MyCamera(batch, playerBody, physics);
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        box = new Box(physics);

        Array<Body> bodyCoin = physics.getBodies("coin");
        winCondition = bodyCoin.size;
        coins = new ArrayList<>();
        for (int i = 0; i < bodyCoin.size; i++) {
            coins.add(new Coin(coins, bodyCoin.get(i)));
        }

        Array<Body> bodySkeleton = physics.getBodies("skeleton");
        skeletons = new ArrayList<>();
        for (int i = 0; i < bodySkeleton.size; i++) {
            skeletons.add(new Skeleton(bodySkeleton.get(i), player));
        }

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        float dt = Gdx.graphics.getDeltaTime();
        ScreenUtils.clear(0.2235f, 0.6823f, 1f, 0);
        camera.render();
        mapRenderer.setView(camera.getOrthographicCamera());
        mapRenderer.render();
        batch.begin();
        box.render(batch, physics);
        player.render(batch, dt);
        updateActiveObjArr(player.getBulletsForRender());
        updateActiveObjArr(coins);
        updateActiveObjArr(skeletons);
        for (Destroyed bullet : player.getBulletsForRender()) {
            bullet.render(batch, physics, dt);
        }
        for (Destroyed coin : coins) {
            coin.render(batch, physics, dt);
        }

        for (Destroyed sc : skeletons) {
            sc.render(batch, physics, dt);
        }
        batch.end();
        physics.step();
        physics.debugDraw(camera.getOrthographicCamera());
        escExitToMenu();
        listenPlayerAndCallGameOverScreenIfDeath();
        listenPlayerAndCallWinScreenIfWin();
    }

    private void escExitToMenu() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            dispose();
            game.setScreen(new MenuScreen(game));
        }
    }

    private void listenPlayerAndCallGameOverScreenIfDeath() {
        if (player.getDeathAnim().isAnimationOver()) {
            dispose();
            game.setScreen(new GameOverScreen(game));
        }
    }

    private void listenPlayerAndCallWinScreenIfWin() {
        if (player.getCoins() == winCondition) {
            dispose();
            game.setScreen(new WinScreen(game));
        }
    }

    private void updateActiveObjArr(ArrayList<Destroyed> destroyed) {
        if (!destroyed.isEmpty()) {
            for (int i = 0; i < destroyed.size(); i++) {
                PhysBody physBody = (PhysBody) destroyed.get(i).getBody().getUserData();
                if (!physBody.isActive()) {
                    physics.destroyBody(destroyed.get(i).getBody());
                    destroyed.remove(i);
                }
            }
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
