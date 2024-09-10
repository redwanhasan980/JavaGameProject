package com.game.Sprites;

import com.badlogic.gdx.math.Rectangle;
import com.game.RahimulBros;
import com.game.Screen.PlayScreen;

public class levelDest extends InteractiveTileObject{
        public levelDest(PlayScreen screen, Rectangle bounds) {
            super(screen, bounds);
            fixture.setUserData(this);
            setCategoryFilter(RahimulBros.LEVEL_BIT);
        }

    @Override
    public void onHeadhit() {

    }
}
