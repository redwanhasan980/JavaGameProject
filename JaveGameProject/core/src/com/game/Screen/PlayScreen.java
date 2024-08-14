package com.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.RahimulBros;
import com.game.Scenes.Hud;
import com.game.Sprites.Rahimul;
import com.game.Tools.B2WorldCreator;

public class PlayScreen implements Screen
{
    private RahimulBros game;

    private Hud hud;
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer rander;

    private OrthographicCamera gamecam;
    private Viewport gameport;

    private World world;
    private Box2DDebugRenderer b2dr;
    private Rahimul player;

    public void handleInput(float dt){
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            player.b2body.applyLinearImpulse(new Vector2(0,4f),player.b2body.getWorldCenter(),true);
           if(Gdx.input.isKeyPressed(Input.Keys.D)&& player.b2body.getLinearVelocity().x<=2)
               player.b2body.applyLinearImpulse(new Vector2(0.1f,0),player.b2body.getWorldCenter(),true);
        if(Gdx.input.isKeyPressed(Input.Keys.A)&& player.b2body.getLinearVelocity().x>=-2)
            player.b2body.applyLinearImpulse(new Vector2(-0.1f,0),player.b2body.getWorldCenter(),true);

    }
    public void update(float dt)
    {
         handleInput(dt);
         world.step(1/60f,6,2);
         gamecam.position.x=player.b2body.getPosition().x;
         gamecam.update();
         rander.setView(gamecam);
    }
    public PlayScreen(RahimulBros game)

{   gamecam = new OrthographicCamera();
    gameport= new StretchViewport(RahimulBros.V_WiDTH/ RahimulBros.PPM, RahimulBros.V_HEIGHT/RahimulBros.PPM,gamecam);
    hud = new Hud(game.batch);
    maploader = new TmxMapLoader();
    map = maploader.load("map.tmx");
    rander = new OrthogonalTiledMapRenderer(map,1/RahimulBros.PPM);
    gamecam.position.set(gameport.getWorldWidth()/2 , gameport.getWorldHeight()/2, 0);
     this.game = game;

world=new World(new Vector2(0,-10),true);
b2dr= new Box2DDebugRenderer();
   new B2WorldCreator(world,map);
    player =new Rahimul(world);



}

    public void show() {

    }


    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        rander.render();
        b2dr.render(world,gamecam.combined);
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

    }


    public void resize(int width, int height) {
        gameport.update(width,height);
    }


    public void pause() {

    }


    public void resume() {

    }


    public void hide() {

    }


    public void dispose() {
        map.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
