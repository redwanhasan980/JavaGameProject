package com.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.game.Screen.PlayScreen;

public class Ground extends InteractiveTileObject{
    public Ground(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
    }

    @Override
    public void onHeadhit() {

    }


}
