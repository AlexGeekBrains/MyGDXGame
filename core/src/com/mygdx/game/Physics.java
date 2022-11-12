package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;


public class Physics {
    private final float PPM = 100;

    public float getPPM() {
        return PPM;
    }

    private World world;
    private Box2DDebugRenderer dDebugRenderer;
    private BodyDef bodyDef;
    private FixtureDef fixtureDef;
    private PolygonShape shape;
    private Body body;
    private MyContList myContList;

    public MyContList getMyContList() {
        return myContList;
    }

    public Physics() {
        this.world = new World(new Vector2(0, -9.8f), true);
        dDebugRenderer = new Box2DDebugRenderer();
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
        shape = new PolygonShape();
        myContList = new MyContList();
        world.setContactListener(myContList);
    }

    public void destroyBody(Body body) {
        world.destroyBody(body);
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
        shape.dispose();
    }

    public Array<Body> getBodies(String name) {
        Array<Body> bodies = new Array<>();
        world.getBodies(bodies);
        Iterator<Body> it = bodies.iterator();
        while (it.hasNext()) {
            PhysBody pb = (PhysBody) it.next().getUserData();
            if (!pb.getName().equals(name)) it.remove();
        }
        return bodies;
    }


    public void createPhysObj(MapLayer textures) {
        fixtureDef.shape = shape;
        Array<RectangleMapObject> rect = textures.getObjects().getByType(RectangleMapObject.class);
        for (int i = 0; i < rect.size; i++) {
            if (rect.get(i).getProperties().get("BodyType") != null) {
                String type = (String) rect.get(i).getProperties().get("BodyType");
                switch (type) {
                    case "StaticBody":
                        bodyDef.type = BodyDef.BodyType.StaticBody;
                        break;
                    case "DynamicBody":
                        bodyDef.type = BodyDef.BodyType.DynamicBody;
                        break;
                }
            }
            if (rect.get(i).getProperties().get("density") != null)
                fixtureDef.density = (float) rect.get(i).getProperties().get("density");
            if (rect.get(i).getProperties().get("friction") != null)
                fixtureDef.friction = (float) rect.get(i).getProperties().get("friction");
            if (rect.get(i).getProperties().get("restitution") != null)
                fixtureDef.restitution = (float) rect.get(i).getProperties().get("restitution");
            if (rect.get(i).getProperties().get("gravityScale") != null)
                bodyDef.gravityScale = (float) rect.get(i).getProperties().get("gravityScale");
            float x = (rect.get(i).getRectangle().width / 2 + rect.get(i).getRectangle().x);
            float y = (rect.get(i).getRectangle().height / 2 + rect.get(i).getRectangle().y);
            float w = (rect.get(i).getRectangle().width / 2);
            float h = (rect.get(i).getRectangle().height / 2);
            bodyDef.position.set(x / PPM, y / PPM);
            shape.setAsBox(w / PPM, h / PPM);
            body = world.createBody(bodyDef);
            body.setUserData(new PhysBody(rect.get(i).getName(), new Vector2(w, h), body));
            body.createFixture(fixtureDef).setUserData(rect.get(i).getName());
            if (rect.get(i).getName().equals("coin")) body.getFixtureList().get(0).setSensor(true);
        }
    }

    public Body createBodyPlayer(MapLayer hero) {
        fixtureDef.shape = shape;
        RectangleMapObject rectHero = (RectangleMapObject) hero.getObjects().get("Hero");
        if (rectHero.getProperties().get("gravityScale") != null)
            bodyDef.gravityScale = (float) rectHero.getProperties().get("gravityScale");
        String type = (String) rectHero.getProperties().get("BodyType");
        switch (type) {
            case "StaticBody":
                bodyDef.type = BodyDef.BodyType.StaticBody;
                break;
            case "DynamicBody":
                bodyDef.type = BodyDef.BodyType.DynamicBody;
                break;
        }
        float x = (rectHero.getRectangle().width / 2 + rectHero.getRectangle().x);
        float y = (rectHero.getRectangle().height / 2 + rectHero.getRectangle().y);
        float w = (rectHero.getRectangle().width / 2);
        float h = (rectHero.getRectangle().height / 2);
        bodyDef.position.set(x / PPM, y / PPM);
        shape.setAsBox(w / PPM, h / PPM);
        if (rectHero.getProperties().get("density") != null)
            fixtureDef.density = (float) rectHero.getProperties().get("density");
        if (rectHero.getProperties().get("restitution") != null)
            fixtureDef.restitution = (float) rectHero.getProperties().get("restitution");
        if (rectHero.getProperties().get("friction") != null)
            fixtureDef.friction = (float) rectHero.getProperties().get("friction");
        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData(rectHero.getName());
        body.setUserData(new PhysBody(rectHero.getName(), new Vector2(w, h), body));
        body.setFixedRotation(true);

        shape.setAsBox((w / 10) / PPM, (h / 10) / PPM, new Vector2((-w / PPM) / PPM, -h / PPM), 0);
        body.createFixture(fixtureDef).setUserData("leftFoot");
        body.getFixtureList().get(body.getFixtureList().size - 1).setSensor(true);

        shape.setAsBox((w / 10) / PPM, (h / 10) / PPM, new Vector2((w / PPM) / PPM, -h / PPM), 0);
        body.createFixture(fixtureDef).setUserData("rightFoot");
        body.getFixtureList().get(body.getFixtureList().size - 1).setSensor(true);
        return body;
    }

    public Body createBodyBullet(Player player) {
        float x = 0;
        float y = 0;
        fixtureDef.shape = shape;
        if (player.isRightOrientation()) {
            x = (player.getPlayerRect().getX() + player.getCurrentDraw().draw().getRegionWidth()) / PPM;
        } else {
            x = player.getPlayerRect().getX() / PPM;
        }
        y = player.getPlayerBody().getPosition().y;
        float w = 4;
        float h = 4;
        bodyDef.position.set(x, y);
        bodyDef.gravityScale = 0.2f;
        shape.setAsBox(w / PPM, h / PPM);
        fixtureDef.density = 1;
        fixtureDef.restitution = 0;
        fixtureDef.friction = 0;
        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData("bullet");
        body.setUserData(new PhysBody("bullet", new Vector2(w, h), body));
        body.setFixedRotation(true);
        body.setBullet(true);
        return body;
    }
}
