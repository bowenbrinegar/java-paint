package a8;

import a8.*;

import java.awt.image.BufferedImage;

public class RectangleCreator {
    public static ObservablePicture create(int w, int h) {
        Pixel[][] arr = new Pixel[w][h];

        int i;
        int j;
        for (i = 0; i < w; i++) {
            for (j = 0; j < h; j++) {
                if (i < 20 || i > w - 20 || j < 20 || j > h - 20) {
                    arr[i][j] = Pixel.BLACK;
                } else {
                    arr[i][j] = Pixel.WHITE;
                }
            }
        }

        return new ObservablePictureImpl(new MutablePixelArrayPicture(arr, "selection rectangle"));
    }
}
