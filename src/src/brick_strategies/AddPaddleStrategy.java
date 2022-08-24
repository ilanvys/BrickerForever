package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.MockPaddle;

public class AddPaddleStrategy extends RemoveBrickStrategyDecorator {
    private final CollisionStrategy toBeDecorated;
    private final ImageReader imageReader;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;
    private final GameObjectCollection gameObjects;


    /**
     * Concrete class extending abstract RemoveBrickStrategyDecorator. Introduces
     * extra paddle to game window which remains until colliding NUM_COLLISIONS_FOR_MOCK_PADDLE_DISAPPEARANCE
     * with other game objects.
     * @param toBeDecorated held object of CollisionStrategy
     * @param imageReader manages image rendering for visible game objects.
     * @param inputListener manages user input for game objects.
     * @param windowDimensions wanted dimensions of the game window
     */
    public AddPaddleStrategy(CollisionStrategy toBeDecorated,
                             ImageReader imageReader,
                             UserInputListener inputListener,
                             Vector2 windowDimensions) {
        super(toBeDecorated);
        this.toBeDecorated = toBeDecorated;
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.gameObjects = toBeDecorated.getGameObjectCollection();
    }

    /**
     * Adds additional paddle to game and delegates to held object.
     * @param thisObj current gameObject
     * @param otherObj other object of collision
     * @param counter global brick counter.
     */
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        initializeExtraPaddle();
    }

    /**
     * Initializes an extra paddle, if one exists already it resets the amount
     * of hits it can take until disappearing.
     */
    private void initializeExtraPaddle() {
        Renderable paddleImage = imageReader.readImage("assets/paddle.png", false);
        GameObject mockPaddle = new MockPaddle(
                Vector2.ZERO,
                new Vector2(150, 20),
                paddleImage,
                inputListener,
                windowDimensions,
                1,
                gameObjects);
        mockPaddle.setCenter(new Vector2(windowDimensions.x()/2,windowDimensions.y()/2));

        MockPaddle existingPaddle = mockPaddleExist();
        if(existingPaddle != null) {
            existingPaddle.resetNumOfHitsLeft();
        }
        else {
            gameObjects.addGameObject(mockPaddle);
        }
    }

    /**
     * @return MockPaddle if one exists in the game.
     */
    private MockPaddle mockPaddleExist() {
        for (GameObject gameObject: gameObjects) {
            if(gameObject instanceof MockPaddle) {
                return (MockPaddle) gameObject;
            }
        }
        return null;
    }
}
