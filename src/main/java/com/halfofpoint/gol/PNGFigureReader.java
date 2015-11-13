package com.halfofpoint.gol;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mart on 13/11/15.
 */
public class PNGFigureReader extends FigureReader {

    @Override
    public List<World.Dot> readFigure(char fileName) {

        URL resource = TextFileFigureReader.class.getClassLoader().getResource(fileName + ".png");

        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(resource);
        } catch (IOException e) {
            return Collections.emptyList();
        }

        List<World.Dot> result = new ArrayList<>();

        for (int x = 0; x < bufferedImage.getWidth(); x++)
            for (int y = 0; y < bufferedImage.getHeight(); y++)
                if (bufferedImage.getRGB(x, y) != 0)
                    result.add(new World.Dot(x, y));

        return result;
    }
}
