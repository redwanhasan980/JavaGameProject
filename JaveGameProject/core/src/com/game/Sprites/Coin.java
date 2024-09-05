package com.game.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.game.RahimulBros;
import com.game.Scenes.Hud;
import com.game.Screen.PlayScreen;


public class Coin extends InteractiveTileObject{
    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 28;
    public Coin(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        tileSet =map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(RahimulBros.COINT_BIT);

    }

    @Override
    public void onHeadhit() {
        setCategoryFilter(RahimulBros.DESTROYED_BIT);
       getCell().setTile(tileSet.getTile(BLANK_COIN));
        Hud.addScore(100);
        RahimulBros.manager.get("audio/sounds/coin.wav", Sound.class).play();
       
    }
}
