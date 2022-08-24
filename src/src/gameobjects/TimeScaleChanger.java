package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class TimeScaleChanger extends StatusDefiner{
    private final String changerType;
    private final WindowController windowController;

    /**
     * construct a new TimeScaleChanger instance.
     * upon hitting the paddle can cause the game to become
     * faster or slower.
     *
     * @param ChangerType type of TimeScaleChanger - slow or fast
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object.
     * @param collisionSound  The sound object to be played upon collision.
     * @param gameObjects    all the game objects that are rendered.
     * @param windowController manages settings for the game window.
     */
    public TimeScaleChanger(
            String ChangerType,
            Vector2 topLeftCorner,
            Vector2 dimensions,
            Renderable renderable,
            Sound collisionSound,
            GameObjectCollection gameObjects,
            WindowController windowController) {
        super(topLeftCorner,
                dimensions,
                renderable,
                collisionSound,
                gameObjects);
        this.changerType = ChangerType;
        this.windowController = windowController;
    }

    /**
     * upon collision sets the game's time-scale to be faster ot
     * slower according to the changerType
     *
     * @param other The game object the ball collided into.
     * @param collision an instance of the collision
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if(super.shouldCollideWith(other)) {
            if(changerType.equals("slow")) {
                windowController.setTimeScale((float) 0.9);
            }
            if(changerType.equals("fast")) {
                windowController.setTimeScale((float) 1.1);
            }
        }
    }
}
