package com.halfofpoint.gol;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mart on 13/11/15.
 */
public class TextFileFigureReader extends FigureReader {

    @Override
    public List<World.Dot> readFigure(char fileName) {

        List<World.Dot> result = new ArrayList<>();

        URL resource = TextFileFigureReader.class.getClassLoader().getResource(fileName + ".txt");

        int lineNumber = 0;
        List<String> strings;

        try {
            Path path = Paths.get(resource.toURI());
            strings = Files.readAllLines(path);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
        for (String line : strings) {
            int charNumber = 0;
            for (char c : line.toCharArray()) {
                if (c == '*') result.add(new World.Dot(charNumber, lineNumber));
                charNumber++;
            }
            lineNumber++;
        }

        return result;
    }
}
