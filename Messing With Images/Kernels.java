import java.awt.image.*; 
import java.io.File; 
import java.io.IOException; 
import javax.imageio.ImageIO; 

public class Kernels {
    static BufferedImage image = null;
    static int width, height;
    static int bigSum;

    public static void main(String[] args) throws IOException {
        readImage("testing image.png", 300, 300);
        width = image.getWidth();
        height = image.getHeight();

        // for (int r=0; r<image.getHeight(); r++) {
        //     for (int c=0; c<image.getWidth(); c++) {
        //         int RGB = image.getRGB(c,r);
        //         int blue = (RGB & 0xff) >> 1;
        //         int green = (RGB & 0xff00) >> 9;
        //         int red = (RGB & 0xff0000) >> 17;
        //         int alpha = (RGB & 0xff000000);

        //         image.setRGB(c,r,(-1 << 24) + (red<<16)+(green<<8)+blue);
        //     }
        // }

        int[][] kernel1 = {{ 1, 4, 6, 4, 1},
                           { 4,16,24,16, 4},
                           { 6,24,36,24, 6},
                           { 4,16,24,16, 4},
                           { 1, 4, 6, 4, 1}};

        totalKernelSum(kernel1);
        image = applyKernel(image, kernel1, true);
        writeImage("writing test 1.png", "png");
        BufferedImage imageCopy = deepCopy(image);

        int[][] kernel2 = {{ 1, 2, 1},
                           { 0, 0, 0},
                           {-1,-2,-1}};
        int[][] kernel2_1 = {{ 1, 0,-1},
                             { 2, 0,-2},
                             { 1, 0,-1}};

        bigSum = 1;
        image = applyKernel(image, kernel2, false);
        imageCopy = applyKernel(imageCopy, kernel2_1, false);
        writeImage("writing test 2.png", "png");

        // int[][] kernel3 = {{1},
        //                    {0},
        //                    {0}};

        // totalKernelSum(kernel3);
        // // BufferedImage imageCopy = deepCopy(image);
        // imageCopy = applyKernel(imageCopy, kernel3, true);
        
        edgeBothDirections(image, imageCopy);
        // sufficientlyDifferent(image, imageCopy, 40);
        writeImage("writing test 3.png", "png");
    }

    public static int[][] calculateGradients(BufferedImage image, boolean dir) {
        int[][] ret = new int[image.getHeight()][image.getWidth()];

        for (int r=1; r<image.getHeight()-1; r++) {
            for (int c=1; c<image.getWidth()-1; c++) {
                if (dir) ret[r][c] = (image.getRGB(c,r+1) & 0xff) - (image.getRGB(c,r-1) & 0xff);
                else ret[r][c] = (image.getRGB(c+1,r) & 0xff) - (image.getRGB(c,r-1) & 0xff);
            }
        }

        return ret;
    }

    public static void edgeBothDirections(BufferedImage image1, BufferedImage image2) {
        for (int r=0; r<image1.getHeight(); r++) {
            for (int c=0; c<image1.getWidth(); c++) {
                if ((image1.getRGB(c,r) & 0xff) < 128 || (image2.getRGB(c,r) & 0xff) < 128) image1.setRGB(c,r,0xff000000);
            }
        }
    }

    public static void sufficientlyDifferent(BufferedImage image1, BufferedImage image2, int cutoff) {
        for (int r=0; r<image1.getHeight(); r++) {
            for (int c=0; c<image1.getWidth(); c++) {
                if (Math.abs((image1.getRGB(c,r) & 0xff) - (image2.getRGB(c,r) & 0xff)) > cutoff) image1.setRGB(c,r,0xffffffff);
                else image1.setRGB(c,r,0xff000000);
            }
        }
    }

    // applies a (2n+1)x(2m+1) kernel to an image,
    // using the center of the kernel as the original
    // pixel color's location
    public static BufferedImage applyKernel(BufferedImage image, int[][] kernel, boolean normalize) {
        BufferedImage imageEdit = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int r=kernel.length/2; r<height-kernel.length/2; r++) {
            for (int c=kernel[0].length/2; c<width-kernel[0].length/2; c++) {
                imageEdit.setRGB(c, r, kernelPixel(image, kernel, r, c, normalize));
            }
        }

        return imageEdit;
    }

    public static int kernelPixel(BufferedImage image, int[][] kernel, int r, int c, boolean normalize) {
        int blues = 0, greens = 0, reds = 0;
        
        for (int row = 0; row < kernel.length; row++) {
            for (int col = 0; col < kernel[0].length; col++) {
                blues += (image.getRGB(c+col-kernel[0].length/2,r+row-kernel.length/2) & 0xff) * kernel[row][col];
                greens += ((image.getRGB(c+col-kernel[0].length/2,r+row-kernel.length/2) & 0xff00) >> 8) * kernel[row][col];
                reds += ((image.getRGB(c+col-kernel[0].length/2,r+row-kernel.length/2) & 0xff0000) >> 16) * kernel[row][col];
            }
        }

        if (normalize) {
            int total = blues + greens + reds;
            blues = total / 3;
            greens = total / 3;
            reds = total / 3;
        }

        return Math.min(blues / bigSum, 255) + (Math.min(greens / bigSum, 255) << 8) + (Math.min(reds / bigSum, 255) << 16) + (-1 << 24);
    }

    public static void totalKernelSum(int[][] kernel) {
        bigSum = 0;

        for (int i=0; i<kernel.length; i++)
            for (int j=0; j<kernel[0].length; j++)
                bigSum += kernel[i][j];
    }

    public static void readImage(String fileName, int width, int height) throws IOException {
        File file = new File(fileName);
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        image = ImageIO.read(file);
    }

    public static void writeImage(String fileName, String fileType) throws IOException {
        File file = new File(fileName);
        ImageIO.write(image, fileType, file);
    }

    static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}