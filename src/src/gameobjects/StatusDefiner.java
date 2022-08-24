package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public abstract class StatusDefiner extends GameObject {
    private final Sound collisionSound;
    private final GameObjectCollection gameObjects;

    /**
     * Construct a new StatusDefiner instance.
     *  @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                       Note that (0,0) is the top-left corner of the window.
     * @param dimensions     Width and height in window coordinates.
     * @param renderable     The renderable representing the object. Can be null, in which case
     * @param collisionSound The sound object to be played upon collision.
     * @param gameObjects    all the game objects that are rendered.
     */
    public StatusDefiner(
            Vector2 topLeftCorner,
            Vector2 dimensions,
            Renderable renderable,
            Sound collisionSound,
            GameObjectCollection gameObjects) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.gameObjects = gameObjects;
    }

    /**
     * @param other object collided with
     * @return true if StatusDefiner should collide with
     * Paddle, otherwise false.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return (other instanceof Paddle);
    }

    /**
     * removes the game object upon collision,
     * not before playing a really cool sound.
     *
     * @param other The game object the ball collided into.
     * @param collision  an instance of the collision
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionSound.play();
        gameObjects.removeGameObject(this);
    }
}
