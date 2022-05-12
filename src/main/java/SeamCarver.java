
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
        System.out.printf("Here is the value before square root: %f\n", calculateHorizontalEnergy(x, y) + calculateVerticalEnergy(x, y));
        return Math.sqrt(calculateHorizontalEnergy(x, y) + calculateVerticalEnergy(x, y));
    }

    private int setRGB(int blue, int green, int red) {
        return (red << 16) + (green << 8) + (blue << 0);
    }

    private double calculateHorizontalEnergy(int x, int y) {
// (x-1, y),  ( x+1, y)
        int vRgb = currentPicture.getRGB(x - 1, y);
        int vBlue = (vRgb >> 0) & 0xFF;
        int vGreen = (vRgb >> 8) & 0xFF;
        int vRed = (vRgb >> 16) & 0xFF;
        int wRgb = currentPicture.getRGB(x + 1, y);
        int wBlue = (wRgb >> 0) & 0xFF;
        int wGreen = (wRgb >> 8) & 0xFF;
        int wRed = (wRgb >> 16) & 0xFf;
        return Math.pow(Math.abs(vRed - wRed), 2) + Math.pow(Math.abs(vGreen - wGreen), 2) + Math.pow(Math.abs(vBlue - wBlue), 2);
    }

    private double calculateVerticalEnergy(int x, int y) {
// v = (x, y-1),  w = (x, y+1)
        int vRgb = currentPicture.getRGB(x, y - 1);
        int vBlue = (vRgb >> 0) & 0xFF;
        int vGreen = (vRgb >> 8) & 0xFF;
        int vRed = (vRgb >> 16) & 0xFF;
        int wRgb = currentPicture.getRGB(x, y + 1);
        int wBlue = (wRgb >> 0) & 0xFF;
        int wGreen = (wRgb >> 8) & 0xFF;
        int wRed = (wRgb >> 16) & 0xFF;
        return Math.pow(Math.abs(vRed - wRed), 2) + Math.pow(Math.abs(vGreen - wGreen), 2) + Math.pow(Math.abs(vBlue - wBlue), 2);
    }

    //  unit testing (optional)
    public static void main(String[] args) {
        File pic = new File("seam/3x4.png");
        Picture picture = new Picture(pic);
        SeamCarver seamCarver = new SeamCarver(picture);
        System.out.printf("%f\n", seamCarver.energy(1, 2));
    }

}
