package src.gameobjects;

import src.brick_strategies.CollisionStrategy;
import src.brick_strategies.RemoveBrickStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class Brick extends GameObject {
    private final CollisionStrategy collisionStrategy;
    private Counter counter;

    /**
     * Construct a new Brick instance.
     *  @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions Width and height in window coordinates.
     * @param renderable The renderable representing the object.
     * @param collisionStrategy The strategy to apply upon collision.
     * @param counter number of bricks in the game.
     */
    public Brick(Vector2 topLeftCorner,
                 Vector2 dimensions,
                 Renderable renderable,
                 CollisionStrategy collisionStrategy,
                 Counter counter) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
        this.counter = counter;
    }

    /**
     * Apply collision strategy upon collision.
     *
     * @param other The game object the ball collided into.
     * @param collision  an instance of the collision
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionStrategy.onCollision(this, other, counter);
    }
}
