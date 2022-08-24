package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Puck;

public class PuckStrategy extends RemoveBrickStrategyDecorator {
    private final ImageReader imageReader;
    private final SoundReader soundReader;

    /**
     * Concrete class extending abstract RemoveBrickStrategyDecorator.
     * Introduces several pucks instead of brick once removed.
     * @param toBeDecorated held object of CollisionStrategy
     * @param imageReader manages image rendering for visible game objects.
     * @param soundReader manages sound rendering for game objects.
     */
    public PuckStrategy(CollisionStrategy toBeDecorated,
                        ImageReader imageReader,
                        SoundReader soundReader) {
        super(toBeDecorated);
        this.imageReader = imageReader;
        this.soundReader = soundReader;
    }

    /**
     * Removes an object on collision.
     * @param thisObj current gameObject.
     * @param otherObj other object of collision.
     * @param counter global brick counter.
     */
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        initializePucks(new Vector2(
                thisObj.getCenter().x(),
                thisObj.getCenter().y() + 50),
                thisObj.getDimensions().x()/3,
                otherObj.getVelocity()
                );
    }

    /**
     * Generates 3 pucks and sends them to different directions.
     * @param center location to initialize pucks in.
     * @param puckSize size of wanted puck. 1/3 of brick.
     * @param velocity speed of pucks.
     */
    private void initializePucks(Vector2 center, float puckSize, Vector2 velocity) {
        Sound collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
        Renderable puckImage = imageReader.readImage("assets/mockBall.png", true);
        Puck puck1 = new Puck(Vector2.ZERO, new Vector2(puckSize, puckSize), puckImage, collisionSound);
        puck1.setVelocity(velocity.rotated(90).mult((float) 0.8));
        puck1.setCenter(center);
        toBeDecorated.getGameObjectCollection().addGameObject(puck1);

        Puck puck2 = new Puck(Vector2.ZERO, new Vector2(puckSize, puckSize), puckImage, collisionSound);
        puck2.setVelocity(velocity.rotated(180).mult((float) 0.8));
        puck2.setCenter(center);
        toBeDecorated.getGameObjectCollection().addGameObject(puck2);

        Puck puck3 = new Puck(Vector2.ZERO, new Vector2(puckSize, puckSize), puckImage, collisionSound);
        puck3.setVelocity(velocity.rotated(270).mult((float) 1.2));
        puck3.setCenter(center);
        toBeDecorated.getGameObjectCollection().addGameObject(puck3);
    }
}
