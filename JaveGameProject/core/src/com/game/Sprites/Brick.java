package com.game.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.game.RahimulBros;
import com.game.Scenes.Hud;
import com.game.Screen.PlayScreen;

public class Brick extends InteractiveTileObject{
    public Brick(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(RahimulBros.BRICK_BIT);
    }

    @Override
    public void onHeadhit() {
      setCategoryFilter(RahimulBros.DESTROYED_BIT);
      getCell().setTile(null);
        Hud.addScore(100);
        RahimulBros.manager.get("audio/sounds/breakblock.wav", Sound.class).play();
    }
}
