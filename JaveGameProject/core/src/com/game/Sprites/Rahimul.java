package com.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.game.RahimulBros;
import com.game.Screen.PlayScreen;

public class Rahimul extends Sprite {
    public World world;
    public Body b2body;
    private TextureRegion mariostand;
    public Rahimul(World world ,PlayScreen screen){
        super(screen.getAtlas().findRegion("little_mario"));
        this.world=world;
        defineRahimul();
        mariostand = new TextureRegion(getTexture(),2,22,32,32);
        setBounds(0,0,32/RahimulBros.PPM,32/RahimulBros.PPM);
        setRegion(mariostand);
    }
    public void update(float dt)
    {
        setPosition(b2body.getPosition().x-getWidth()/2,b2body.getPosition().y-getHeight()/2);
    }
    public void defineRahimul()
    {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32/ RahimulBros.PPM,32/RahimulBros.PPM);
        bdef.type=BodyDef.BodyType.DynamicBody;
        b2body=world.createBody(bdef);
        FixtureDef fdef =new FixtureDef();
        CircleShape shape =new CircleShape();
        shape.setRadius(14/RahimulBros.PPM);
        fdef.shape=shape;
        b2body.createFixture(fdef);

    }

}
