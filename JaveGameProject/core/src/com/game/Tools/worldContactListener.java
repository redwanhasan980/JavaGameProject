package com.game.Tools;

import com.badlogic.gdx.physics.box2d.*;
import com.game.Sprites.InteractiveTileObject;

public class worldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA=contact.getFixtureA();
        Fixture fixB= contact.getFixtureB();
        if(fixA.getUserData()=="head"|| fixB.getUserData()=="head")
        {
            Fixture head=fixA.getUserData()=="head"?fixA:fixB;
            Fixture object=head==fixA?fixB:fixA;
            if(object.getUserData()!=null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass()))
            {
                ((InteractiveTileObject) object.getUserData()).onHeadhit();
            }
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
