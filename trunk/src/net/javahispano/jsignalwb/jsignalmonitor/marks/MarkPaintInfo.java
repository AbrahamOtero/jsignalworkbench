/*
 * MarkPaintInfo.java
 *
 * Created on 3 de agosto de 2007, 10:40
 */

package net.javahispano.jsignalwb.jsignalmonitor.marks;

import java.awt.Point;

/**
 *
 * @author Roman Segador
 */
public class MarkPaintInfo {
    private Point point;
    private Point startValue;
    private Point endValue;
    private int maxValueY;
    private int minValueY;
    private int width;
    private int height;

    public MarkPaintInfo(Point point, Point startValue, Point endValue,
                         int maxValueY, int minValueY, int width, int height) {
        this.point = point;
        this.startValue = startValue;
        this.endValue = endValue;
        this.maxValueY = maxValueY;
        this.minValueY = minValueY;
        this.width = width;
        this.height = height;
    }

    public Point getPoint() {
        return point;
    }

    public Point getStartValue() {
        return startValue;
    }

    public Point getEndValue() {
        return endValue;
    }

    public int getMaxValueY() {
        return maxValueY;
    }

    public int getMinValueY() {
        return minValueY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean equals(MarkPaintInfo mpi) {
        if (mpi.getPoint().x == point.x &&
            mpi.getPoint().y == point.y &&
            mpi.getStartValue().x == startValue.x &&
            mpi.getStartValue().y == startValue.y &&
            mpi.getEndValue().x == endValue.x &&
            mpi.getEndValue().y == endValue.y &&
            mpi.getMaxValueY() == maxValueY &&
            mpi.getMinValueY() == minValueY &&
            mpi.getHeight() == height &&
            mpi.getWidth() == width) {
            return true;
        }
        return false;
    }

}
