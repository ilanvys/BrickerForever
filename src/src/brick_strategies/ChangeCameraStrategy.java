package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.gameobjects.Ball;
import src.gameobjects.BallCollisionCountdownAgent;
import src.gameobjects.Paddle;
import src.gameobjects.Puck;

public class ChangeCameraStrategy extends RemoveBrickStrategyDecorator {
    private final int NUM_BALL_COLLISIONS_TO_TURN_OFF = 4;
    private final CollisionStrategy toBeDecorated;
    private final WindowController windowController;
    private final BrickerGameManager gameManager;
    private final GameObjectCollection gameObjects;
    private BallCollisionCountdownAgent ballCollisionCountdownAgent;

    /**
     * Concrete class extending abstract RemoveBrickStrategyDecorator.
     * Changes camera focus from ground to ball until ball collides
     * NUM_BALL_COLLISIONS_TO_TURN_OFF times.
     * @param toBeDecorated held object of CollisionStrategy
     * @param windowController manages settings for the game window.
     * @param gameManager the game manager
     */
    public ChangeCameraStrategy(
            CollisionStrategy toBeDecorated,
            WindowController windowController,
            BrickerGameManager gameManager) {
        super(toBeDecorated);
        this.toBeDecorated = toBeDecorated;
        this.windowController = windowController;
        this.gameManager = gameManager;
        this.gameObjects = toBeDecorated.getGameObjectCollection();
    }

    /**
     * Change camera position on collision and delegate to held CollisionStrategy.
     * @param thisObj current gameObject
     * @param otherObj other object of collision
     * @param counter global brick counter.
     */
    @Override
    public void onCollision(GameObject thisObj,
                            GameObject otherObj,
                            Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        if(gameManager.getCamera() == null && !(otherObj instanceof Puck)) {
            gameManager.setCamera(
                    new Camera(
                            otherObj,
                            Vector2.ZERO,
                            windowController.getWindowDimensions().mult(1.2f),
                            windowController.getWindowDimensions())
            );

            ballCollisionCountdownAgent = new BallCollisionCountdownAgent(
                    (Ball) otherObj,
                    this,
                    NUM_BALL_COLLISIONS_TO_TURN_OFF);
            gameObjects.addGameObject(ballCollisionCountdownAgent);
        }
    }

    /**
     * Return camera to normal ground position.
     */
    public void turnOffCameraChange() {
        gameManager.setCamera(null);
        gameObjects.removeGameObject(ballCollisionCountdownAgent);
    }
}
