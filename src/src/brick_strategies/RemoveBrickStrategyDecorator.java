package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

public abstract class RemoveBrickStrategyDecorator implements CollisionStrategy {
    CollisionStrategy toBeDecorated;

    /**
     * Abstract decorator to add functionality to the remove brick strategy,
     * following the decorator pattern. All strategy decorators should
     * inherit from this class.
     * @param toBeDecorated held object of CollisionStrategy.
     */
    public RemoveBrickStrategyDecorator(CollisionStrategy toBeDecorated) {
        this.toBeDecorated = toBeDecorated;
    }

    /**
     * Removes an object on collision.
     * @param thisObj current gameObject.
     * @param otherObj other object of collision.
     * @param counter global brick counter.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        toBeDecorated.getGameObjectCollection().removeGameObject(thisObj, Layer.STATIC_OBJECTS);
        counter.decrement();
    }

    /**
     * @return all the game objects that are rendered
     */
    @Override
    public GameObjectCollection getGameObjectCollection() {
        return toBeDecorated.getGameObjectCollection();
    }
}
