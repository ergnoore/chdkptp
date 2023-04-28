package org.example;

import java.io.IOException;

public class Main {

    public static final int[] DELAYS = {1, 1};
    public static final String PATH = "/home/ergnoore";

    public static void makeShootSeries(ShootMaker shootMaker, int[] delays, String filepath) throws IOException, InterruptedException {
        for (int delay: delays){
            shootMaker.makeShoot(filepath);
            System.out.println("Make shoot");
            System.out.println("Sleep: " + delay);
            Thread.sleep(delay * 1000L);
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        DefaultShootMaker defaultShootMaker = new DefaultShootMaker();

        defaultShootMaker.connect();
        makeShootSeries(defaultShootMaker, DELAYS, PATH);
        defaultShootMaker.destroy();
    }
}
