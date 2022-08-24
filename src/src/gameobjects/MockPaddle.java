package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class MockPaddle extends Paddle {
    int numOfHitsLeft = 3;
    private final GameObjectCollection gameObjects;

    /**
     * Construct a new MockPaddle instance.
     *
     * @param topLeftCorner       Position of the object, in window coordinates (pixels).
     *                            Note that (0,0) is the top-left corner of the window.
     * @param dimensions          Width and height in window coordinates.
     * @param renderable          The renderable representing the object.
     * @param inputListener       Listener for user input.
     * @param windowDimensions    Current dimensions of the window of the game
     * @param minDistanceFromEdge border for paddle movement
     */
    public MockPaddle(
            Vector2 topLeftCorner,
            Vector2 dimensions,
            Renderable renderable,
            UserInputListener inputListener,
            Vector2 windowDimensions,
            int minDistanceFromEdge,
            GameObjectCollection gameObjects) {
        super(topLeftCorner,
                dimensions,
                renderable,
                inputListener,
                windowDimensions,
                minDistanceFromEdge);
        this.gameObjects = gameObjects;
    }
    /**
     * Counts the hit and decreases the num of collisions left
     * until the mock paddle is removed from the game.
     *
     * @param other The game object the ball collided into.
     * @param collision  an instance of the collision
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        numOfHitsLeft -= 1;
        if(numOfHitsLeft <= 0) {
            gameObjects.removeGameObject(this);
        }
    }

    /**
     * reset the num of hits left for the paddle to be removed.
     */
    public void resetNumOfHitsLeft(){
        numOfHitsLeft = 3;
    }
}

