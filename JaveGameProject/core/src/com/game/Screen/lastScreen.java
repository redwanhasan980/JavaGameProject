package com.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.RahimulBros;
import com.game.Scenes.Hud;

public class lastScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private Game game;
    private Texture texture;
    private SpriteBatch batch;
    private OrthographicCamera gamecam;
    private Viewport gameport;
    public lastScreen(Game game)
    {
        String s1=Hud.score.toString();
        batch = new SpriteBatch();
        gamecam = new OrthographicCamera();
        gameport = new StretchViewport(RahimulBros.V_WiDTH / RahimulBros.PPM, RahimulBros.V_HEIGHT / RahimulBros.PPM, gamecam);

        texture = new Texture(Gdx.files.internal("last.jpg"));


         this.game=game;
         viewport = new FitViewport(RahimulBros.V_WiDTH,RahimulBros.V_HEIGHT,new OrthographicCamera());
         stage = new Stage(viewport,((RahimulBros) game).batch);

        Label.LabelStyle font1 = new Label.LabelStyle(new BitmapFont(), Color.RED);
        Label.LabelStyle font2 = new Label.LabelStyle(new BitmapFont(), Color.WHITE);


        Table table=new Table();
        table.center();
        table.setFillParent(true);

        Label play = new Label("Apni korsen ki!! Game to shes!",font2);
        Label Redo = new Label("REDO GAMING",font1);
        Label st = new Label("Congratulations!! Apni paisen "+s1+" ta kola!!",font2);


        Redo.setFontScale(1.7f);


        table.add(play).expandX().padTop(100f).padLeft(40);
        table.row();
        table.add(st).padTop(5f);
        table.row();
        table.add(Redo).expandX().padTop(30).padLeft(250);
        stage.addActor(table);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //batch.setProjectionMatrix(gamecam.combined);
        batch.begin();
        batch.draw(texture, 40, 10);
        batch.end();
     stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

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
  stage.dispose();
    }
}