package com.halfofpoint.gol;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Pavel_Korshunov
 */
public class World3d {

    final Integer cols;
    final Integer rows;
    final Integer z;

    List<Dot> dots = new ArrayList<>();

    public World3d() {
        this(10, 10, 10);
    }

    public World3d(Integer cols, Integer rows, Integer z) {
        this.cols = cols;
        this.rows = rows;
        this.z = z;
    }

    public void print() {
        char[][] world = new char[cols][rows];
        for (char[] row : world)
            Arrays.fill(row, '.');

        dots.stream().forEach(dot -> world[dot.getX()][dot.getY()] = '*');
        for (char[] row : world) {
            for (char col : row)
                System.out.print(col);
            System.out.println();
        }
        System.out.println();
    }

    public void tick() {
        Set<Dot> dead = new HashSet<>();
        Set<Dot> born = new HashSet<>();

        dead.addAll(dots.stream().filter(dot -> this.findNeighborhoods(dot).size() / 2 != 1).collect(Collectors.toSet()));

        for (int x = 0; x < cols; x++ )
            for (int y = 0; y < rows; y++ )
                for (int z = 0; z < rows; z++ )
                    if (!dotExists(x, y, z) && findNeighborhoods(x, y, z).size() == 3)
                        born.add(new Dot(x, y, z));

        dots.removeAll(dead);
        born.stream().forEach(this::addDot);
    }

    public Dot addDot(Dot dot) {
        if (dotExists(dot)) return null;
        dots.add(dot);
        return dot;
    }

    public Dot newDot(Integer x, Integer y, Integer z) {
        if (x > cols - 1 || y > rows - 1) return null;
        Dot dot = new Dot(x, y, z);
        return addDot(dot);
    }

    public World3d addDot(Integer x, Integer y, Integer z) {
        if (x > cols - 1 || y > rows - 1)
            return this;
        Dot dot = new Dot(x, y, z);
        addDot(dot);
        return this;
    }

    public List<Dot> getDots() {
        return dots;
    }

    public void setDots(List<Dot> dots) {
        this.dots = dots;
    }

    private boolean dotExists(Dot dot) {
        return dotExists(dot.getX(), dot.getY(), dot.getZ());
    }

    private boolean dotExists(Integer x, Integer y, Integer z) {
        return dots.stream()
                .filter(dot -> dot.getX().equals(x) && dot.getY().equals(y) && dot.getZ().equals(z))
                .count() > 0;
    }

    public List<Dot> findNeighborhoods(Dot dot) {
        Integer x = dot.getX();
        Integer y = dot.getY();
        Integer z = dot.getZ();
        return findNeighborhoods(x, y, z).stream()
                .filter(dot2 -> !dot2.equals(dot))
                .collect(Collectors.toList());
    }

    public List<Dot> findNeighborhoods(Integer x, Integer y, Integer z) {
        return dots.stream()
                .filter(dot -> !(dot.getX() < x - 1 ||
                        dot.getX() > x + 1 ||
                        dot.getY() < y - 1 ||
                        dot.getY() > y + 1 ||
                        dot.getZ() < z - 1 ||
                        dot.getZ() > z + 1))
                .collect(Collectors.toList());
    }

    public void killAll() {
        dots = new ArrayList<>();
    }

    public static class Dot {
        Integer x;
        Integer y;
        Integer z;

        public Dot(Integer x, Integer y, Integer z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public void setX(Integer x) {
            this.x = x;
        }
        public void setY(Integer y) {
            this.y = y;
        }
        public void setZ(Integer z) {
            this.z = z;
        }

        public Integer getX() {
            return x;
        }

        public Integer getY() {
            return y;
        }
        public Integer getZ() {
            return z;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Dot dot = (Dot) o;

            if (x != null ? !x.equals(dot.x) : dot.x != null) return false;
            if (y != null ? !y.equals(dot.y) : dot.y != null) return false;
            return !(z != null ? !z.equals(dot.z) : dot.z != null);

        }

        @Override
        public int hashCode() {
            int result = x != null ? x.hashCode() : 0;
            result = 31 * result + (y != null ? y.hashCode() : 0);
            result = 31 * result + (z != null ? z.hashCode() : 0);
            return result;
        }
    }
}
