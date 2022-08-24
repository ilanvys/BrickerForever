package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

public class DoubleStrategy implements CollisionStrategy{
    private final CollisionStrategy toBeDecorated1;
    private final CollisionStrategy toBeDecorated2;

    /**
     * Concrete class implementing CollisionStrategy interface.
     * holds two CollisionStrategy objects.
     * @param toBeDecorated1 first held object of CollisionStrategy
     * @param toBeDecorated2 second held object of CollisionStrategy
     */
    public DoubleStrategy(CollisionStrategy toBeDecorated1, CollisionStrategy toBeDecorated2){
        this.toBeDecorated1 = toBeDecorated1;
        this.toBeDecorated2 = toBeDecorated2;
    }

    /**
     * calls the collision handle on both of the decorators,
     * that way it actually implements two collision strategies
     * @param thisObj current gameObject.
     * @param otherObj other object of collision.
     * @param counter global brick counter.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        toBeDecorated1.onCollision(thisObj, otherObj, counter);
        toBeDecorated2.onCollision(thisObj, otherObj, counter);
        counter.increment();
    }

    /**
     * @return all the game objects that are rendered
     */
    @Override
    public GameObjectCollection getGameObjectCollection() {
        return toBeDecorated1.getGameObjectCollection();
    }
}
