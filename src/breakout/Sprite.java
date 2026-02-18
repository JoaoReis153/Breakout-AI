package breakout;

import java.awt.*;

public class Sprite {

    int x;
    int y;
    int imageWidth;
    int imageHeight;
    Image image;

    protected void setX(int x) {
        this.x = x;
    }

    int getX() {
        return x;
    }

    protected void setY(int y) {
        this.y = y;
    }

    int getY() {
        return y;
    }

    int getImageWidth() {
        return imageWidth;
    }

    int getImageHeight() {
        return imageHeight;
    }

    Image getImage() {
        return image;
    }

    Rectangle getRect() {
        if (image == null) {
            return new Rectangle(x, y, 0, 0);
        }
        return new Rectangle(x, y,
                image.getWidth(null), image.getHeight(null));
    }

    void getImageDimensions() {
        if (image != null) {
            imageWidth = image.getWidth(null);
            imageHeight = image.getHeight(null);

            if (imageWidth == -1 || imageHeight == -1) {
                System.err.println("Warning: Image dimensions not available for " + getClass().getSimpleName());
            }
        } else {
            System.err.println("Error: Image is null for " + getClass().getSimpleName());
            imageWidth = 0;
            imageHeight = 0;
        }
    }
}
