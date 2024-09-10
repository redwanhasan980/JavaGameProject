package com.game.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.game.RahimulBros;
import com.game.Screen.PlayScreen;

public class Turtle extends Enemy{
    public static final int KickLeft=-2;
    public static final int KickRight=2;
    public enum State {WALKING,SHELL,MOVING_SHELL,DEAD};
    public State currentState;
    public State previousState;
    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private float deadRotation;
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
        deadRotation=0;
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
        vertice[0] = new Vector2(-4.6f, 11).scl(1 / RahimulBros.PPM);
        vertice[1] = new Vector2(4.6f
                , 11).scl(1 / RahimulBros.PPM);
        vertice[2] = new Vector2(-5, 2).scl(1 / RahimulBros.PPM);
        vertice[3] = new Vector2(5, 2).scl(1 / RahimulBros.PPM);
        head.set(vertice);

        fdef.shape = head;
        fdef.restitution = 1.5f;
        fdef.filter.categoryBits = RahimulBros.ENEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);
    }

    public TextureRegion getFrame(float dt){
        TextureRegion region;

        switch (currentState){
            case SHELL:
            case MOVING_SHELL:
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
        if(currentState==State.DEAD)
        {
            deadRotation+=3;
            rotate(deadRotation);
            if(stateTime>5&&!destroyed)
            {   world.destroyBody(b2body);
                destroyed=true;
            }


        }
        else
            b2body.setLinearVelocity(velocity);
        b2body.setLinearVelocity(velocity);
    }

    @Override
    public void hitOnHead(Rahimul rahimul) {

        if(currentState != State.SHELL) {
            RahimulBros.manager.get("audio/sounds/stomp.wav", Sound.class).play();
        currentState=State.SHELL;
        velocity.x=0;
        }
        else
        {
            Kick(rahimul.getX()<=this.getX()?KickRight:KickLeft);
        }

    }

public void Kick(int speed){
    RahimulBros.manager.get("audio/sounds/powerup.wav", Sound.class).play();
        velocity.x=speed;
        currentState=State.MOVING_SHELL;
}

    @Override
    public void onEnemyHit(Enemy enemy) {
        if(enemy instanceof Turtle)
        {
            if(((Turtle)enemy).currentState==State.MOVING_SHELL&&currentState!=State.MOVING_SHELL)
            {
                Killed();
            }
            else if(currentState==State.MOVING_SHELL&&((Turtle)enemy).currentState==State.WALKING)
                return;
            else
                reverseVelocity(true,false);
        }
        else if(currentState!=State.MOVING_SHELL)
        {
            reverseVelocity(true,false);
        }

    }

    public State getCurrentState()
{
    return currentState;
}
public void Killed()
{
    currentState=State.DEAD;
    Filter filter =new Filter();
    filter.maskBits=RahimulBros.NOTHING_BIT;
    for(Fixture fixture:b2body.getFixtureList())
        fixture.setFilterData(filter);
    b2body.applyLinearImpulse(new Vector2(0,5f),b2body.getWorldCenter(),true);

}


}