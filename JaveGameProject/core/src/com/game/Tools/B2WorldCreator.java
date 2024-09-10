package com.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.game.RahimulBros;
import com.game.Screen.PlayScreen;
import com.game.Sprites.*;



public class B2WorldCreator {


    private Array<Goomba> goombas;
    private Array<Turtle> turtles;
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
        //goombas
        goombas=new Array<Goomba>();
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            goombas.add(new Goomba(screen,rect.getX()/RahimulBros.PPM, rect.getY()/RahimulBros.PPM));

        }
        turtles=new Array<Turtle>();
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            turtles.add(new Turtle(screen,rect.getX()/RahimulBros.PPM, rect.getY()/RahimulBros.PPM));

        }
        for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new levelDest(screen,rect);
        }

    }
    public Array<Goomba> getGoombas() {
        return goombas;
    }
    public Array<Enemy> getEnemies(){
        Array<Enemy>enemies=new Array<Enemy>();
        enemies.addAll(goombas);
        enemies.addAll(turtles);
        return enemies;
    }
}
