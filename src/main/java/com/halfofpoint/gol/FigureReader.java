package com.halfofpoint.gol;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by mart on 13/11/15.
 */
public abstract class FigureReader {
    abstract public List<World.Dot> readFigure(char fileName);

    public List<World.Dot> readFigureWithShift(char fileName, int xShift, int yShift) {
        List<World.Dot> dots = readFigure(fileName);
        dots.stream().forEach(dot -> {
            dot.setX(dot.getX() + xShift);
            dot.setY(dot.getY() + yShift);
        });

        return dots;
    }
}
