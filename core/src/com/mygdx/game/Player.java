package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import java.util.ArrayList;

public class Player {
    private Physics physics;
    private MyInputProcessor myInputProcessor;

    private Body playerBody;

    private MyAtlasAnim standAnim, runAnim, shotAnim, deathAnim, currentDraw;

    private boolean isRightOrientation;
//    private Label label;
    private static final Sound HIT_SOUND = Gdx.audio.newSound(Gdx.files.internal("hit.mp3"));
    private static final Sound TAKE_COIN_SOUND = Gdx.audio.newSound(Gdx.files.internal("money.mp3"));
    private static final Sound NO_BULLET_SOUND = Gdx.audio.newSound(Gdx.files.internal("nobullet.mp3"));
    private static final Sound RECHARGE_SOUND = Gdx.audio.newSound(Gdx.files.internal("recharge.mp3"));
    private static final Sound LOSS_SOUND = Gdx.audio.newSound(Gdx.files.internal("loss.mp3"));


    private int health;
    private boolean isFire;
    private boolean isMisFire;

    private int bulletsInClip;

    private boolean isDamage;
    private static final float X_PHYS_OK = 0.250f;
    private static final float Y_PHYS_OK = 30.2f;

    private int coins;

    private boolean isDeath;
    private Blood blood;

    private ArrayList<Destroyed> bulletsForRender;

    public Player(MyInputProcessor myInputProcessor, Body playerBody, Physics physics) {
        this.physics = physics;
        health = 100;
        standAnim = new MyAtlasAnim("atlas/unnamed.atlas", "stay", 10, Animation.PlayMode.LOOP);
        runAnim = new MyAtlasAnim("atlas/unnamed.atlas", "run", 10, Animation.PlayMode.LOOP);
        shotAnim = new MyAtlasAnim("atlas/unnamed.atlas", "shot", 20, Animation.PlayMode.NORMAL);
        deathAnim = new MyAtlasAnim("atlas/unnamed.atlas", "death", 20, Animation.PlayMode.NORMAL);
        blood = new Blood();
        currentDraw = standAnim;
        this.myInputProcessor = myInputProcessor;
        this.playerBody = playerBody;
        this.physics = physics;
        bulletsForRender = new ArrayList<>();
        coins = 0;
        bulletsInClip = 7;
//        label = new Label(40, Color.BLACK);
    }

    public void render(SpriteBatch batch, float dt) {
        currentDraw.setTime(dt);
        update();
        float correctY = 4f; //Корректировка, что бы картинка персонажа касалась земли (видимо анимация кривая если это убрать то как будто парит в воздухе немного
        float correctX = 25;
        Rectangle rectangle = getPlayerRect();
        ((PolygonShape) playerBody.getFixtureList().get(0).getShape()).setAsBox((rectangle.width - correctX) / 2 / physics.getPPM(), rectangle.height / 2 / physics.getPPM());
        ((PolygonShape) playerBody.getFixtureList().get(playerBody.getFixtureList().size - 1).getShape()).setAsBox(rectangle.width / 15 / physics.getPPM(), rectangle.height / 15 / physics.getPPM(), new Vector2((((rectangle.width - correctX) / 2) - 6f) / physics.getPPM(), -rectangle.height / 2 / physics.getPPM()), 0);
        ((PolygonShape) playerBody.getFixtureList().get(playerBody.getFixtureList().size - 2).getShape()).setAsBox(rectangle.width / 15 / physics.getPPM(), rectangle.height / 15 / physics.getPPM(), new Vector2(((-(rectangle.width - correctX) / 2) + 6f) / physics.getPPM(), -rectangle.height / 2 / physics.getPPM()), 0);
        batch.draw(currentDraw.draw(), rectangle.x, rectangle.y - correctY, rectangle.width, rectangle.height);
        if (isDamage) {
            if (blood.render(batch, dt, rectangle)) isDamage = false;
        }
//        if (bulletsInClip == 0) {
//            label.draw(batch, "Press   R   to reload the weapon", rectangle.x - 250, rectangle.y + label.getSize() + 150);
//        }
    }

    public void decreaseHealth(float damage) {
        health -= damage;
    }

    private void checkMovement() {
        if (!isFire && !isDeath && !isMisFire) {
            changeAnimOrientation(standAnim);
            currentDraw = standAnim;
            if (myInputProcessor.getOutString().contains("A")) {
                if (runAnim.draw().isFlipX()) {
                    runAnim.draw().flip(true, false);
                    isRightOrientation = false;
                }
                currentDraw = runAnim;
                if (playerBody.getLinearVelocity().x > -2.2)
                    playerBody.applyForceToCenter(new Vector2(-X_PHYS_OK, 0), true);
            }
            if (myInputProcessor.getOutString().contains("D")) {
                if (!runAnim.draw().isFlipX()) {
                    runAnim.draw().flip(true, false);
                    isRightOrientation = true;
                }
                currentDraw = runAnim;
                if (playerBody.getLinearVelocity().x < 2.2) {
                    playerBody.applyForceToCenter(new Vector2(X_PHYS_OK, 0), true);
                }
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.W) && physics.getMyContList().isOnGround()) {
                playerBody.applyForceToCenter(new Vector2(0, Y_PHYS_OK), true);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                if (bulletsInClip == 0) {
                    NO_BULLET_SOUND.play();
                    isMisFire = true;
                }
                if (bulletsInClip > 0) {
                    bulletsInClip--;
                    bulletsForRender.add(new Bullet(this).shot(new Vector2(2, 0), physics)); // скорость пули тут
                    HIT_SOUND.play();
                    isFire = true;
                }
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
                reloadedGun();
                RECHARGE_SOUND.play();
            }
        }
    }

    public void update() {
        checkMovement();
        checkFire();
        misfire();
        checkHealth();
        checkDeath();
    }

    public void checkFire() {
        if (isFire) {
            changeAnimOrientation(shotAnim);
            currentDraw = shotAnim;
            if (currentDraw.isAnimationOver()) {
                currentDraw.resetTime();
                isFire = false;
            }
        }
    }

    public void misfire() {
        if (isMisFire) {
            changeAnimOrientation(shotAnim);
            currentDraw = shotAnim;
            if (currentDraw.isAnimationOver()) {
                currentDraw.resetTime();
                isMisFire = false;
            }
        }
    }

    public void reloadedGun() {
        bulletsInClip = 7;
    }

    public void checkDeath() {
        if (isDeath) {
            changeAnimOrientation(deathAnim);
            currentDraw = deathAnim;
            if (currentDraw.isAnimationOver()) LOSS_SOUND.play();
        }
    }

    public void checkHealth() {
        if (health <= 0) isDeath = true;
    }

    public void incrementCoins() {
        coins++;
        TAKE_COIN_SOUND.play();
        System.out.println(coins);
    }

    public Rectangle getPlayerRect() {
        TextureRegion tr = currentDraw.draw();
        float x = playerBody.getPosition().x * physics.getPPM() - tr.getRegionWidth() / 2;
        float y = playerBody.getPosition().y * physics.getPPM() - tr.getRegionHeight() / 2;
        float w = tr.getRegionWidth();
        float h = tr.getRegionHeight();
        return new Rectangle(x, y, w, h);
    }

    private void changeAnimOrientation(MyAtlasAnim anim) {
        if (isRightOrientation && !anim.draw().isFlipX()) {
            anim.draw().flip(true, false);
        }
        if (!isRightOrientation && anim.draw().isFlipX()) {
            anim.draw().flip(true, false);
        }
    }

    public void dispose() {
        standAnim.dispose();
        runAnim.dispose();
        shotAnim.dispose();
        currentDraw.dispose();
//        label.dispose();
        HIT_SOUND.dispose();
        TAKE_COIN_SOUND.dispose();
        NO_BULLET_SOUND.dispose();
        RECHARGE_SOUND.dispose();
        LOSS_SOUND.dispose();
    }

    public Physics getPhysics() {
        return physics;
    }


    public Body getPlayerBody() {
        return playerBody;
    }


    public MyAtlasAnim getDeathAnim() {
        return deathAnim;
    }

    public MyAtlasAnim getCurrentDraw() {
        return currentDraw;
    }

    public boolean isRightOrientation() {
        return isRightOrientation;
    }


    public int getHealth() {
        return health;
    }


    public int getBulletsInClip() {
        return bulletsInClip;
    }


    public int getCoins() {
        return coins;
    }

    public ArrayList<Destroyed> getBulletsForRender() {
        return bulletsForRender;
    }

    public void setPhysics(Physics physics) {
        this.physics = physics;
    }


    public void setDamage(boolean damage) {
        isDamage = damage;
    }

    public void setDeath(boolean death) {
        isDeath = death;
    }


}