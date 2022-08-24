package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class GraphicLifeCounter extends GameObject {
    private final GameObjectCollection gameObjectsCollection;
    private final Counter livesCounter;
    private int numOfLives;
    private GameObject[] graphicLivesArr;

    /**
     * Construct a new GraphicLifeCounter instance.
     *  @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param livesCounter counts lives left for user before game ends.
     * @param widgetRenderable    The renderable representing the object.
     * @param gameObjectsCollection all the game objects that are rendered.
     * @param numOfLives initial number of lives
     */
    public GraphicLifeCounter(Vector2 topLeftCorner,
                              Vector2 dimensions,
                              Counter livesCounter,
                              Renderable widgetRenderable,
                              GameObjectCollection gameObjectsCollection,
                              int numOfLives) {
        super(topLeftCorner, dimensions, widgetRenderable);
        this.livesCounter = livesCounter;
        this.numOfLives = numOfLives;
        this.gameObjectsCollection = gameObjectsCollection;
        this.graphicLivesArr = new GameObject[numOfLives-1];

        for (int i = 0; i < numOfLives - 1; i++) {
            graphicLivesArr[i] = new GameObject(
                    topLeftCorner.add(new Vector2(i*25, 0)),
                    new Vector2(20, 20),
                    widgetRenderable
            );
            gameObjectsCollection.addGameObject(graphicLivesArr[i], Layer.UI);
        }
    }

    /**
     * Checks if lives counter has gone down and removes a widget if necessary
     * @param deltaTime amount of time between rendering the game.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(livesCounter.value() < numOfLives) {
            numOfLives -= 1;
            gameObjectsCollection.removeGameObject(graphicLivesArr[numOfLives-1], Layer.UI);
        }
        if(numOfLives == 1) {
            gameObjectsCollection.removeGameObject(this, Layer.UI);
        }
    }
}