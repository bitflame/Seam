import edu.princeton.cs.algs4.Picture
import spock.lang.Specification

import java.awt.Color

class SeamCarverSpecification extends Specification {
    def "pixel values should equal"() {
        when:
        File pic = new File("seam/3x4.png")
        Picture picture = new Picture(pic)
        Color currentPixlColor = picture.get(x, y);
        double red = currentPixlColor.getRed();
        double green = currentPixlColor.getGreen();
        double blue = currentPixlColor.getBlue();
        int rgb = picture.getRGB(x, y);
        int rgbBlue = (rgb >> 0) & 0xFF;
        int rgbGreen = (rgb >> 8) & 0xFF;
        int rgbRed = (rgb >> 16) & 0xFF;
    }
}
