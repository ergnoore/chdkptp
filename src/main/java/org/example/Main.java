package org.example;
import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void makeShootSeries(ShootMaker shootMaker, double[] delays, String filepath) throws IOException, InterruptedException {
        for (double delay: delays){
            shootMaker.makeShoot(filepath);
            System.out.println("Make shoot");
            System.out.println("Sleep: " + delay);
            Thread.sleep((long) delay * 1000L);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        if(args.length < 1){
            System.out.println(
                "The first argument must be a directory with snapshots, then delays separated by a space. " +
                        "Example: ~/photos 1 2.2 3.4 4 5"
            );
            return;
        }
        String directoryPath = args[0];
        String[] strDelays = Arrays.stream(args, 1, args.length).toArray(String[]::new);
        double[] delays = Arrays.stream(strDelays).mapToDouble(Double::parseDouble).toArray();

        DefaultShootMaker defaultShootMaker = new DefaultShootMaker();
        defaultShootMaker.connect();
        makeShootSeries(defaultShootMaker, delays, directoryPath);
        defaultShootMaker.destroy();

    }
}
