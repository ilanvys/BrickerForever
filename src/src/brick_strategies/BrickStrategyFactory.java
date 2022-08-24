package src.brick_strategies;

import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import src.BrickerGameManager;

import java.util.Random;

public class BrickStrategyFactory {
    private final GameObjectCollection gameObjects;
    private final BrickerGameManager gameManager;
    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final UserInputListener inputListener;
    private final WindowController windowController;
    private final Vector2 windowDimensions;
    /**
     * Factory class for creating Collision strategies
     * @param gameObjectCollection all the game objects that are rendered.
     * @param gameManager the game manager
     * @param imageReader manages image rendering for visible game objects.
     * @param soundReader manages sound rendering for game objects.
     * @param inputListener manages user input for game objects.
     * @param windowController manages settings for the game window.
     * @param windowDimensions wanted dimensions of the game window
     */
    public BrickStrategyFactory(
            GameObjectCollection gameObjectCollection,
            BrickerGameManager gameManager,
            ImageReader imageReader,
            SoundReader soundReader,
            UserInputListener inputListener,
            WindowController windowController,
            Vector2 windowDimensions) {
        this.gameObjects = gameObjectCollection;
        this.gameManager = gameManager;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.windowDimensions = windowDimensions;
    }

    /**
     * method randomly selects between 5 strategies and returns one
     * CollisionStrategy object which is a RemoveBrickStrategy decorated by
     * one of the decorator strategies, or decorated by two randomly selected
     * strategies, or decorated by one of the decorator strategies and a pair
     * of additional two decorator strategies.
     * @return one of the strategies
     */
    public CollisionStrategy getStrategy() {
        Random rand = new Random();
        int randStrategy = rand.nextInt(6);
        System.out.println(randStrategy);
        if(randStrategy != 5) {
            return getStrategyByNum(randStrategy);
        }
        else { // brick has two strategies
            int randStrategy1 = rand.nextInt(6);
            int randStrategy2 = rand.nextInt(5);
            if(randStrategy1 != 5) {
                return new DoubleStrategy(
                        getStrategyByNum(randStrategy1),
                        getStrategyByNum(randStrategy2));
            }
            else { // brick has two strategies, and one of them is a double strategy
                return new DoubleStrategy(new DoubleStrategy(
                        getStrategyByNum(rand.nextInt(5)),
                        getStrategyByNum(rand.nextInt(5))),
                        getStrategyByNum(rand.nextInt(5)));
            }
        }
    }

    /**
     * Recieves a number between 0 to 4 and returns an instance of a
     * CollisionStrategy matching that number.
     *
     * @param num the number of the collision strategy from 1 to 5.
     * @return CollisionStrategy instance
     */
    private CollisionStrategy getStrategyByNum(int num){
        if(num == 0) {
            return new PuckStrategy(
                    new RemoveBrickStrategy(gameObjects),
                    imageReader,
                    soundReader);
        }
        if(num == 1) {
            return new AddPaddleStrategy(
                    new RemoveBrickStrategy(gameObjects),
                    imageReader,
                    inputListener,
                    windowDimensions);
        }
        if(num == 2) {
            return new ChangeCameraStrategy(
                    new RemoveBrickStrategy(gameObjects),
                    windowController,
                    gameManager);
        }
        if(num == 3) {
            return new TimeScaleStrategy(
                    new RemoveBrickStrategy(gameObjects),
                    imageReader,
                    soundReader,
                    windowController
            );
        }
        return new RemoveBrickStrategy(gameObjects);
    }

}
