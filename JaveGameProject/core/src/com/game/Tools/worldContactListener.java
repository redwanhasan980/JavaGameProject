package com.game.Tools;

import com.badlogic.gdx.physics.box2d.*;
import com.game.RahimulBros;
import com.game.Sprites.Enemy;
import com.game.Sprites.InteractiveTileObject;
import com.game.Sprites.Rahimul;

public class worldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA=contact.getFixtureA();
        Fixture fixB= contact.getFixtureB();
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        if(fixA.getUserData()=="head"|| fixB.getUserData()=="head")
        {
            Fixture head=fixA.getUserData()=="head"?fixA:fixB;
            Fixture object=head==fixA?fixB:fixA;
            if(object.getUserData()!=null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass()))
            {
                ((InteractiveTileObject) object.getUserData()).onHeadhit();
            }
        }
        switch (cDef)
        {
            case RahimulBros.ENEMY_HEAD_BIT | RahimulBros.MARIO_BIT:
                if(fixA.getFilterData().categoryBits == RahimulBros.ENEMY_HEAD_BIT)
                    ((Enemy)fixA.getUserData()).hitOnHead();
                else
                    ((Enemy)fixB.getUserData()).hitOnHead();
                break;
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
