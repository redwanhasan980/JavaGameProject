package com.game.Tools;

import com.badlogic.gdx.Gdx;
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
                    ((Enemy)fixA.getUserData()).hitOnHead((Rahimul)fixB.getUserData());
                else
                    ((Enemy)fixB.getUserData()).hitOnHead((Rahimul)fixA.getUserData());
                break;
            case RahimulBros.ENEMY_BIT | RahimulBros.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == RahimulBros.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true,false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true,false);
                break;
            case RahimulBros.ENEMY_BIT | RahimulBros.BRICK_BIT:
                if(fixA.getFilterData().categoryBits == RahimulBros.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true,false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true,false);
                break;
            case RahimulBros.ENEMY_BIT | RahimulBros.ENEMY_BIT:
                ((Enemy)fixA.getUserData()).reverseVelocity(true,false);
                ((Enemy)fixB.getUserData()).reverseVelocity(true,false);
                break;

            case RahimulBros.ENEMY_BIT | RahimulBros.MARIO_BIT:
                if(fixA.getFilterData().categoryBits == RahimulBros.MARIO_BIT)
                    ((Rahimul)fixA.getUserData()).hit((Enemy)fixB.getUserData());
                else
                    ((Rahimul)fixB.getUserData()).hit((Enemy)fixA.getUserData());
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
