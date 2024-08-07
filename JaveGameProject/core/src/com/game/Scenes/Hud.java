package com.game.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.BananaShooter;

public class Hud {
    public Stage stage;
    private Viewport viewport;
    private Integer score;
    private Integer worldTimer;
    private float timeCount;

    Label countdownLabel;
    Label scoreLabel;
    Label timeLabel;
    Label worldLabel;
    Label bananaLabel;
    Label labelLabel;
    public Hud(SpriteBatch sb)
    {
        worldTimer = 300;
        timeCount =0;
        score=0;
        viewport = new FitViewport(BananaShooter.V_WiDTH,BananaShooter.V_HEIGHT,new OrthographicCamera());
        stage =new Stage(viewport,sb);
        Table table = new Table();
        table.top();
        table.setFillParent(true);
        countdownLabel = new Label(String.format("%03d",worldTimer),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel= new Label(String.format("%06d",score),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel= new Label("TIME",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel= new Label("World",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        bananaLabel= new Label("Banana",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        labelLabel= new Label("1-1",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(bananaLabel).expandX().pad(10);
        table.add(worldLabel).expandX().pad(10);
        table.add(timeLabel).expandX().pad(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(labelLabel).expandX();
        table.add(countdownLabel).expandX();
        stage.addActor(table);
    }



}
