package breakout;

import javax.swing.*;

public class Brick extends Sprite {

    private boolean destroyed;

    public Brick(int x, int y) {
        initBrick(x, y);
    }

    private void initBrick(int x, int y) {
        this.x = x;
        this.y = y;

        destroyed = false;
        loadImage();
        getImageDimensions();
    }

    private void loadImage() {
        var resource = getClass().getResource("/resources/brick.png");
        if (resource == null) {
            System.err.println("Error: Could not find /resources/brick.png in classpath");
            return;
        }
        var ii = new ImageIcon(resource);
        image = ii.getImage();
    }

    boolean isDestroyed() {
        return destroyed;
    }

    void setDestroyed(boolean val) {
        destroyed = val;
    }
}
