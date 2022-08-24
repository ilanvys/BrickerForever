package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.TimeScaleChanger;

import java.util.Random;

public class TimeScaleStrategy extends RemoveBrickStrategyDecorator{
    private final CollisionStrategy toBeDecorated;
    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final GameObjectCollection gameObjects;
    private final WindowController windowController;
    private final Random random = new Random();

    /**
     * @param toBeDecorated held object of CollisionStrategy
     * @param imageReader manages image rendering for visible game objects.
     * @param soundReader manages sound rendering for game objects.
     * @param windowController manages settings for the game window.
     */
    public TimeScaleStrategy(
            CollisionStrategy toBeDecorated,
            ImageReader imageReader,
            SoundReader soundReader,
            WindowController windowController) {
        super(toBeDecorated);
        this.toBeDecorated = toBeDecorated;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.windowController = windowController;
        this.gameObjects = toBeDecorated.getGameObjectCollection();
    }

    /**
     * Removes an object on collision, and creates a TimeScale,
     * to fall from the object's location.
     * @param thisObj current gameObject.
     * @param otherObj other object of collision.
     * @param counter global brick counter.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        initializeTimeScale(
                thisObj.getDimensions().x()/2,
                thisObj.getCenter());
    }

    /**
     * Generates TimeScale GameObject and set it to fall from
     * the brick location. If the game is already faster or slower,
     * it only generates TimeScales from the opposite option.
     * @param timeScaleChangerSize size of the TimeScale icons.
     * @param center location to initialize TimeScale in.
     */
    private void initializeTimeScale(float timeScaleChangerSize, Vector2 center) {
        Sound collisionSound = soundReader.readSound("assets/Bubble5_4.wav");
        TimeScaleChanger timeScaleChanger;

        double timeScale = windowController.getTimeScale();
        if(timeScale == 1.1) {
            timeScaleChanger = createSlowChanger(timeScaleChangerSize, collisionSound);
        } else if(timeScale == 0.9) {
            timeScaleChanger = createFastChanger(timeScaleChangerSize, collisionSound);
        } else {
            if(random.nextBoolean()) {
                timeScaleChanger = createFastChanger(timeScaleChangerSize, collisionSound);
            } else {
                timeScaleChanger = createSlowChanger(timeScaleChangerSize, collisionSound);
            }
        }

        timeScaleChanger.setVelocity(Vector2.DOWN.mult((float) 100));
        timeScaleChanger.setCenter(center);
        toBeDecorated.getGameObjectCollection().addGameObject(timeScaleChanger);
    }

    /**
     * @param timeScaleChangerSize size of the TimeScale icons.
     * @param collisionSound The sound object to be played upon collision.
     * @return TimeScaleChanger for slowing the game down.
     */
    private TimeScaleChanger createSlowChanger(float timeScaleChangerSize, Sound collisionSound) {
        Renderable timeScaleChangerImage = imageReader.readImage("assets/slow.png", true);
        return new TimeScaleChanger(
                "slow",
                Vector2.ZERO,
                new Vector2(timeScaleChangerSize*2, timeScaleChangerSize),
                timeScaleChangerImage,
                collisionSound,
                gameObjects,
                windowController);
    }

    /**
     * @param timeScaleChangerSize size of the TimeScale icons.
     * @param collisionSound The sound object to be played upon collision.
     * @return TimeScaleChanger for speeding up the game down.
     */
    private TimeScaleChanger createFastChanger(float timeScaleChangerSize, Sound collisionSound) {
        Renderable timeScaleChangerImage = imageReader.readImage("assets/quicken.png", true);
        return new TimeScaleChanger(
                "fast",
                Vector2.ZERO,
                new Vector2(timeScaleChangerSize*2, timeScaleChangerSize),
                timeScaleChangerImage,
                collisionSound,
                gameObjects,
                windowController);
    }
}
