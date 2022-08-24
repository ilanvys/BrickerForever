package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

public class RemoveBrickStrategy implements CollisionStrategy {
    private final GameObjectCollection gameObjects;

    /**
     * Concrete brick strategy implementing CollisionStrategy interface.
     * Removes holding brick on collision.
     * @param gameObjects all the game objects that are rendered.
     */
    public RemoveBrickStrategy(GameObjectCollection gameObjects) {
        this.gameObjects = gameObjects;
    }

    /**
     * @return all the game objects that are rendered
     */
    @Override
    public GameObjectCollection getGameObjectCollection() {
        return gameObjects;
    }

    /**
     * Removes an object on collision.
     * @param thisObj current gameObject.
     * @param otherObj other object of collision.
     * @param counter global brick counter.
     */
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        gameObjects.removeGameObject(thisObj, Layer.STATIC_OBJECTS);
        counter.decrement();
    }
}
