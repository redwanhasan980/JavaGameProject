package com.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.game.RahimulBros;
import com.game.Scenes.Hud;
import com.game.Sprites.Enemy;
import com.game.Sprites.Rahimul;
import com.game.Tools.B2WorldCreator;
import com.game.Tools.worldContactListener;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayScreen implements Screen {
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
    public static int jumpCount = 2;
    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();
    private float blockX=screenWidth/16;
    private float blockY=screenHeight/8;
    // For buttons
    private Stage stage;
    private ImageButton leftButton, rightButton, jumpButton;
    private boolean moveLeft = false, moveRight = false, isJumping = false;

    public PlayScreen(RahimulBros game) {
        atlas = new TextureAtlas("Mario_and_Enemies.pack");
        gamecam = new OrthographicCamera();
        gameport = new StretchViewport(RahimulBros.V_WiDTH / RahimulBros.PPM, RahimulBros.V_HEIGHT / RahimulBros.PPM, gamecam);
        hud = new Hud(game.batch);
        maploader = new TmxMapLoader();
        if (RahimulBros.Level == 1)
            map = maploader.load("map.tmx");
        else if (RahimulBros.Level == 2)
            map = maploader.load("level2.tmx");
        else
            map = maploader.load("level3.tmx");
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

        initButtons();
    }
    public TextureAtlas getAtlas()
    {
        return atlas;
    }
    private void initButtons() {
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage); // Set the input processor to handle button clicks

        // Left button
        TextureRegion leftRegion = new TextureRegion(new TextureAtlas("button.atlas").findRegion("left"));
        leftButton = new ImageButton(new TextureRegionDrawable(leftRegion));
        leftButton.setPosition(blockX*3, blockY*1); // Position the button in the lower-left corner
        leftButton.setSize(200, 200);


        // Right button
        TextureRegion rightRegion = new TextureRegion(new TextureAtlas("button.atlas").findRegion("right"));
        rightButton = new ImageButton(new TextureRegionDrawable(rightRegion));
        rightButton.setPosition(blockX*1, blockY*1); // Position the button next to the left button
        rightButton.setSize(200, 200);


        // Jump button
        TextureRegion jumpRegion = new TextureRegion(new TextureAtlas("button.atlas").findRegion("jump"));
        jumpButton = new ImageButton(new TextureRegionDrawable(jumpRegion));
        jumpButton.setPosition(Gdx.graphics.getWidth() - blockX*2, blockY); // Position the jump button on the right
        jumpButton.setSize(200, 200);


        // Add buttons to the stage
        stage.addActor(leftButton);
        stage.addActor(rightButton);
        stage.addActor(jumpButton);
    }

    public void handleInput(float dt) {
        if(player.currentState != Rahimul.State.DEAD) {
            // Get the screen width and height for touch calculations
            float screenWidth = Gdx.graphics.getWidth();
            float screenHeight = Gdx.graphics.getHeight();

            // Check for touch input on the left side of the screen (move left)
            for (int i = 0; i < 5; i++) {
                if (Gdx.input.isTouched(i)) {
                    float touchX = Gdx.input.getX(i);  // Get the X position of the touch
                    float touchY = Gdx.input.getY(i);  // Get the Y position of the touch

                    // Left side (move left)
                    if (touchX < blockX * 2 && touchX > blockX && touchY > (Gdx.graphics.getHeight() - blockY * 2) && touchY < (Gdx.graphics.getHeight() - blockY)) {
                        if (player.b2body.getLinearVelocity().x >= -2) {
                            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
                        }
                    }
                    // Right side (move right)
                    if (touchX > blockX * 3 && touchX < blockX * 4 && touchY > (Gdx.graphics.getHeight() - blockY * 2) && touchY < (Gdx.graphics.getHeight() - blockY)) {
                        if (player.b2body.getLinearVelocity().x <= 2) {
                            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
                        }
                    }
                    // Middle of the screen (jump)
                    if (touchX > (Gdx.graphics.getWidth() - blockX * 2) && touchX < (Gdx.graphics.getWidth() - blockX) && touchY > (Gdx.graphics.getHeight() - blockY * 2) && touchY < (Gdx.graphics.getHeight() - blockY)) {
                        if (player.b2body.getLinearVelocity().y < 1 && jumpCount > 0) {
                            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
                            jumpCount--;
                        }
                    }
                }

                // Reset jump count if player is on the ground
                if (player.b2body.getLinearVelocity().y == 0) {
                    jumpCount = 2;
                }
            }
        }
    }


    public void update(float dt) {
        handleInput(dt);

        player.update(dt);
        for (Enemy enemy : creator.getEnemies()) {
            enemy.update(dt);
            if (enemy.getX() < player.getX() + 224 / RahimulBros.PPM)
                enemy.b2body.setActive(true);
        }

        hud.update(dt);
        world.step(1 / 60f, 6, 2);
        if (player.currentState != Rahimul.State.DEAD) {
            gamecam.position.x = player.b2body.getPosition().x;
        }

        gamecam.update();
        rander.setView(gamecam);
    }

    @Override
    public void show() {

    }
    public void handleLevel()
    {
        RahimulBros.Level++;
        if(RahimulBros.Level==4)
        {
            game.setScreen(new lastScreen(game));
        }
        else
        {
            RahimulBros.changeLevel=false;
            game.setScreen(new PlayScreen((RahimulBros) game));
        }


    }


    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        rander.render();

        // Render the stage for buttons
        stage.act();
        stage.draw();

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        for (Enemy enemy : creator.getEnemies())
            enemy.draw(game.batch);

        game.batch.end();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        if (gameOver()) {
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
        if (RahimulBros.changeLevel) {
            handleLevel();
            dispose();
        }
    }

    // Other methods like resize, pause, resume, etc., remain unchanged
    // ...

    public void resize(int width, int height) {
        gameport.update(width,height);
    }
    public TiledMap getMap()
    {
        return this.map;
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
    {    if(Hud.worldTimer<0)
        return true;
    else if(player.currentState == Rahimul.State.DEAD && player.getStateTimer()>3){
        return true;
    }
    else if(player.b2body.getPosition().y<-2)
        return true;
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

