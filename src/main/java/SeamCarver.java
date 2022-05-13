

import edu.princeton.cs.algs4.Picture;


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
        if (x > width - 1 || x < 0)
            throw new IllegalArgumentException("x coordinate is not a valid value for this image.");
        if (y > height - 1 || y < 0)
            throw new IllegalArgumentException("y coordinate is not a valid value for this image.");
//        System.out.printf("Here is the value before taking the square root: %f for %d and %d\n", calculateHorizontalEnergy(x, y) +
//                calculateVerticalEnergy(x, y), x, y);
        if (x == 0 || y == 0 || x == width-1 || y == height-1) return (1000);
        return Math.sqrt(calculateHorizontalEnergy(x, y) + calculateVerticalEnergy(x, y));
    }

    public int[] findHorizontalSeam() {
        int[][] energy = new int[height][width];
        int[][] distTo = new int[height][width];
        int[][] edgeTo = new int[height][width];
        int[] horizontalSeam = new int[width];
        return horizontalSeam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[][] energy = new int[height][width];
        int[][] distTo = new int[height][width];
        int[][] edgeTo = new int[height][width];
        int[] verticalSeam = new int[height];
        return verticalSeam;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) throw new IllegalArgumentException("The seam object is invalid.");
        if (this.height == 1)
            throw new IllegalArgumentException("Can not carve any more horizontal seams; image height is 1 pixel.");
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null) throw new IllegalArgumentException("The seam object is invalid.");
        if (this.width == 1)
            throw new IllegalArgumentException("Can not carve any more vertical seams; image width is 1 pixel.");
    }

    private double calculateHorizontalEnergy(int x, int y) {
// (x-1, y),  ( x+1, y) - getRGB() has a companion called setRGB() (0, y), and (x, 0) is 1000
        int vRgb = currentPicture.getRGB(x - 1, y);
        int vBlue = (vRgb >> 0) & 0xFF;
        int vGreen = (vRgb >> 8) & 0xFF;
        int vRed = (vRgb >> 16) & 0xFF;
        int wRgb = currentPicture.getRGB(x + 1, y);
        int wBlue = (wRgb >> 0) & 0xFF;
        int wGreen = (wRgb >> 8) & 0xFF;
        int wRed = (wRgb >> 16) & 0xFf;
        return Math.pow(wRed - vRed, 2) + Math.pow(wGreen - vGreen, 2) + Math.pow(wBlue - vBlue, 2);
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
        return Math.pow(wRed - vRed, 2) + Math.pow(wGreen - vGreen, 2) + Math.pow(wBlue - vBlue, 2);
    }

    //  unit testing (optional)
    public static void main(String[] args) {
        File pic = new File("libs/seam/3x4.png");
        Picture picture = new Picture(pic);
        SeamCarver seamCarver = new SeamCarver(picture);
        System.out.printf("The energy level for pixel (%d, %d) is: %f\n", 1, 2, seamCarver.energy(1, 2));
        System.out.printf("The energy level for pixel (%d, %d) is: %f\n", 1, 1, seamCarver.energy(1, 1));
        // 255, 203, 51 color values for 2,0
        double x = Math.sqrt(Math.pow(255, 2) + Math.pow(203, 2) + Math.pow(51, 2));
        System.out.printf("%f\n", x);
    }

}