package src;

import src.brick_strategies.BrickStrategyFactory;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.*;

public class BrickerGameManager extends GameManager {
    private static final int BORDER_WIDTH = 20;
    private static final int PADDLE_HEIGHT = 20;
    private static final int PADDLE_WIDTH = 150;
    private static final int BALL_RADIUS = 35;
    private static final int BALL_SPEED = 200;
    private static final int NUM_OF_BRICKS = 40;
    private static final int NUM_OF_LIVES = 4;
    private static final int MIN_DISTANCE_FROM_SCREEN_EDGE = 1;

    private Vector2 windowDimensions;
    private WindowController windowController;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private UserInputListener inputListener;
    private Ball ball;
    private Counter brickCounter;
    private Counter lifeCounter;

    /**
     * Game manager - this class is responsible for game initialization,
     * holding references for game objects and calling update methods for
     * every update iteration. Entry point for code should be in a main method
     * in this class.
     * @param windowTitle name of the game
     * @param windowDimensions wanted dimensions of the game window
     */
    BrickerGameManager(String windowTitle, Vector2 windowDimensions) {

        super(windowTitle, windowDimensions);
    }

    /**
     * Override the game initialization method.
     * init all the game objects needed for a game.
     * @param imageReader manages image rendering for visible game objects.
     * @param soundReader manages sound rendering for game objects.
     * @param inputListener manages user input for game objects.
     * @param windowController manages settings for the game window.
     */
    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowDimensions = windowController.getWindowDimensions();
        this.imageReader = imageReader;
        this.windowController = windowController;
        this.inputListener = inputListener;
        this.soundReader = soundReader;
        this.brickCounter = new Counter(NUM_OF_BRICKS);
        this.lifeCounter = new Counter(NUM_OF_LIVES);

        initializeBackground();
        initializeWalls();
        initializeBricks();
        initializePaddle();
        initializePaddle();
        initializeGraphicLifeCounter();
        initializeNumericLifeCounter();
        initializeBall();
    }

    /**
     * Override the update method.
     * Checks if the game has ended for each render.
     * Checks if there are objects outside the game's border
     * and removes them.
     * @param deltaTime amount of time between rendering the game.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd();
        removeObjectsOutOfBounds();
    }

    /**
     * Removes out of bounds objects from the game.
     */
    private void removeObjectsOutOfBounds() {
        for (GameObject gameObject: gameObjects()) {
            if(gameObject.getCenter().y() > windowDimensions.y()) {
                gameObjects().removeGameObject(gameObject);
            }
        }
    }

    /**
     * Check if the game ended - no more lives left,
     * and prompt a dialog asking if user wants to play another round.
     */
    public void checkForGameEnd() {
        float ballHeight = ball.getCenter().y();
        String prompt = "";
        if(ballHeight > windowDimensions.y()) {
            if(lifeCounter.value() > 1) {
                lifeCounter.decrement();
                ball.setVelocity(Vector2.UP.mult(BALL_SPEED));
                ball.setCenter(windowDimensions.mult((float)(0.5)));
            }
            else {
                prompt = "You Lose!";
            }
        }
        if(brickCounter.value() <= 0) {
            prompt = "You Win!";
        }
        if(!prompt.isEmpty()){
            prompt += "Play again?";
            if(windowController.openYesNoDialog(prompt)){
                windowController.resetGame();
            }
            else {
                windowController.closeWindow();
            }
        }
    }

    /**
     * initialize the game background
     */
    private void initializeBackground() {
        GameObject background = new GameObject(
                Vector2.ZERO,
                windowDimensions,
                imageReader.readImage("assets/DARK_BG2_small.jpeg", false));
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    /**
     * initialize the graphic lives counter -
     * hearts that appear in the bottom left corner of the screen.
     */
    private void initializeGraphicLifeCounter() {
        Renderable graphicLifeCounterImage = imageReader.readImage("assets/heart.png", true);
        GraphicLifeCounter graphicLifeCounter = new GraphicLifeCounter(
                new Vector2(25, windowDimensions.y() - 25),
                new Vector2(20, 20),
                lifeCounter,
                graphicLifeCounterImage,
                gameObjects(),
                NUM_OF_LIVES);
        gameObjects().addGameObject(graphicLifeCounter, Layer.UI);
    }

    private void initializeNumericLifeCounter() {
        NumericLifeCounter numericLifeCounter = new NumericLifeCounter(
                lifeCounter,
                new Vector2(5, windowDimensions.y() - 25),
                new Vector2(20, 20),
                gameObjects()
        );
        gameObjects().addGameObject(numericLifeCounter, Layer.BACKGROUND);
    }

    /**
     * initialize the paddle
     */
    private void initializePaddle() {
        Renderable paddleImage = imageReader.readImage("assets/paddle.png", false);
        GameObject userPaddle = new Paddle(
                Vector2.ZERO,
                new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                paddleImage,
                inputListener,
                windowDimensions,
                MIN_DISTANCE_FROM_SCREEN_EDGE);
        userPaddle.setCenter(new Vector2(windowDimensions.x()/2,(int)windowDimensions.y()-40));
        gameObjects().addGameObject(userPaddle);
    }

    /**
     * initialize the game ball
     */
    private void initializeBall() {
        Sound collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
        Renderable ballImage = imageReader.readImage("assets/ball.png", true);
        ball = new Ball(Vector2.ZERO, new Vector2(BALL_RADIUS, BALL_RADIUS), ballImage, collisionSound);

        ball.setVelocity(Vector2.UP.mult(BALL_SPEED));
        ball.setCenter(windowDimensions.mult((float) (0.65)));
        gameObjects().addGameObject(ball);
    }

    /**
     * initialize the bricks
     */
    private void initializeBricks() {
        Renderable brickImage = imageReader.readImage("assets/brick.png", false);
        BrickStrategyFactory brickStrategyFactory = new BrickStrategyFactory(
                gameObjects(),
                this,
                imageReader,
                soundReader,
                inputListener,
                windowController,
                windowDimensions);
        float margin = 10;
        float brickGap = 2;
        float brickWidth = (windowDimensions.x()- (margin*2) - (7*brickGap)) / 8;
        float brickHeight = 15;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 8; j++) {
                GameObject brick = new Brick(
                        new Vector2(margin + (j * (brickWidth+brickGap)),
                                margin + (i * (brickHeight+brickGap))),
                        new Vector2(brickWidth, brickHeight),
                        brickImage,
                        brickStrategyFactory.getStrategy(),
                        brickCounter
                );
                gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
            }
        }
    }

    /**
     * initialize game borders so that the ball won't exit the frame
     */
    private void initializeWalls() {
        // Upper Wall
        this.gameObjects().addGameObject(
                new GameObject(
                        new Vector2(0, -BORDER_WIDTH),
                        new Vector2(windowDimensions.x(), (float) BORDER_WIDTH),
                        null
                )
        );
        // Left Wall
        this.gameObjects().addGameObject(
                new GameObject(
                        new Vector2(-BORDER_WIDTH, 0),
                        new Vector2(BORDER_WIDTH, windowDimensions.x()),
                        null
                )
        );
        // Right Wall
        this.gameObjects().addGameObject(
                new GameObject(
                        new Vector2(windowDimensions.x(), 0),
                        new Vector2(BORDER_WIDTH, windowDimensions.y()),
                        null
                )
        );

    }

    public static void main(String[] args) {
        new BrickerGameManager("bricker",
                new Vector2(700, 500)).run();
    }
}
