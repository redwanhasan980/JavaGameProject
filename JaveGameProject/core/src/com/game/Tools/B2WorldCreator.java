package com.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.game.RahimulBros;
import com.game.Screen.PlayScreen;
import com.game.Sprites.Brick;
import com.game.Sprites.Coin;
import com.game.Sprites.Ground;
import com.game.Sprites.Pipe;

public class B2WorldCreator {
    public B2WorldCreator(PlayScreen screen)
    {
          World world = screen.getWorld();
          TiledMap map = screen.getMap();
        PolygonShape shape = new PolygonShape();
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        Body body;
        //Ground
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / RahimulBros.PPM, (rect.getY() + rect.getHeight() / 2) / RahimulBros.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / RahimulBros.PPM, rect.getHeight() / 2 / RahimulBros.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);

        }
        //Pipes
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / RahimulBros.PPM, (rect.getY() + rect.getHeight() / 2) / RahimulBros.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / RahimulBros.PPM, rect.getHeight() / 2 / RahimulBros.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = RahimulBros.OBJECT_BIT;
            body.createFixture(fdef);
        }
        //Brick
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
           new Brick(screen,rect);

        }
        //coin
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Coin(screen,rect);

        }
    }
}
