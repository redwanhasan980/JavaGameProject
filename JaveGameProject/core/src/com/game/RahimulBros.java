package com.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.Screen.PlayScreen;

public class RahimulBros extends Game {
public SpriteBatch batch;
public static final int V_WiDTH = 450;
public static final int V_HEIGHT = 208;
public static final float PPM =100;
	public static final short DEFAULT_BIT=1;

public static final short MARIO_BIT=2;
	public static final short BRICK_BIT=4;
	public static final short COINT_BIT=8;
	public static final short DESTROYED_BIT=16;
	public static final short OBJECT_BIT=32;
	public static final short ENEMY_BIT=64;
	public static final short ENEMY_HEAD_BIT=128;
	public static final short ITEM_BIT=256;
	public  static AssetManager manager;


Texture img;

	public void create() {
		batch = new SpriteBatch();

		manager = new AssetManager();
		manager.load("audio/music/mario_music.ogg",Music.class);
		manager.load("audio/sounds/coin.wav", Sound.class);
		manager.load("audio/sounds/bump.wav", Sound.class);
		manager.load("audio/sounds/breakblock.wav",Sound.class);
		manager.load("audio/sounds/powerup_spawn.wav",Sound.class);
		manager.load("audio/sounds/powerup.wav",Sound.class);
		manager.load("audio/sounds/powerdown.wav",Sound.class);
		manager.load("audio/sounds/stomp.wav",Sound.class);
		manager.finishLoading();
		setScreen(new PlayScreen(this));

	}


	public void render() {

		super.render();
	}
}