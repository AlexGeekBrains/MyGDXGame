package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import lombok.Getter;

public class Label {
    private BitmapFont font;
    @Getter
    private int size;

    public Label(int size, Color color) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        this.size = size;
        parameter.size = size;
        parameter.color = color;
        parameter.characters = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm.,![]1234567890%";
        font = generator.generateFont(parameter);
    }

    public void draw(SpriteBatch batch, String txt, float x, float y) {
        font.draw(batch, txt, x, y);
    }

    public void dispose() {
        font.dispose();
    }
}
