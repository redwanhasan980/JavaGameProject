package com.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.game.RahimulBros;
import com.game.Sprites.Brick;
import com.game.Sprites.Coin;
import com.game.Sprites.Ground;
import com.game.Sprites.Pipe;

public class B2WorldCreator {
    public B2WorldCreator(World world, TiledMap map)
    {

        PolygonShape shape = new PolygonShape();

        Body body;
        //Ground
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
         new Ground(world,map,rect);

        }
        //Pipes
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Pipe(world,map,rect);

        }
        //Brick
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
           new Brick(world,map,rect);

        }
        //coin
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Coin(world,map,rect);

        }
    }
}
