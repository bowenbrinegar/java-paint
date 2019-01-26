package a8;

import java.awt.*;
import java.awt.image.ImageObserver;

public class ImageTracker implements ImageObserver {
    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return true;
    }
}
