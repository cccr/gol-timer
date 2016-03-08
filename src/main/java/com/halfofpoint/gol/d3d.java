package com.halfofpoint.gol;

import processing.core.PApplet;
    import peasy.*;

/**
 * Created by mart on 14/11/15.
 */
public class d3d extends PApplet {


    PeasyCam cam;

    public static void main(String args[]) {
        PApplet.main(new String[]{"--present", "com.halfofpoint.gol.d3d"});
    }


    public void settings() {
        size(200, 200, P3D);
    }

    public void setup() {
        cam = new PeasyCam(this, 100);
        cam.setMinimumDistance(50);
        cam.setMaximumDistance(500);
    }

    public void draw() {
        rotateX((float) -0.5);
        rotateY((float) -0.5);
        background(0);
        fill(255,0,0);
        box(30);
        pushMatrix();
        translate(0,0,20);
        fill(0,0,255);
        box(5);
        popMatrix();
    }

}
