package src.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Paddle extends GameObject {
    private static final float MOVEMENT_SPEED = 300;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;
    private final int minDistanceFromEdge;

    /**
     * Construct a new Paddle instance.
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object.
     * @param inputListener Listener for user input.
     * @param windowDimensions Current dimensions of the window of the game
     * @param minDistanceFromEdge border for paddle movement
     */
    public Paddle(Vector2 topLeftCorner,
                  Vector2 dimensions,
                  Renderable renderable,
                  UserInputListener inputListener,
                  Vector2 windowDimensions,
                  int minDistanceFromEdge) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.minDistanceFromEdge = minDistanceFromEdge;
    }

    /**
     * Updates the movement of the Paddle, from the user input.
     * @param deltaTime amount of time between rendering the game.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movementDir = movementDir.add(Vector2.LEFT);
        }
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movementDir = movementDir.add(Vector2.RIGHT);
        }
        setVelocity(movementDir.mult(MOVEMENT_SPEED));

        if(getTopLeftCorner().x() <= minDistanceFromEdge) {
            transform().setTopLeftCornerX(1);
        }
        if(getTopLeftCorner().x() >= this.windowDimensions.x()-minDistanceFromEdge-getDimensions().x()) {
            transform().setTopLeftCornerX(this.windowDimensions.x()-minDistanceFromEdge-getDimensions().x());
        }
    }
}
