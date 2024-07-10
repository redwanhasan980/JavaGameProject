package com.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.Screen.PlayScreen;

public class BananaShooter extends Game {
public SpriteBatch batch;
public static final int V_WiDTH = 400;
public static final int V_HEIGHT = 280;
Texture img;
	@Override
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}
}