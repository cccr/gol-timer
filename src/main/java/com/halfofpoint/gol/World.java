package com.halfofpoint.gol;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Pavel_Korshunov
 */
public class World {

    final Integer cols;
    final Integer rows;

    List<Dot> dots = new ArrayList<>();

    public World() {
        this(10, 10);
    }

    public World(Integer cols, Integer rows) {
        this.cols = cols;
        this.rows = rows;
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

        dead.addAll(dots.stream().filter(dot -> this.findNeighborhoods(dot).size()/2 != 1).collect(Collectors.toSet()));

        for (int x = 0; x < cols; x++ )
            for (int y = 0; y < rows; y++ )
                if (!dotExists(x, y) && findNeighborhoods(x, y).size() == 3)
                    born.add(new Dot(x, y));

        dots.removeAll(dead);
        born.stream().forEach(this::addDot);
    }

    public Dot addDot(Dot dot) {
        if (dotExists(dot)) return null;
        dots.add(dot);
        return dot;
    }

    public Dot newDot(Integer x, Integer y) {
        if (x > cols - 1 || y > rows - 1) return null;
        Dot dot = new Dot(x, y);
        return addDot(dot);
    }

    public World addDot(Integer x, Integer y) {
        if (x > cols - 1 || y > rows - 1)
            return this;
        Dot dot = new Dot(x, y);
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
        return dotExists(dot.getX(), dot.getY());
    }

    private boolean dotExists(Integer x, Integer y) {
        return dots.stream()
                .filter(dot -> dot.getX().equals(x) && dot.getY().equals(y))
                .count() > 0;
    }

    public List<Dot> findNeighborhoods(Dot dot) {
        Integer x = dot.getX();
        Integer y = dot.getY();
        return findNeighborhoods(x, y).stream()
                .filter(dot2 -> !dot2.equals(dot))
                .collect(Collectors.toList());
    }

    public List<Dot> findNeighborhoods(Integer x, Integer y) {
        return dots.stream()
                .filter(dot -> !(dot.getX() < x - 1 ||
                        dot.getX() > x + 1 ||
                        dot.getY() < y - 1 ||
                        dot.getY() > y + 1))
                .collect(Collectors.toList());
    }

    public void killAll() {
        dots = new ArrayList<>();
    }

    public static class Dot {
        Integer x;
        Integer y;

        public Dot(Integer x, Integer y) {
            this.x = x;
            this.y = y;
        }

        public void setX(Integer x) {
            this.x = x;
        }
        public void setY(Integer y) {
            this.y = y;
        }

        public Integer getX() {
            return x;
        }

        public Integer getY() {
            return y;
        }

        public boolean equals(Dot dot) {
            return this == dot || !(x != null ? !x.equals(dot.x) : dot.x != null) && !(y != null ? !y.equals(dot.y) : dot.y != null);

        }

        @Override
        public int hashCode() {
            int result = x != null ? x.hashCode() : 0;
            result = 31 * result + (y != null ? y.hashCode() : 0);
            return result;
        }
    }
}
