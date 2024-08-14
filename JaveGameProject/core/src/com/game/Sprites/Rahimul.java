package com.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.game.RahimulBros;

public class Rahimul extends Sprite {
    public World world;
    public Body b2body;
    public Rahimul(World world){
        this.world=world;
        defineRahimul();
    }
    public void defineRahimul()
    {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32/ RahimulBros.PPM,32/RahimulBros.PPM);
        bdef.type=BodyDef.BodyType.DynamicBody;
        b2body=world.createBody(bdef);
        FixtureDef fdef =new FixtureDef();
        CircleShape shape =new CircleShape();
        shape.setRadius(5/RahimulBros.PPM);
        fdef.shape=shape;
        b2body.createFixture(fdef);

    }

}
