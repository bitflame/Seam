

import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Picture;

import java.io.File;

public class SeamCarver {
    // I wonder if we can use the values at the borders
    // create a seam carver object based on the given picture
    Picture currentPicture;
    int rows;
    int columns;


    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException("picture object is null.");
        currentPicture = new Picture(picture);
        rows = picture.height();
        columns = picture.width();
    }

    // current picture
    public Picture picture() {
        return currentPicture;
    }

    // width of current picture
    public int width() {
        return columns;
    }

    // height of current picture
    public int height() {
        return rows;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x > columns || x < 0)
            throw new IllegalArgumentException("x coordinate is not a valid value for this image.");
        if (y > rows || y < 0)
            throw new IllegalArgumentException("y coordinate is not a valid value for this image.");
//        System.out.printf("Here is the value before taking the square root: %f for %d and %d\n", calculateHorizontalEnergy(x, y) +
//                calculateVerticalEnergy(x, y), x, y);
        if (x == 0 || y == 0 || x == columns - 1 || y == rows - 1) return (1000);
        return Math.sqrt(calculateHorizontalEnergy(x, y) + calculateVerticalEnergy(x, y));
    }

    // energy of pixel at column x and row y
    private int calculateHorizontalEnergy(int x, int y) {
        // (x-1, y),  ( x+1, y) - getRGB() has a companion called setRGB() (0, y), and (x, 0) is 1000
        int wRed = 0, wBlue = 0, wGreen = 0;
        int vRgb = currentPicture.getRGB(x - 1, y);
        int vRed = (vRgb >> 16) & 0xFF;
        int vGreen = (vRgb >> 8) & 0xFF;
        int vBlue = (vRgb >> 0) & 0xFF;
        int wRgb = currentPicture.getRGB(x + 1, y);
        wRed = (wRgb >> 16) & 0xFF;
        wGreen = (wRgb >> 8) & 0xFF;
        wBlue = (wRgb >> 0) & 0xFF;
        int sum = (wRed - vRed) + (wGreen - vGreen) + (wBlue - vBlue);
        int result = (int) Math.pow(wRed - vRed, 2) + (int) Math.pow(wGreen - vGreen, 2) + (int) Math.pow(wBlue - vBlue, 2);
        return result;
        // return (wRed - vRed) + (wGreen - vGreen) + (wBlue - vBlue);
    }

    // x: column y: row
    private int calculateVerticalEnergy(int x, int y) {
        // v = (x, y-1),  w = (x, y+1)
        int vRgb = currentPicture.getRGB(x, y - 1);
        int vRed = (vRgb >> 16) & 0xFF;
        int vGreen = (vRgb >> 8) & 0xFF;
        int vBlue = (vRgb >> 0) & 0xFF;
        int wRgb = currentPicture.getRGB(x, y + 1);
        int wRed = (wRgb >> 16) & 0xFF;
        int wGreen = (wRgb >> 8) & 0xFF;
        int wBlue = (wRgb >> 0) & 0xFF;
        int result = (int) Math.pow(wRed - vRed, 2) + (int) Math.pow(wGreen - vGreen, 2) + (int) Math.pow(wBlue - vBlue, 2);
        return result;
        // return (wRed - vRed) + (wGreen - vGreen) + (wBlue - vBlue);
    }

    public int[] findHorizontalSeam() {
        double[][] energy = new double[rows][columns];
        double[][] distTo = new double[rows][columns];
        int[][] edgeTo = new int[rows][columns];
        int[] horizontalSeam = new int[columns];
        IndexMinPQ pq = new IndexMinPQ(rows);

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++) {
                // infinity value in double
                energy[i][j] = energy(j, i);
                distTo[i][j] = Double.POSITIVE_INFINITY;
                edgeTo[i][j] = j - 1;
            }
        distTo[0][0] = energy[0][0];
        edgeTo[0][0] = 0;
        for (int y = 0; y < rows - 1; y++) {
            Double minEnergy = Double.POSITIVE_INFINITY;
            int minX = Integer.MIN_VALUE;
            for (int x = 0; x < columns; x++) {
                if (y == 0) {
                    distTo[y][x] = energy[y][x];
                    if (!pq.contains(y)) {
                        pq.insert(y, x);
                        horizontalSeam[y] = x;
                        edgeTo[y][x] = x;
                    }
                }
                if (x == 0) {
                    distTo[y + 1][x] = Math.min(energy[y + 1][x] + distTo[y][x], distTo[y + 1][x]);
                    distTo[y + 1][x + 1] = Math.min(distTo[y + 1][x + 1], distTo[y][x] + energy[y + 1][x + 1]);
                    if (minEnergy > distTo[y + 1][x]) {
                        minEnergy = distTo[y + 1][x];
                        minX = x;
                        edgeTo[y + 1][x] = x;
                    }
                    if (minEnergy > distTo[y + 1][x + 1]) {
                        minEnergy = distTo[y + 1][x + 1];
                        minX = x + 1;
                        edgeTo[y + 1][x + 1] = x;
                    }
                } else if (x > 0 && x < columns - 1) {
                    distTo[y + 1][x] = Math.min(energy[y + 1][x] + distTo[y][x], distTo[y + 1][x]);
                    distTo[y + 1][x + 1] = Math.min(distTo[y + 1][x + 1], distTo[y][x] + energy[y + 1][x + 1]);
                    distTo[y + 1][x - 1] = Math.min(distTo[y + 1][x - 1], distTo[y][x] + energy[y + 1][x - 1]);
                    if (minEnergy > distTo[y + 1][x - 1]) {
                        minEnergy = distTo[y + 1][x - 1];
                        minX = x - 1;
                        edgeTo[y + 1][x - 1] = x;
                    }
                    if (minEnergy > distTo[y + 1][x]) {
                        minEnergy = distTo[y + 1][x];
                        minX = x;
                        edgeTo[y + 1][x] = x;
                    }
                    if (minEnergy > distTo[y + 1][x + 1]) {
                        minEnergy = distTo[y + 1][x + 1];
                        minX = x + 1;
                        edgeTo[y + 1][x + 1] = x;
                    }
                } else {
                    distTo[y + 1][x] = Math.min(energy[y + 1][x] + distTo[y][x], distTo[y + 1][x]);
                    distTo[y + 1][x - 1] = Math.min(distTo[y + 1][x - 1], distTo[y][x] + energy[y + 1][x - 1]);
                    if (minEnergy > distTo[y + 1][x - 1]) {
                        minEnergy = distTo[y + 1][x - 1];
                        minX = x - 1;
                        edgeTo[y + 1][x - 1] = x;
                    }
                    if (minEnergy > distTo[y + 1][x]) {
                        minEnergy = distTo[y + 1][x];
                        minX = x;
                        edgeTo[y + 1][x] = x;
                    }
                }
            }
            pq.insert(y + 1, minX);
            horizontalSeam[y + 1] = minX;
            int tempY = y;
            int tempX = edgeTo[y + 1][minX];
            while (tempY >= 0 && pq.keyOf(tempY).compareTo(tempX) != 0) {
                pq.changeKey(tempY, tempX);
                horizontalSeam[tempY] = tempX;
                tempX = edgeTo[tempY][tempX];
                tempY--;
            }
        } // todo -- just add the values to the integer [] rather than using the priority queue
        return horizontalSeam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        double[][] energy = new double[rows][columns];
        double[][] distTo = new double[rows][columns];
        int[][] edgeTo = new int[rows][columns];
        int[] verticalSeam = new int[rows];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++) {
                // infinity value in double
                energy[i][j] = energy(j, i);
                distTo[i][j] = Double.POSITIVE_INFINITY;
                edgeTo[i][j] = j - 1;
            }
        distTo[0][0] = energy[0][0];
        edgeTo[0][0] = 0;
        for (int y = 0; y < rows - 1; y++) {
            Double minEnergy = Double.POSITIVE_INFINITY;
            int minX = Integer.MIN_VALUE;
            for (int x = 0; x < columns; x++) {
                if (y == 0) {
                    distTo[y][x] = energy[y][x];
                }
                if (x == 0) {
                    distTo[y + 1][x] = Math.min(energy[y + 1][x] + distTo[y][x], distTo[y + 1][x]);
                    distTo[y + 1][x + 1] = Math.min(distTo[y + 1][x + 1], distTo[y][x] + energy[y + 1][x + 1]);
                    if (minEnergy > distTo[y + 1][x]) {
                        minEnergy = distTo[y + 1][x];
                        minX = x;
                        edgeTo[y + 1][x] = x;
                    }
                    if (minEnergy > distTo[y + 1][x + 1]) {
                        minEnergy = distTo[y + 1][x + 1];
                        minX = x + 1;
                        edgeTo[y + 1][x + 1] = x;
                    }
                } else if (x > 0 && x < columns - 1) {
                    distTo[y + 1][x] = Math.min(energy[y + 1][x] + distTo[y][x], distTo[y + 1][x]);
                    distTo[y + 1][x + 1] = Math.min(distTo[y + 1][x + 1], distTo[y][x] + energy[y + 1][x + 1]);
                    distTo[y + 1][x - 1] = Math.min(distTo[y + 1][x - 1], distTo[y][x] + energy[y + 1][x - 1]);
                    if (minEnergy > distTo[y + 1][x - 1]) {
                        minEnergy = distTo[y + 1][x - 1];
                        minX = x - 1;
                        edgeTo[y + 1][x - 1] = x;
                    }
                    if (minEnergy > distTo[y + 1][x]) {
                        minEnergy = distTo[y + 1][x];
                        minX = x;
                        edgeTo[y + 1][x] = x;
                    }
                    if (minEnergy > distTo[y + 1][x + 1]) {
                        minEnergy = distTo[y + 1][x + 1];
                        minX = x + 1;
                        edgeTo[y + 1][x + 1] = x;
                    }
                } else {
                    distTo[y + 1][x] = Math.min(energy[y + 1][x] + distTo[y][x], distTo[y + 1][x]);
                    distTo[y + 1][x - 1] = Math.min(distTo[y + 1][x - 1], distTo[y][x] + energy[y + 1][x - 1]);
                    if (minEnergy > distTo[y + 1][x - 1]) {
                        minEnergy = distTo[y + 1][x - 1];
                        minX = x - 1;
                        edgeTo[y + 1][x - 1] = x;
                    }
                    if (minEnergy > distTo[y + 1][x]) {
                        minEnergy = distTo[y + 1][x];
                        minX = x;
                        edgeTo[y + 1][x] = x;
                    }
                }
            }
            verticalSeam[y + 1] = minX;
            int tempY = y;
            int tempX = edgeTo[y + 1][minX];
            while (tempY >= 0) {
                verticalSeam[tempY] = tempX;
                tempX = edgeTo[tempY][tempX];
                tempY--;
            }
        } // todo -- just add the values to the integer [] rather than using the priority queue
        return verticalSeam;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) throw new IllegalArgumentException("The seam object is invalid.");
        if (this.rows == 1)
            throw new IllegalArgumentException("Can not carve any more horizontal seams; image height is 1 pixel.");
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null) throw new IllegalArgumentException("The seam object is invalid.");
        if (this.columns == 1)
            throw new IllegalArgumentException("Can not carve any more vertical seams; image width is 1 pixel.");
    }

    // x: column y: row


    //  unit testing (optional)
    public static void main(String[] args) {
        File pic = new File(args[0]);
        Picture picture = new Picture(pic);
        SeamCarver seamCarver = new SeamCarver(picture);
        System.out.printf("\n");
        System.out.printf("The energy level for pixel (Column %d, Row %d) is: %f\n", 1, 2, seamCarver.energy(1, 2));
        System.out.printf("The energy level for pixel (Column %d, Row %d) is: %f\n", 1, 1, seamCarver.energy(1, 1));
        // 255, 203, 51 color values for 2,0
        double x = Math.sqrt(Math.pow(255, 2) + Math.pow(203, 2) + Math.pow(51, 2));
        System.out.printf("\n");
//        for (double d : seamCarver.findVerticalSeam()) {
//            System.out.printf("%9.2f", d);
//        }
        for(int i: seamCarver.findVerticalSeam()){
            System.out.printf("%d ",i);
        }
    }

}