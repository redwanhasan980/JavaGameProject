package com.game.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.RahimulBros;

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;
    private static Integer score;
    private Integer worldTimer;
    private float timeCount;
    private static Integer life;

   private Label countdownLabel;
    private static Label scoreLabel;
    private Label timeLabel;
    private Label worldLabel;
    private Label bananaLabel;
    private Label labelLabel;
    private Label Lifelabel;
    private static Label showLife;
    public Hud(SpriteBatch sb)
    {
        worldTimer = 300;
        timeCount =0;
        score=0;
        life=3;
        viewport = new FitViewport(RahimulBros.V_WiDTH, RahimulBros.V_HEIGHT,new OrthographicCamera());
        stage =new Stage(viewport,sb);
        Table table = new Table();
        table.top();
        table.setFillParent(true);
        countdownLabel = new Label(String.format("%03d",worldTimer),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel= new Label(String.format("%d",score),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        showLife= new Label(String.format("%d",life),new Label.LabelStyle(new BitmapFont(), Color.RED));
        timeLabel= new Label("TIME",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel= new Label("World",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        bananaLabel= new Label("Banana",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Lifelabel= new Label("LIFE",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        labelLabel= new Label("1-1",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(bananaLabel).expandX().pad(10);
        table.add(worldLabel).expandX().pad(10);
        table.add(timeLabel).expandX().pad(10);
        table.add(Lifelabel).expandX().pad(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(labelLabel).expandX();
        table.add(countdownLabel).expandX();
        table.add(showLife).expandX();
        stage.addActor(table);
    }

public void update(float dt)

{
    timeCount+=dt;
    if(timeCount>=1)
    {
        worldTimer--;
        countdownLabel.setText(String.format("%03d",worldTimer));
        timeCount=0;
    }
}
public static void addScore(int value)
{
    score+=value;
    scoreLabel.setText(String.format("%06d",score));

}
    public static void removeLife()
    {
        life--;
        showLife.setText(String.format("%d",life));

    }

    public void dispose() {
        stage.dispose();
    }
    public static Integer getLife(){
        return life;
    }


}
