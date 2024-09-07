package com.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.game.RahimulBros;
import com.game.Screen.PlayScreen;

public class Turtle extends Enemy{
    public enum State {WALKING,SHELL};
    public State currentState;
    public State previousState;
    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    float angle;
    private TextureRegion shell;
    public Turtle(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames=new Array<TextureRegion>();
        frames.add(new TextureRegion(screen.getAtlas().findRegion("turtle"),338,34,30,48));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("turtle"),370,37,30,48));
        shell = new TextureRegion(screen.getAtlas().findRegion("turtle"),468,37,30,48);
        walkAnimation = new Animation(0.2f,frames);
        currentState=previousState=State.WALKING;
        setBounds(getX(), getY(), 16 / RahimulBros.PPM, 24 / RahimulBros.PPM);

    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(8 / RahimulBros.PPM);
        fdef.filter.categoryBits = RahimulBros.ENEMY_BIT;
        fdef.filter.maskBits = RahimulBros.DEFAULT_BIT |
                RahimulBros.COINT_BIT |
                RahimulBros.BRICK_BIT |
                RahimulBros.ENEMY_BIT |
                RahimulBros.OBJECT_BIT |
                RahimulBros.MARIO_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        //Create the Head here:
        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-4, 11).scl(1 / RahimulBros.PPM);
        vertice[1] = new Vector2(4, 11).scl(1 / RahimulBros.PPM);
        vertice[2] = new Vector2(-5, 2).scl(1 / RahimulBros.PPM);
        vertice[3] = new Vector2(5, 2).scl(1 / RahimulBros.PPM);
        head.set(vertice);

        fdef.shape = head;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = RahimulBros.ENEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);
    }

    public TextureRegion getFrame(float dt){
        TextureRegion region;

        switch (currentState){
            case SHELL:
                region = shell;
                break;
            case WALKING:
            default:
                region = (TextureRegion) walkAnimation.getKeyFrame(stateTime, true);
                break;
        }

        if(velocity.x > 0 && !region.isFlipX()){
            region.flip(true, false);
        }
        if(velocity.x < 0 && region.isFlipX()){
            region.flip(true, false);
        }
        stateTime = currentState == previousState ? stateTime + dt : 0;
        //update previous state
        previousState = currentState;
        //return our final adjusted frame
        return region;
    }

    @Override
    public void update(float dt) {
        setRegion(getFrame(dt));
        if(currentState == State.SHELL && stateTime > 5){
            currentState = State.WALKING;
            velocity.x = 1;
            RahimulBros.noLifeReduce=false;
            System.out.println("WAKE UP SHELL");
        }
        else if(currentState == State.SHELL)
        {
            RahimulBros.noLifeReduce=true;
        }

        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - 8 /RahimulBros.PPM);
        b2body.setLinearVelocity(velocity);
    }

    @Override
    public void hitOnHead() {
        if(currentState != State.SHELL) {
        currentState=State.SHELL;
        velocity.x=0;
        }

    }




}