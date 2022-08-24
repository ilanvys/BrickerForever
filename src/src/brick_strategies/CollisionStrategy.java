package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

public interface CollisionStrategy {
    /**
     * General type for brick strategies,
     * part of decorator pattern implementation.
     * All brick strategies implement this interface.
     *
     * @param thisObj current gameObject
     * @param otherObj other object of collision
     * @param counter global brick counter.
     */
    void onCollision(GameObject thisObj, GameObject otherObj, Counter counter);


    /**
     * All collision strategy objects should hold a reference to the global game object
     * collection and be able to return it.
     * @return global game object collection whose reference is held in object.
     */
    GameObjectCollection getGameObjectCollection();
}
