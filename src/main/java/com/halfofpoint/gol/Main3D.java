package com.halfofpoint.gol;

import peasy.PeasyCam;
import processing.core.PApplet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by mart on 13/11/15.
 */
public class Main3D extends PApplet {

    final static int CELL_SIZE = 20;
    final static int WORLD_WIDTH = 100;
    final static int WORLD_HEIGHT = 70;

    final static int FRAME_RATE = 1;

    PeasyCam cam;

    FigureReader fr = new PNGFigureReader();

    public static void main(String args[]) {
        PApplet.main(new String[]{"--present", "com.halfofpoint.gol.Main3D"});
    }


    public void settings() {
        //fullScreen(P3D, 2);
        size(WORLD_WIDTH * CELL_SIZE, WORLD_HEIGHT * CELL_SIZE, P3D);
    }

    Map<String, List<World3d.Dot>> charactersDotMap = new HashMap<>(70);

    World3d counterWorld;

    int m;
    int initialFreeze = 0;
    int start = 27;

    public void setup() {

//        cam = new PeasyCam(this, 100);
//        cam.setMinimumDistance(50);
//        cam.setMaximumDistance(500);

        counterWorld = new World3d(WORLD_WIDTH, WORLD_HEIGHT, 40);


        fillNumbersMap();

        fillWorldWithDigits(start, counterWorld);

        background(0);
        noStroke();
        frameRate(FRAME_RATE);

        m = minute();
    }

    private void fillNumbersMap() {
        for (int i = 0; i <=59; i++) {
            String format = String.format("%02d", i);
            char[] number = format.toCharArray();
            List<World.Dot> dots = fr.readFigureWithShift(number[0], 10, 10);
            dots.addAll(fr.readFigureWithShift(number[1], 25, 10));

            List<World3d.Dot> dots3d = dots.stream().map(dot -> {
                return new World3d.Dot(dot.getX(), dot.getY(), 10);
            }).collect(Collectors.toList());

            charactersDotMap.put(format, dots3d);
        }
    }

    void fillWorldWithDigits(int digits, World3d world) {
        world.killAll();
        String number = String.format("%02d", digits);
        charactersDotMap.get(number).stream().forEach(world::addDot);
    }



    private void drawWorld(World3d world, Drawer drawer) {
        world.getDots().stream().forEach(drawer::draw);
    }

    public void draw() {
        background(0);

        drawWorld(counterWorld, new TypicalDrawer());

/*
        if (initialFreeze > FRAME_RATE) initialFreeze = (FRAME_RATE * -4 + 1);

        if (initialFreeze < 0)
            counterWorld.tick();

        initialFreeze++;

        if (frameCount%(FRAME_RATE*5) == 0)
            fillWorldWithDigits(start, counterWorld);

        if (m != minute()) {
            m = minute();
            start--;
            fillWorldWithDigits(start, counterWorld);
        }*/
    }

    public void keyPressed() {
        if (key == CODED) {
            if (keyCode == UP) {
                start++;
            } else if (keyCode == DOWN) {
                start--;
            }
        }
    }



    private float getRandomFloat(int multiplier, int plus) {
        return (float) Math.random()*multiplier+plus;
    }

    interface Drawer {
        void draw(World3d.Dot dot);
    }

    class TypicalDrawer implements Drawer {
        @Override
        public void draw(World3d.Dot dot) {
            fill(0, 181, 210);

            pushMatrix();
            translate(dot.getX() * CELL_SIZE + 100, dot.getY() * CELL_SIZE + 100, dot.getZ() * CELL_SIZE);
            //fill(0,0,255);
            box(CELL_SIZE);

            //rect(dot.getX() * CELL_SIZE, dot.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            popMatrix();
        }
    }


}