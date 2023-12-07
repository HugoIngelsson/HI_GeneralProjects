import java.awt.image.*; 
import java.awt.Graphics;
import java.io.File; 
import java.io.IOException; 
import javax.imageio.ImageIO;

public class Canny {
    static BufferedImage image = null;
    static int width, height;
    static int bigSum;

    public static void main(String[] args) throws IOException {
        long time = System.currentTimeMillis();
        width = 1920; height = 1355;
        readImage("Testing Data/Large_Scaled_Forest_Lizard.jpg", width, height);
        
    for (int i=0; i<5; i++) {
        // writeImage("writing test 4.png", "png");
        long deltaT = System.currentTimeMillis() - time;
        time += deltaT;
        System.out.println("Image loading time: " + (deltaT / 1000.0));

        int[][] kernel1_1 = {{ 0, 1, 0}};
        int[][] kernel1_2 = {{0},{1},{0}};
        // int[][] kernel1 = {{1,2,4,2,1},{2,4,8,4,2},{4,8,16,8,4},{2,4,8,4,2},{1,2,4,2,1}};

        bigSum = 4;
        int[][] imageArray = grayScaleImage(image);
        // image = applyIntArray(image, imageArray);
        // writeImage("Outputs/writing test 3.png", "png");

        imageArray = applyKernel(imageArray, kernel1_1);
        imageArray = applyKernel(imageArray, kernel1_2);
        // image = applyIntArray(image, imageArray);
        // writeImage("writing test 1.png", "png");
        deltaT = System.currentTimeMillis() - time;
        time += deltaT;
        System.out.println("Converting image to gray-scale and blurring: " + (deltaT / 1000.0));
        
        int[][] Gx = calculateGradientX(imageArray, 1), Gy = calculateGradientY(imageArray, 1);
        int[][] G = magnitudeGradients(Gx, Gy), theta = directionGradients(Gx, Gy);

        // image = applyIntArray(image, G);
        // writeImage("writing test 2.png", "png");
        deltaT = System.currentTimeMillis() - time;
        time += deltaT;
        System.out.println("Calculating gradients: " + (deltaT / 1000.0));

        G = magnitudeThresholding(G, theta);
        // image = applyIntArray(image, G);
        // writeImage("writing test 3.png", "png");
        deltaT = System.currentTimeMillis() - time;
        time += deltaT;
        System.out.println("Magnitude Thresholding: " + (deltaT / 1000.0));

        G = doubleThresholdHysterisis(G, 40, 80);
        // image = applyIntArray(image, G);
        // writeImage("writing test 4.png", "png");
        deltaT = System.currentTimeMillis() - time;
        time += deltaT;
        System.out.println("Applying double threshold and hysterisis: " + (deltaT / 1000.0));

        G = maximizePixels(G);
        // image = applyIntArray(image, G);
        // writeImage("writing test 5.png", "png");
        deltaT = System.currentTimeMillis() - time;
        time += deltaT;
        System.out.println("Brightening all pixels: " + (deltaT / 1000.0));
    
        if (i == 4) {
            image = applyIntArray(image, G);
            writeImage("Outputs/final output.png", "png");
            deltaT = System.currentTimeMillis() - time;
            time += deltaT;
            System.out.println("Outputting final image: " + (deltaT / 1000.0));
        }
        
    }
    }

    public static int[][] maximizePixels(int[][] array) {
        int[][] ret = new int[array.length][array[0].length];

        for (int r=3; r<image.getHeight()-3; r++) {
            for (int c=3; c<image.getWidth()-3; c++) {
                if ((array[r][c] & 0xff) > 0) ret[r][c] = 0xffffffff;
            }
        }

        return ret;
    }

    public static BufferedImage applyIntArray(BufferedImage image, int[][] array) {
        for (int r=0; r<image.getHeight(); r++) {
            for (int c=0; c<image.getWidth(); c++) {
                int RGB = Math.min(array[r][c],255)*65793 | 0xff000000;
                image.setRGB(c,r,RGB);
            }
        }

        return image;
    }

    public static int[][] doubleThresholdHysterisis(int[][] G, int lowThresh, int highThresh) {
        int[][] ret = new int[G.length][G[0].length];

        for (int r=1; r<image.getHeight()-1; r++) {
            for (int c=1; c<image.getWidth()-1; c++) {
                if (G[r][c] >= highThresh) ret[r][c] = G[r][c];
                else if (G[r][c] >= lowThresh && blobAnalysis(r,c,highThresh,G)) ret[r][c] = G[r][c];
            }
        }

        return ret;
    }

    public static boolean blobAnalysis(int r, int c, int highThresh, int[][] G) {
        for (int i=-1; i<=1; i++) for (int j=-1; j<=1; j++)
            if (G[r+i][c+j] >= highThresh) return true;
        return false;
    }

    public static int[][] magnitudeThresholding(int[][] G, int[][] theta) {
        int[][] ret = new int[G.length][G[0].length];

        for (int r=1; r<image.getHeight()-1; r++) {
            for (int c=1; c<image.getWidth()-1; c++) {
                if (theta[r][c] == 0) {
                    if (G[r][c+1] < G[r][c] && G[r][c-1] < G[r][c]) ret[r][c] = G[r][c];
                }
                else if (theta[r][c] == 1) {
                    if (G[r+1][c+1] < G[r][c] && G[r-1][c-1] < G[r][c]) ret[r][c] = G[r][c];
                }
                else if (theta[r][c] == 2) {
                    if (G[r+1][c] < G[r][c] && G[r-1][c] < G[r][c]) ret[r][c] = G[r][c];
                }
                else {
                    if (G[r-1][c+1] < G[r][c] && G[r+1][c-1] < G[r][c]) ret[r][c] = G[r][c];
                }
            }
        }

        return ret;
    }

    public static int[][] directionGradients(int[][] Gx, int[][] Gy) {
        int[][] theta = new int[Gx.length][Gx[0].length];
        
        for (int r=0; r<Gx.length; r++) {
            for (int c=0; c<Gx[0].length; c++) {
                int x = Math.abs(Gx[r][c]), y = Math.abs(Gy[r][c]);

                // handling the directions this way is computationally
                // less expensive than actually calculating theta
                if (y < x * 2 / 5) theta[r][c] = 0;
                else if (y > x * 5 / 2) theta[r][c] = 2;
                else if (Gx[r][c] * Gy[r][c] > 0) theta[r][c] = 1;
                else theta[r][c] = 3;
            }
        }

        return theta;
    }

    public static int[][] magnitudeGradients(int[][] Gx, int[][] Gy) {
        int[][] G = new int[Gx.length][Gx[0].length];
        
        for (int r=0; r<Gx.length; r++) {
            for (int c=0; c<Gx[0].length; c++) {
                // this calculation isn't ideal compared to the standard
                // distance function, sqrt(Gx^2 + Gy^2), but it's much
                // faster at computing
                G[r][c] = Math.abs(Gx[r][c]) + Math.abs(Gy[r][c]);
            }
        }

        return G;
    }

    public static int[][] calculateGradientX(int[][] image, int delta) {
        int[][] ret = new int[image.length][image[0].length];

        for (int r=delta; r<image.length-delta; r++) {
            for (int c=delta; c<image[0].length-delta; c++) {
                ret[r][c] = image[r][c+delta] - image[r][c-delta];
            }
        }

        return ret;
    }

    public static int[][] calculateGradientY(int[][] image, int delta) {
        int[][] ret = new int[image.length][image[0].length];

        for (int r=delta; r<image.length-delta; r++) {
            for (int c=delta; c<image[0].length-delta; c++) {
                ret[r][c] = image[r+delta][c] - image[r-delta][c];
            }
        }

        return ret;
    }

    // applies a (2n+1)x(2m+1) kernel to an image,
    // using the center of the kernel as the original
    // pixel color's location
    public static int[][] applyKernel(int[][] image, int[][] kernel) {
        int[][] ret = new int[image.length][image[0].length];

        for (int r=kernel.length/2; r<height-kernel.length/2; r++) {
            for (int c=kernel[0].length/2; c<width-kernel[0].length/2; c++) {
                ret[r][c] = kernelPixel(image, kernel, r, c);
            }
        }

        return ret;
    }

    public static int[][] grayScaleImage(BufferedImage image) {
        int[][] ret = new int[image.getHeight()][image.getWidth()];

        for (int r=0; r<image.getHeight(); r++) {
            for (int c=0; c<image.getWidth(); c++) {
                ret[r][c] = image.getRGB(c,r) & 0xff;
            }
        }

        return ret;
    }

    public static int kernelPixel(int[][] image, int[][] kernel, int r, int c) {
        int ret = 0;
        
        for (int row = 0; row < kernel.length; row++) {
            for (int col = 0; col < kernel[0].length; col++) {
                ret += image[r+row-kernel.length/2][c+col-kernel[0].length/2] << kernel[row][col];
            }
        }

        return ret / bigSum;
    }

    public static void totalKernelSum(int[][] kernel) {
        bigSum = 0;

        for (int i=0; i<kernel.length; i++)
            for (int j=0; j<kernel[0].length; j++)
                bigSum += kernel[i][j];
    }

    public static void readImage(String fileName, int width, int height) throws IOException {
        File file = new File(fileName);
        image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = image.getGraphics();

        g.drawImage(ImageIO.read(file), 0, 0, null);
        g.dispose();
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