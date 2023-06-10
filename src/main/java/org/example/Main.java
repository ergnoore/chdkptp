package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class Main {

    public static final int FPS = 25;

    public static void main(String[] args) throws IOException, InterruptedException {
        if(args.length < 1){
            System.out.println(
                "The first argument must be a full path to directory with snapshots, then delays separated by a space. " +
                        "Example: ~/home/user/photos 1 2.2 3.4 4 5"
            );
            return;
        }
        String directoryPath = args[0];
        String[] strDelays = Arrays.stream(args, 1, args.length).toArray(String[]::new);
        double[] delays = Arrays.stream(strDelays).mapToDouble(Double::parseDouble).toArray();

//        String directoryPath = "/home/user/photos";
//        double[] delays = {0.5, 0.5, 1};

        // Опрделяем общее временя съемки.
        // Т.к gphoto2 - в начале делает черновой снимок, делаем поправки в количестве кадров и общей длительности.
        // С учетом FPS, рассчитываем число кадров для видео.
        double totalSeconds = Arrays.stream(delays).sum() + 1 / Main.FPS;
        int framesCount = (int) Math.round((totalSeconds * Main.FPS)) + 1;

        // Запускаем захват видео, и транслируем его в ffmpeg для разбиения по кадрам.
        ProcessBuilder pb = new ProcessBuilder();
        pb.command(
                "/bin/bash",
                "-c",
                "/usr/bin/gphoto2 --capture-movie " + framesCount + " --stdout |" +
                        " /usr/bin/ffmpeg -i - -vcodec mjpeg -f image2 " + directoryPath + "/image%03d.jpg");

        Process process = pb.start();
        StringBuilder output = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line);
        }

        int exitVal = process.waitFor();
        if (exitVal != 0){
            System.out.println("Error! ");
            System.out.println(exitVal);
            System.out.println(output);
        }


        Set<Integer> requiredFrames = new HashSet<>();

        // Подсчитываем номера нужных кадров, исходя из FPS и задержек между ними.
        List<Double> frameTimes = new ArrayList<>();
        double currentSum = 1 / Main.FPS;
        frameTimes.add(currentSum);
        for (double delay: delays) {
            currentSum += delay;
            frameTimes.add(currentSum);
        }

        for (double frameTime: frameTimes) {
            int frameNumber = (int)Math.floor(frameTime * Main.FPS);
            requiredFrames.add(frameNumber);
        }

        // Удаляем ненужные кадры.
        for (int i = 0; i < framesCount; i++) {
            if(requiredFrames.contains(i)){
                continue;
            }
            String numberStr = String.format("%03d", i + 1);
            File file = new File(directoryPath + "/image" + numberStr +".jpg");
            System.out.print("Deleting file: ");
            System.out.println(directoryPath + "/image" + numberStr +".jpg");
            file.deleteOnExit();
        }
    }
}
