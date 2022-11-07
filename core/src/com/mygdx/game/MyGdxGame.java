package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
//    private SpriteBatch batch;
////    private Music music;
//    private MyInputProcessor myInputProcessor;
//    private Player player;
//    private PlayerControl playerControl;
//    private MyCamera camera;
//    private Physics physics;
//    private TiledMap map;
//    private OrthogonalTiledMapRenderer mapRenderer;
//
//    @Override
//    public void create() {
//        map = new TmxMapLoader().load("map/mp.tmx");
//        physics = new Physics();
//        myInputProcessor = new MyInputProcessor();
////        music = Gdx.audio.newMusic(Gdx.files.internal("Tetris_1984.mp3"));
////        music.setVolume(0.2f);
////        music.play();
//        Gdx.input.setInputProcessor(myInputProcessor);
//        batch = new SpriteBatch();
//        playerControl = new PlayerControl(myInputProcessor, physics.getBody());
//        player = new Player(playerControl, physics);
//        camera = new MyCamera(batch, physics.getBody(), physics);
//        mapRenderer = new OrthogonalTiledMapRenderer(map);
//    }
//
//    @Override
//    public void resize(int width, int height) {
//        camera.getOrthographicCamera().viewportWidth = width;
//        camera.getOrthographicCamera().viewportHeight = height;
//    }
//
//    @Override
//    public void render() {
//        float dt = Gdx.graphics.getDeltaTime();
//        ScreenUtils.clear(1, 1, 1, 0);
//        camera.render();
//        mapRenderer.setView(camera.getOrthographicCamera());
//        mapRenderer.render();
//        batch.begin();
//        System.out.println(myInputProcessor.getOutString());
//        player.render(batch, dt);
//        batch.end();
//        physics.step();
//        physics.debugDraw(camera.getOrthographicCamera());
//    }
//
//    @Override
//    public void dispose() {
//        playerControl.dispose();
//        batch.dispose();
//        physics.dispose();
//        map.dispose();
//        mapRenderer.dispose();
////        music.dispose();
//    }
}