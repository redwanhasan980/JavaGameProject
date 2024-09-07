package com.game.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.RahimulBros;
import com.game.Scenes.Hud;
import com.game.Sprites.Enemy;
import com.game.Sprites.Goomba;
import com.game.Sprites.Rahimul;
import com.game.Tools.B2WorldCreator;
import com.game.Tools.worldContactListener;


import java.util.PriorityQueue;


public class PlayScreen implements Screen
{
    private RahimulBros game;
    private TextureAtlas atlas;


    private Hud hud;
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer rander;

    private OrthographicCamera gamecam;
    private Viewport gameport;

    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;
    private Rahimul player;
    public static Music music;


    public void handleInput(float dt){
        if(player.currentState!= Rahimul.State.DEAD) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
                player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
            if (Gdx.input.isKeyPressed(Input.Keys.D) && player.b2body.getLinearVelocity().x <= 2)
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
            if (Gdx.input.isKeyPressed(Input.Keys.A) && player.b2body.getLinearVelocity().x >= -2)
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        }
    }
    public void update(float dt)
    {
         handleInput(dt);

         player.update(dt);
         for(Enemy enemy : creator.getEnemies())
         {
             enemy.update(dt);
             if(enemy.getX()<player.getX()+224/RahimulBros.PPM)
                 enemy.b2body.setActive(true);
         }

         hud.update(dt);
         world.step(1/60f,6,2);
         if(player.currentState!= Rahimul.State.DEAD) {
             gamecam.position.x=player.b2body.getPosition().x;
         }

         gamecam.update();
         rander.setView(gamecam);
    }
    public PlayScreen(RahimulBros game) {
        atlas = new TextureAtlas("Mario_and_Enemies.pack");
        gamecam = new OrthographicCamera();
        gameport = new StretchViewport(RahimulBros.V_WiDTH / RahimulBros.PPM, RahimulBros.V_HEIGHT / RahimulBros.PPM, gamecam);
        hud = new Hud(game.batch);
        maploader = new TmxMapLoader();
        map = maploader.load("map.tmx");
        rander = new OrthogonalTiledMapRenderer(map, 1 / RahimulBros.PPM);
        gamecam.position.set(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2, 0);
        this.game = game;

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();
        creator = new B2WorldCreator(this);
        player = new Rahimul(this);

        world.setContactListener(new worldContactListener());
        music = RahimulBros.manager.get("audio/music/mario_music.ogg", Music.class);
        music.setLooping(true);
        music.setVolume(0.35f);
        music.play();
    }
public TextureAtlas getAtlas()
{
    return atlas;
}


    @Override
    public void show() {

    }

    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        rander.render();
        b2dr.render(world,gamecam.combined);
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        for(Enemy enemy : creator.getEnemies())
            enemy.draw(game.batch);

        game.batch.end();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        if(gameOver())
        {
            game.setScreen(new GameOverScreen(game));
            dispose();
        }

    }


    public void resize(int width, int height) {
        gameport.update(width,height);
    }
public TiledMap getMap()
{
    return map;
}
public World getWorld()
{
     return world;
}
    public void pause() {

    }


    public void resume() {

    }


    public void hide() {

    }
    public boolean gameOver()
    {
         if(player.currentState == Rahimul.State.DEAD && player.getStateTimer()>3){
             return true;
         }
         else
             return false;
    }

    public void dispose() {
        map.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
