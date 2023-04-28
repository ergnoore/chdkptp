# _CHDKPTP-MAKESHOOTER_

Для того чтобы сделать фото с помощью камеры, подключенной по _USB_, с помощью утилиты _chdkptp_, необходимо выполнить команду:
```bash
chdkptp -c -e"remoteshoot -dng '<path>'"
```

Где -с - подключение к камере, a -e указывает, какую команду необходимо выполнить.

# Реализация
```java
public class DefaultShootMaker implements ShootMaker{

    public Process process;
    public BufferedReader input;
    public BufferedWriter output;

    private void executeCommand(String command) throws IOException {
        // Отправляем в stdin процесса команду.
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(this.process.getOutputStream()));
        out.write(command + "\n");
        out.flush();
        System.out.println(this.input.readLine());
    }

    public void connect() throws IOException {
        ProcessBuilder pb = new ProcessBuilder();
        // Вызываем утилиту chdkptp в режиме интерактивной оболчки.
        pb.command("/bin/bash", "-c", "/usr/bin/chdkptp -i");
        this.process = pb.start();
        this.input = new BufferedReader( new InputStreamReader(process.getInputStream()) );
        this.output = new BufferedWriter(new OutputStreamWriter(this.process.getOutputStream()));
        this.executeCommand("connect");
    }

    public void makeShoot(String filepath) throws IOException{
        // Переводим камеру в режим записи.
        this.executeCommand("rec");
        // Делаем снимок.
        this.executeCommand("remoteshoot -dng '" + filepath + "'");
    }
    public void destroy() throws IOException {
        this.executeCommand("disconnect");
        if(this.process != null){
            process.destroy();
        }
    }
}
```