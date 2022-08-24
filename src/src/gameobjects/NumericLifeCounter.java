package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;

public class NumericLifeCounter extends GameObject {
    TextRenderable textRenderable;
    Counter livesCounter;
    /**
     * Construct a new GameObject instance.
     *
     * @param livesCounter counts lives left for user before game ends.
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param gameObjectCollection all the game objects that are rendered.
     */
    public NumericLifeCounter(Counter livesCounter,
                              Vector2 topLeftCorner,
                              Vector2 dimensions,
                              GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, dimensions, null);
        this.livesCounter = livesCounter;

        this.textRenderable = new TextRenderable(String.format("%d", livesCounter.value()));
        textRenderable.setColor(Color.RED);
        GameObject text = new GameObject(topLeftCorner, dimensions, this.textRenderable);
        gameObjectCollection.addGameObject(text, Layer.BACKGROUND);
    }

    /**
     * Updates the number of lives left in the game.
     * @param deltaTime amount of time between rendering the game.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        textRenderable.setString(String.format("%d", livesCounter.value()));
    }
}
