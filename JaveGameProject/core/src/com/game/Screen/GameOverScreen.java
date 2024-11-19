package com.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.RahimulBros;

public class GameOverScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private Game game;
    public GameOverScreen(Game game)
    {
         this.game=game;
         viewport = new FitViewport(RahimulBros.V_WiDTH,RahimulBros.V_HEIGHT,new OrthographicCamera());
         stage = new Stage(viewport,((RahimulBros) game).batch);
        Label.LabelStyle font1 = new Label.LabelStyle(new BitmapFont(), Color.RED);
        Label.LabelStyle font2 = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        Table table=new Table();
        table.center();
        table.setFillParent(true);
        Label gameOver = new Label("GAME OVER",font1);
        Label playAgain = new Label("Tap anywhere to Play Again",font2);
        table.add(gameOver).expandX();
        table.row();
        table.add(playAgain).expandX().padTop(15f);
        stage.addActor(table);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        if(Gdx.input.isTouched())
        {   RahimulBros.Level=1;
            game.setScreen(new PlayScreen((RahimulBros) game));
            dispose();
        }

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
