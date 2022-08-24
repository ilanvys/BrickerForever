package src.gameobjects;

import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Puck extends Ball{
    /**
     * Construct a new Puck instance.
     * One of the types of objects that can be set loose when a brick is hit.
     *
     * @param topLeftCorner  Position of the object, in window coordinates (pixels).
     *                       Note that (0,0) is the top-left corner of the window.
     * @param dimensions     Width and height in window coordinates.
     * @param renderable     The renderable representing the object.
     * @param collisionSound The sound object to be played upon collision.
     */
    public Puck(
            Vector2 topLeftCorner,
            Vector2 dimensions,
            Renderable renderable,
            Sound collisionSound) {
        super(topLeftCorner,
                dimensions,
                renderable,
                collisionSound);
    }
}
