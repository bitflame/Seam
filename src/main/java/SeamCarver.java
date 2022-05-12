
import edu.princeton.cs.algs4.Picture;


import java.awt.Color;
import java.io.File;

public class SeamCarver {
    // I wonder if we can use the values at the borders
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
        int rgb = currentPicture.getRGB(x, y);
        double red = currentPixlColor.getRed();
        double green = currentPixlColor.getGreen();
        double blue = currentPixlColor.getBlue();
        System.out.printf("%f %f %f \n", red, green, blue);
        int rgbBlue = (rgb >> 0) & 0xFF;
        int rgbGreen = (rgb >> 8) & 0xFF;
        int rgbRed = (rgb >> 16) & 0xFF;
        System.out.printf("%d %d %d \n", rgbRed, rgbGreen, rgbBlue);
        return -1;
    }

    private double calculateHorizontalEnergy(int x, int y) {
// (x-1, y),  ( x+1, y)
        Color v = currentPicture.get(x - 1, y);
        double vRed = v.getRed();
        double vGreen = v.getGreen();
        double vBlue = v.getBlue();
        Color w = currentPicture.get(x + 1, y);
        double wRed = w.getRed();
        double wGreen = w.getGreen();
        double wBlue = w.getBlue();
        return Math.pow(Math.abs(vRed - wRed), 2) + Math.pow(Math.abs(vGreen - wGreen), 2) + Math.pow(Math.abs(vBlue - wBlue), 2);
    }

    private double calculateVerticalEnergy(int x, int y) {
// v = (x, y-1),  w = (x, y+1)
        Color v = currentPicture.get(x, y - 1);
        double vRed = v.getRed();
        double vGreen = v.getGreen();
        double vBlue = v.getBlue();
        Color w = currentPicture.get(x, y + 1);
        double wRed = w.getRed();
        double wGreen = w.getGreen();
        double wBlue = w.getBlue();
        return Math.pow(Math.abs(vRed - wRed), 2) + Math.pow(Math.abs(vGreen - wGreen), 2) + Math.pow(Math.abs(vBlue - wBlue), 2);
    }

    //  unit testing (optional)
    public static void main(String[] args) {
        File pic = new File("seam/3x4.png");
        Picture picture = new Picture(pic);
        SeamCarver seamCarver = new SeamCarver(picture);
        seamCarver.energy(1, 2);
    }

}
