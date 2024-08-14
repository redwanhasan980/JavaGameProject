package com.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.Screen.PlayScreen;

public class RahimulBros extends Game {
public SpriteBatch batch;
public static final int V_WiDTH = 450;
public static final int V_HEIGHT = 208;
public static final float PPM =100;
Texture img;

	public void create() {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		setScreen(new PlayScreen(this));
	}


	public void render() {

		super.render();
	}
}