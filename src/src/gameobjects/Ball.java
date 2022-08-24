package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Ball extends GameObject {
    private final Sound collisionSound;
    private int collisionCount;

    /**
     * Construct a new Ball instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object.
     * @param collisionSound    The sound object to be played upon collision.
     */
    public Ball(Vector2 topLeftCorner,
                Vector2 dimensions,
                Renderable renderable,
                Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        collisionCount = 0;
    }

    /**
     * Flips the velocity of the ball upon collision with other game objects
     *
     * @param other The game object the ball collided into.
     * @param collision  an instance of the collision
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        Vector2 newVal = getVelocity().flipped(collision.getNormal());
        setVelocity(newVal);
        collisionSound.play();
        collisionCount++;
    }

    /**
     * Ball object maintains a counter which keeps count of collisions
     *  from start of game. This getter method allows access to the collision
     * count in case any behavior should need to be based on number of ball collisions.
     *
     * @return number of times ball collided with an object since start of game.
     */
    public int getCollisionCount() {
        return collisionCount;
    }
}
