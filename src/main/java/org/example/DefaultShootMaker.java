package org.example;

import java.io.*;

public class DefaultShootMaker implements ShootMaker{

    public Process process;
    public BufferedReader input;
    public BufferedWriter output;

    private void executeCommand(String command) throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(this.process.getOutputStream()));
        out.write(command + "\n");
        out.flush();
        System.out.println(this.input.readLine());
    }

    public void connect() throws IOException {
        ProcessBuilder pb = new ProcessBuilder();
        pb.command("/bin/bash", "-c", "/usr/bin/chdkptp -i");
        this.process = pb.start();
        this.input = new BufferedReader( new InputStreamReader(process.getInputStream()) );
        this.output = new BufferedWriter(new OutputStreamWriter(this.process.getOutputStream()));
        this.executeCommand("connect");
    }

    public void makeShoot(String filepath) throws IOException{
        this.executeCommand("rec");
        this.executeCommand("remoteshoot -dng '" + filepath + "'");
    }
    public void destroy() throws IOException {
        this.executeCommand("disconnect");
        if(this.process != null){
            process.destroy();
        }
    }
}
