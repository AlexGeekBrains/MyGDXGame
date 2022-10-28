package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;


public class Physics {
    private final float PPM = 200;

    public float getPPM() {
        return PPM;
    }

    private  World world;
    private Box2DDebugRenderer dDebugRenderer;
    private BodyDef bodyDef;
    private FixtureDef fixtureDef;
    private PolygonShape shape;
    private Map map;
    private Body body;

    public Physics(Map map) {
        this.world = new World(new Vector2(0, -9.8f), true);
        dDebugRenderer = new Box2DDebugRenderer();
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
        shape = new PolygonShape();
        this.map = map;
        drawTextures();
        drawPlayer();
    }

    public void debugDraw(OrthographicCamera camera) {
        dDebugRenderer.render(world, camera.combined);
    }

    public void step() {
        world.step(1 / 60f, 3, 3);
    }

    public void dispose() {
        world.dispose();
        dDebugRenderer.dispose();
    }

    private void drawTextures() {
        bodyDef.type = BodyDef.BodyType.StaticBody;
        fixtureDef.shape = shape;
        fixtureDef.density = 1;
        fixtureDef.friction = 0.19f;
        fixtureDef.restitution = 0;
        MapLayer stat = map.getLayers().get("staticFlat");
        Array<RectangleMapObject> rect = stat.getObjects().getByType(RectangleMapObject.class);
        for (int i = 0; i < rect.size; i++) {
            float sx = (rect.get(i).getRectangle().width / 2 + rect.get(i).getRectangle().x);
            float sy = (rect.get(i).getRectangle().height / 2 + rect.get(i).getRectangle().y);
            float sw = (rect.get(i).getRectangle().width / 2);
            float sh = (rect.get(i).getRectangle().height / 2);
            bodyDef.position.set(sx/PPM, sy/PPM);
            shape.setAsBox(sw/PPM, sh/PPM);
            body = world.createBody(bodyDef);
            body.createFixture(fixtureDef).setUserData("staticFlat");
            body.setFixedRotation(true);
        }
//
//        bodyDef.type = BodyDef.BodyType.StaticBody;
//        fixtureDef.shape = shape;
//        fixtureDef.density = 1;
//        fixtureDef.friction = 1;
//        fixtureDef.restitution = 0;
//        stat = map.getLayers().get("staticTriangle");
//        rect = stat.getObjects().getByType(RectangleMapObject.class);
//        for (int i = 0; i < rect.size; i++) {
//            float sx = rect.get(i).getRectangle().x;
//            float sy = rect.get(i).getRectangle().y;
//            float sw = rect.get(i).getRectangle().width;
//            float sh = rect.get(i).getRectangle().height;
//            bodyDef.position.set(sx, sy);
//            shape.setAsBox(sw, sh);
//            world.createBody(bodyDef).createFixture(fixtureDef).setUserData("staticTriangle");
//        }
    }

    private void drawPlayer() {
        bodyDef.gravityScale = 1;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        MapLayer hero = map.getLayers().get("player");
        RectangleMapObject rectHero = (RectangleMapObject) hero.getObjects().get("Hero");
        float x = (rectHero.getRectangle().width / 2 + rectHero.getRectangle().x) ;
        float y = (rectHero.getRectangle().height / 2 + rectHero.getRectangle().y);
        float w = (rectHero.getRectangle().width / 2) ;
        float h = (rectHero.getRectangle().height /2);

        bodyDef.position.set(x/PPM, y/PPM);
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(w/PPM, h/PPM);
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.19f;
        fixtureDef.restitution = 0;
        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData("hero");
        body.setFixedRotation(true);
    }

    public Body getBody() {
        return body;
    }
}
