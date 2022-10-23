package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Music music;
    private MyInputProcessor myInputProcessor;
    private Player player;
    private MyCamera camera;
    private Physics physics;
    private Body body;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    @Override
    public void create() {


        physics = new Physics();
        BodyDef bodyDef = new BodyDef();
//        bodyDef.gravityScale = 1;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, 0);
        FixtureDef fixtureDef = new FixtureDef();

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(10, 10);
        fixtureDef.shape = shape;
        fixtureDef.density = 1;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 1;

        body = physics.getWorld().createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData("cube");

        myInputProcessor = new MyInputProcessor();
        music = Gdx.audio.newMusic(Gdx.files.internal("Tetris_1984.mp3"));
        music.setVolume(0.2f);
        //        music.play();
        Gdx.input.setInputProcessor(myInputProcessor);
        batch = new SpriteBatch();
        player = new Player(myInputProcessor, body);
        camera = new MyCamera(player, batch, body);

        map = new TmxMapLoader().load("map/mp.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

    }

    @Override
    public void render() {
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