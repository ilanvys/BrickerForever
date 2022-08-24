package src.gameobjects;

import danogl.GameObject;
import src.brick_strategies.ChangeCameraStrategy;

public class BallCollisionCountdownAgent extends GameObject {
    private final Ball ball;
    private final ChangeCameraStrategy owner;
    private final int countDownValue;
    private final int initialNumOfCollisions;

    /**
     * An object of this class is instantiated on collision of ball
     * with a brick with a change camera strategy. It checks ball's
     * collision counter every frame, and once it finds the ball
     * has collided countDownValue times since instantiation,
     * it calls the strategy to reset the camera to normal.
     *
     * @param ball Ball object whose collisions are to be counted.
     * @param owner Object asking for countdown notification.
     * @param countDownValue Number of ball collisions. Notify caller
     *                      object that the ball collided countDownValue
     *                      times since instantiation.
     */
    public BallCollisionCountdownAgent(Ball ball,
                                       ChangeCameraStrategy owner,
                                       int countDownValue) {
        super(ball.getTopLeftCorner(),
                ball.getDimensions(),
                null);
        this.owner = owner;
        this.countDownValue = countDownValue;
        this.ball = ball;
        this.initialNumOfCollisions = this.ball.getCollisionCount();
    }

    /**
     * checks if we need to notify the owner, by counting the collisions
     * of the ball sinc the instance was created.
     * @param deltaTime amount of time between rendering the game.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(ball.getCollisionCount() - initialNumOfCollisions >= countDownValue) {
            owner.turnOffCameraChange();
        }
    }
}