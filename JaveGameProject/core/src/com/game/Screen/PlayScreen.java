package com.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.BananaShooter;
import com.game.Scenes.Hud;

public class PlayScreen implements Screen
{
    private BananaShooter game;

    private Hud hud;
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer rander;

    private OrthographicCamera gamecam;
    private Viewport gameport;
    public void handleInput(float dt){
        if(Gdx.input.isTouched())
            gamecam.position.x+=100*dt;
    }
    public void update(float dt)
    {
         handleInput(dt);
         gamecam.update();
         rander.setView(gamecam);
    }
    public PlayScreen(BananaShooter game)

{   gamecam = new OrthographicCamera();
    gameport= new StretchViewport(BananaShooter.V_WiDTH,BananaShooter.V_HEIGHT,gamecam);
    hud = new Hud(game.batch);
    maploader = new TmxMapLoader();
    map = maploader.load("map.tmx");
    rander = new OrthogonalTiledMapRenderer(map);
    gamecam.position.set(gameport.getWorldWidth()/2 , gameport.getWorldHeight()/2, 0);
     this.game = game;



}
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        rander.render();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        gameport.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
