package tetris.util;

import tetris.gui.ColorScheme;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

/**
 * Created by max on 2016-04-15.
 */
public class GaussianBlur {

    private ConvolveOp hblur, vblur;
    private int blurRadius;
    private int blurOffset;

    /**
     * Creates a two-pass (separated) Gaussian blur filter.
     * @param radius the standard deviation of the Gaussian distribution
     * @param offset the number of standard deviation that this kernel covers
     */
    public GaussianBlur(int radius, int offset) {
        blurRadius = radius;
        blurOffset = offset;
        generateGaussian1DConvolutionKernel();
    }

    public GaussianBlur() {
        blurRadius = 5;
        blurOffset = 3;
        generateGaussian1DConvolutionKernel();
    }

    /**
     * Calculates the value of a Gaussian distribution at a given point.
     * @param sigma the standard deviation of the Gaussian distribution
     * @param x the point at which to calculate the value
     * @return the value of the Gaussian distribution at point x
     */
    private float Gaussian1D(float sigma, float x) {
        float twoSigmaSq = 2.0f * sigma * sigma;
        float pi = (float) Math.PI;
        return (float) (Math.exp(-x * x / twoSigmaSq) /
                Math.sqrt(twoSigmaSq * pi));
    }

    /**
     * Creates the necessary horizontal and vertical convolution kernels for
     * a Gaussian blur filter.
     */
    private void generateGaussian1DConvolutionKernel() {
        int size = blurRadius * 2 * blurOffset + 1;

        float[] weights = new float[size];

        // Sample 1D Gaussian function at pixel locations
        for (int i = 0; i < size; i++) {
            float x = (float) i - blurRadius * blurOffset;
            weights[i] = Gaussian1D((float) blurRadius, x);
        }

        // Create two perpendicular convolution kernels (the Gaussian
        // convolution is separable)

        // Horizontal
        Kernel hConvKernel = new Kernel(size, 1, weights);
        hblur = new ConvolveOp(hConvKernel, ConvolveOp.EDGE_NO_OP, null);

        // Vertical
        Kernel vConvKernel = new Kernel(1, size, weights);
        vblur = new ConvolveOp(vConvKernel, ConvolveOp.EDGE_NO_OP, null);
    }

    /**
     * A helper function which generates a blurred background of the screen
     * before the modal window is drawn on top.
     */
    public BufferedImage apply(BufferedImage src) {

        // Calculate edge offset
        int offset = 2 * blurRadius * blurOffset;
        int w = src.getWidth();
        int h = src.getHeight();

        // Create a temporary oversized buffer for proper edge effects
        BufferedImage offsetBuffer = new BufferedImage(w + offset, h + offset,
                BufferedImage.TYPE_INT_RGB);

        // Draw src buffer to temporary oversized buffer
        Graphics g = offsetBuffer.getGraphics();
        g.setColor(ColorScheme.BASE_00.color);
        g.fillRect(0, 0, w + offset, h + offset);
        g.drawImage(src, offset / 2, offset / 2, null);

        // Apply blur in two passes: horizontal and vertical
        BufferedImage hBlurBuffer = new BufferedImage(w, h + offset,
                BufferedImage.TYPE_INT_RGB);
        hblur.filter(offsetBuffer, hBlurBuffer);
        BufferedImage dst = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        vblur.filter(hBlurBuffer, dst);

        return dst;
    }
}
