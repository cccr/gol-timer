package com.halfofpoint.gol;

import com.halfofpoint.gol.World;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class Test01 {

    @Test
    public void setDot() {
        Integer x = 3;
        Integer y = 5;

        World world = new World();
        world.newDot(x, y);

        World.Dot dot = world.getDots().get(0);

        assertEquals(x, dot.getX());
        assertEquals(y, dot.getY());
        assertNotEquals(x, dot.getY());
    }

    @Test
    public void dotCount() {
        World world = new World();

        world.newDot(0, 0);
        world.newDot(0, 1);
        world.newDot(0, 2);
        world.newDot(0, 0);

        assertEquals(3, world.getDots().size());
    }

    @Test
    public void findNeighborhoods() {
        World world = new World();

        world.newDot(0, 0);
        World.Dot dot = world.newDot(0, 1);
        world.newDot(0, 2);
        world.newDot(5, 7);

        assertEquals(2, world.findNeighborhoods(dot).size());
    }


    @Test
    public void tick() {
        World world = new World();

        world.newDot(0, 0);
        world.newDot(0, 1);
        world.newDot(0, 2);
        world.newDot(0, 3);
        world.newDot(5, 7);

        world.print();
        world.tick();
        world.print();

        assertEquals(4, world.getDots().size());
    }

    @Test
    public void threeTicks() {
        World world = new World();

        world.newDot(0, 0);
        world.newDot(0, 1);
        world.newDot(0, 2);
        world.newDot(0, 3);
        world.newDot(2, 0);
        world.newDot(2, 1);
        world.newDot(2, 2);
        world.newDot(5, 7);
        world.tick();
        world.tick();
        world.tick();

        assertEquals(2, world.getDots().size());
    }

    @Test
    public void testBorders() {
        World world = new World();

        world.newDot(0, 11);
        world.newDot(10, 10);

        assertEquals(0, world.getDots().size());
    }

    @Test
    public void infinity() {
        World world = new World(50, 30);
        world.addDot(27, 3)
                .addDot(27, 4)
                //
                .addDot(24, 5)
                .addDot(23, 5)
                .addDot(24, 6)
                .addDot(23, 6)
                .addDot(24, 7)
                .addDot(23, 7)
                //
                .addDot(25, 4)
                .addDot(25, 8)
                //
                .addDot(27, 8)
                .addDot(27, 9)
                //
                .addDot(37, 5)
                .addDot(37, 6)
                .addDot(38, 5)
                .addDot(38, 6)
                .addDot(3, 7)
                .addDot(3, 8)
                .addDot(4, 7)
                .addDot(4, 8)
                .addDot(13, 7)
                .addDot(13, 8)
                .addDot(13, 9)
                .addDot(19, 7)
                .addDot(19, 8)
                .addDot(19, 9)
                .addDot(20, 8)
                .addDot(17, 8)
                .addDot(18, 6)
                .addDot(18, 10)
                .addDot(15, 5)
                .addDot(16, 5)
                .addDot(14, 6)
                .addDot(14, 10)
                .addDot(15, 11)
                .addDot(16, 11);

        for (int i = 0; i < 200; i++)
            world.tick();

        assertTrue(world.getDots().size() > 10);

    }
}
