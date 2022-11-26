package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.physbody.PhysBody;
import com.mygdx.game.physbody.PhysBodyBang;
import com.mygdx.game.physbody.PhysBodyBox;
import com.mygdx.game.physbody.PhysBodyMonster;

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
    private CircleShape circleShape;

    public MyContList getMyContList() {
        return myContList;
    }

    private Filter filter;

    public Physics() {
        this.world = new World(new Vector2(0, -9.8f), true);
        dDebugRenderer = new Box2DDebugRenderer();
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
        shape = new PolygonShape();
        circleShape = new CircleShape();
        myContList = new MyContList();
        world.setContactListener(myContList);
        filter = new Filter();
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
        circleShape.dispose();
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

    public Body createPhysObj(MapLayer textures) {
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

            if (rect.get(i).getName().equals("skeleton") || rect.get(i).getName().equals("golem")) {
                body.setUserData(new PhysBodyMonster(rect.get(i).getName(), new Vector2(w, h), body));
                body.createFixture(fixtureDef).setUserData(rect.get(i).getName());
                body.setFixedRotation(true);

                shape.setAsBox((w / 10) / PPM, (h / 10) / PPM, new Vector2((-w / PPM), -h / PPM), 0);
                body.createFixture(fixtureDef).setUserData("monsterLeftFoot");
                body.getFixtureList().get(body.getFixtureList().size - 1).setSensor(true);

                shape.setAsBox((w / 10) / PPM, (h / 10) / PPM, new Vector2((w / PPM), -h / PPM), 0);
                body.createFixture(fixtureDef).setUserData("monsterRightFoot");
                body.getFixtureList().get(body.getFixtureList().size - 1).setSensor(true);

                shape.setAsBox((w / 10) / PPM, (h / 10) / PPM, new Vector2((-w / PPM), h / PPM), 0);
                body.createFixture(fixtureDef).setUserData("monsterSeeLeftSide");
                body.getFixtureList().get(body.getFixtureList().size - 1).setSensor(true);

                shape.setAsBox((w / 10) / PPM, (h / 10) / PPM, new Vector2((w / PPM), h / PPM), 0);
                body.createFixture(fixtureDef).setUserData("monsterSeeRightSide");
                body.getFixtureList().get(body.getFixtureList().size - 1).setSensor(true);

                shape.setAsBox((w / 10) / PPM, (h / 10) / PPM, new Vector2((w / PPM), h / PPM), 0);
                body.createFixture(fixtureDef).setUserData("monsterDamage");
                body.getFixtureList().get(body.getFixtureList().size - 1).setSensor(true);
            } else if (rect.get(i).getName().equals("box")) {
                if (rect.get(i).getProperties().get("isTnt") != null) {
                    boolean isTnt = (boolean) rect.get(i).getProperties().get("isTnt");
                    body.setUserData(new PhysBodyBox(rect.get(i).getName(), new Vector2(w, h), body, isTnt));
                } else {
                    body.setUserData(new PhysBodyBox(rect.get(i).getName(), new Vector2(w, h), body));
                }
                body.createFixture(fixtureDef).setUserData(rect.get(i).getName());
                body.setFixedRotation(true);
            } else {
                body.setUserData(new PhysBody(rect.get(i).getName(), new Vector2(w, h), body));
                body.createFixture(fixtureDef).setUserData(rect.get(i).getName());
                if (rect.get(i).getName().equals("coin")) body.getFixtureList().get(0).setSensor(true);
                if (rect.get(i).getName().equals("Hero")) {
                    body.setFixedRotation(true);
                    shape.setAsBox((w / 10) / PPM, (h / 10) / PPM, new Vector2((-w / PPM) / PPM, -h / PPM), 0);
                    body.createFixture(fixtureDef).setUserData("leftFoot");
                    body.getFixtureList().get(body.getFixtureList().size - 1).setSensor(true);
                    shape.setAsBox((w / 10) / PPM, (h / 10) / PPM, new Vector2((w / PPM) / PPM, -h / PPM), 0);
                    body.createFixture(fixtureDef).setUserData("rightFoot");
                    body.getFixtureList().get(body.getFixtureList().size - 1).setSensor(true);
                }
            }
            //фильтра
            if (rect.get(i).getName().equals("skeleton") || rect.get(i).getName().equals("golem")) {
                filter.categoryBits = Types.Monster;
                filter.maskBits = Types.Texture | Types.Player | Types.Bullet;
                Array<Fixture> fixtureArray = body.getFixtureList();
                for (int j = 0; j < fixtureArray.size; j++) {
                    body.getFixtureList().get(j).setFilterData(filter);
                }
            }
            if (rect.get(i).getName().equals("box")) {
                filter.categoryBits = Types.Box;
                filter.maskBits = Types.Bullet | Types.Texture | Types.Player;
                body.getFixtureList().get(0).setFilterData(filter);
            }
            if (rect.get(i).getName().equals("coin")) {
                filter.categoryBits = Types.Coin;
                filter.maskBits = Types.Player;
                body.getFixtureList().get(0).setFilterData(filter);
            }
            if (rect.get(i).getName().equals("Hero")) {
                filter.categoryBits = Types.Player;
                filter.maskBits = Types.Texture | Types.Box | Types.Coin | Types.Monster;
                Array<Fixture> fixtureArray = body.getFixtureList();
                for (int j = 0; j < fixtureArray.size; j++) {
                    body.getFixtureList().get(j).setFilterData(filter);
                }
            }
            if (rect.get(i).getName().equals("water") || rect.get(i).getName().equals("texture")) {
                filter.categoryBits = Types.Texture;
                filter.maskBits = Types.Bang | Types.Monster | Types.Player | Types.Coin | Types.Bullet | Types.Box;
                body.getFixtureList().get(0).setFilterData(filter);
            }
        }
        return body;
    }

    public Body createBodyBullet(Player player) {
        float x;
        float y;
        fixtureDef.shape = shape;
        if (player.isRightOrientation()) {
            x = (player.getPlayerRect().getX() + player.getCurrentDraw().draw().getRegionWidth()) / PPM;
        } else {
            x = player.getPlayerRect().getX() / PPM;
        }
        y = player.getPlayerBody().getPosition().y;
        float w = 1;
        float h = 1;
        bodyDef.position.set(x, y);
        bodyDef.gravityScale = 0.2f;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        shape.setAsBox(w / PPM, h / PPM);
        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData("bullet");
        body.setUserData(new PhysBody("bullet", new Vector2(w, h), body));
        filter.categoryBits = Types.Bullet;
        filter.maskBits = Types.Texture | Types.Monster | Types.Box;
        body.getFixtureList().get(0).setFilterData(filter);
        body.setFixedRotation(true);
        body.setBullet(true);
        return body;
    }

    public Body createBang(Box box) {
        fixtureDef.shape = circleShape;
        float x = box.getBody().getPosition().x;
        float y = box.getBody().getPosition().y;
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        circleShape.setRadius(30 / PPM);
        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData("bang");
        body.setUserData(new PhysBodyBang("bang", body, 30));
        filter.categoryBits = Types.Bang;
        filter.maskBits = Types.Box;
        body.getFixtureList().get(0).setFilterData(filter);
        circleShape.setRadius(35 / PPM);
        body.createFixture(fixtureDef).setUserData("sensorBang");
        body.getFixtureList().get(body.getFixtureList().size - 1).setSensor(true);
        filter.categoryBits = Types.Texture;
        filter.maskBits = -1;
        body.getFixtureList().get(1).setFilterData(filter);
        return body;
    }
}
