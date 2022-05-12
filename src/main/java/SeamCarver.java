import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Picture;

import java.awt.Color;
import java.io.File;

public class SeamCarver {
    // Start by writing the constructor as well as picture(), width() and height()
    // create a seam carver object based on the given picture
    Picture currentPicture;
    int height;
    int width;

    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException("picture object is null.");
        currentPicture = new Picture(picture);
        height = picture.height();
        width = picture.width();
    }

    // current picture
    public Picture picture() {
        return currentPicture;
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        Color currentPixlColor = currentPicture.get(x, y);
        double red = currentPixlColor.getRed();
        double green = currentPixlColor.getGreen();
        double blue = currentPixlColor.getBlue();
        System.out.printf("%f %f %f ", red, green, blue);
        return -1;
    }

    //  unit testing (optional)
    public static void main(String[] args) {
        File pic = new File("seam/3x4.png");
        Picture picture = new Picture(pic);
        SeamCarver seamCarver = new SeamCarver(picture);
        seamCarver.energy(1, 2);
    }

}
