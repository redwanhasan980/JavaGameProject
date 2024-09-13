package com.game.Sprites;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.game.RahimulBros;
import com.game.Scenes.Hud;
import com.game.Screen.PlayScreen;


public class Rahimul extends Sprite {
    public enum State {FALLING,JUMPING,STANDING,RUNNING,DEAD};
    public State currentState;
    public State previousState;

    public World world;
    public Body b2body;
    private TextureRegion mariostand;
    private Animation marioRun;
    private Animation marioJump;
    private float stateTimer;
    private boolean runningRight;
    private TextureRegion marioDead;
    private boolean marioIsDead;

    public Rahimul(PlayScreen screen){
        super(screen.getAtlas().findRegion("little_mario"));
        this.world=screen.getWorld();
        defineRahimul();
        currentState =State.STANDING;
        
        previousState=State.STANDING;
        stateTimer =0;
        runningRight =true;
        Array<TextureRegion> frames =new Array<TextureRegion>();
        for(int i=1;i<4;i++)
             frames.add(new TextureRegion(getTexture(),i*32,22,32,32));
        marioRun = new Animation(0.1f,frames);
        frames.clear();
        for(int i=4;i<6;i++)
            frames.add(new TextureRegion(getTexture(),i*32,22,32,32));
        marioJump = new Animation(0.1f,frames);

        mariostand = new TextureRegion(getTexture(),2,22,32,32);
        marioDead = new TextureRegion(getTexture(),194,22,32,32);
        setBounds(0,0,32/ RahimulBros.PPM,32/RahimulBros.PPM);
        setRegion(mariostand);
    }
    public void update(float dt)
    {
        setPosition(b2body.getPosition().x-getWidth()/2,b2body.getPosition().y-getHeight()/2);
          setRegion(getFrame(dt));
    }
    public TextureRegion getFrame(float dt)
    {
        currentState=getState();
        TextureRegion region;
        switch ( currentState)
        {   case DEAD:
               region=marioDead;
               break;
            case JUMPING:
                region= (TextureRegion) marioJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = (TextureRegion) marioRun.getKeyFrame(stateTimer,true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = mariostand;
                break;
        }
        if((b2body.getLinearVelocity().x<0||!runningRight)&&!region.isFlipX()) {
            region.flip(true, false);
            runningRight=false;
        }
        else if((b2body.getLinearVelocity().x>0||runningRight)&&region.isFlipX())
        {
            region.flip(true, false);
            runningRight=true;
        }
        stateTimer=currentState==previousState?stateTimer+dt:0;
        previousState=currentState;
        return region;
    }
    public State getState()
    {   if(marioIsDead)
        return State.DEAD;
        else if(b2body.getLinearVelocity().y>0  ||(b2body.getLinearVelocity().y<0 &&previousState==State.JUMPING) )
        return State.JUMPING;
        else if(b2body.getLinearVelocity().y<0)
            return State.FALLING;
       else if(b2body.getLinearVelocity().x!=0)
            return State.RUNNING;
       else
           return State.STANDING;



    }
    public void hit(Enemy enemy)
    { if(enemy instanceof Turtle &&((Turtle)enemy).getCurrentState()== Turtle.State.SHELL)
    {
        ((Turtle)enemy).Kick(this.getX()<=enemy.getX()?Turtle.KickRight:Turtle.KickLeft);
    }
        else if(Hud.getLife()>1)
        {
            Hud.removeLife();
            RahimulBros.manager.get("audio/sounds/powerdown.wav", Sound.class).play();
        }

        else
        {          Hud.removeLife();
            PlayScreen.music.stop();
            RahimulBros.manager.get("audio/sounds/mariodie.wav", Sound.class).play();
            marioIsDead=true;
            Filter filter = new Filter();
            filter.maskBits=RahimulBros.NOTHING_BIT;
            for(Fixture fixture:b2body.getFixtureList())
                 fixture.setFilterData(filter);
            b2body.applyLinearImpulse(new Vector2(0,4f),b2body.getWorldCenter(),true);
        }
    }
    public void defineRahimul()
    {
        BodyDef bdef = new BodyDef();
        bdef.position.set(274/ RahimulBros.PPM,64/RahimulBros.PPM);
        bdef.type=BodyDef.BodyType.DynamicBody;
        b2body=world.createBody(bdef);
        FixtureDef fdef =new FixtureDef();
        CircleShape shape =new CircleShape();
        shape.setRadius(14/RahimulBros.PPM);
        fdef.filter.categoryBits=RahimulBros.MARIO_BIT;
        fdef.filter.maskBits=RahimulBros.DEFAULT_BIT|RahimulBros.BRICK_BIT|RahimulBros.COINT_BIT|
                RahimulBros.OBJECT_BIT|RahimulBros.ENEMY_BIT|RahimulBros.ENEMY_HEAD_BIT|RahimulBros.LEVEL_BIT;

        fdef.shape=shape;
        b2body.createFixture(fdef).setUserData(this);
        EdgeShape head =new EdgeShape();
        head.set(new Vector2(-4/RahimulBros.PPM,14/RahimulBros.PPM),new Vector2(4/RahimulBros.PPM,14/RahimulBros.PPM));
        fdef.shape=head;
        b2body.createFixture(fdef).setUserData("head");
        fdef.isSensor=true;


    }
    public boolean isDead()
    {
        return marioIsDead;
    }
    public float getStateTimer()
    {
        return stateTimer;
    }

}
