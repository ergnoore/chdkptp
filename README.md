# _MAKESHOOTER_

### Зависимости
+ ffmpeg (n6.0);
+ gphoto2 (2.5.28);
+ libgphoto2 (2.5.30);
+ libgphoto2_port (0.12.1);

```bash
$ pacman -S ffmpeg gphoto2 libgphoto2
```

###  Описание работы.
Даже если у камеры есть режим *Серийной съемки* без настройки _eosremoterelease_,
реализовать удаленную съемку не представляется возможным.

Для обхода данной особенности камеры, можно использовать не режим *Серийной съемки*,
а запись видео, с последующей его обработкой.

Алгоритм действий:
+ Определение длительности съемки;
+ Производится потоковый захват видео с камеры в формате mjpeg;
+ Произодится разделение видео на кадры с помощью ffmpeg;
+ Удаление ненужных кадров;


## Быстрый запуск.
```bash
[chdkptp]$ mvn compile exec:java -Dexec.mainClass="org.example.Main" -Dexec.args="/home/ergnoore/f 0.1 0.1 0.5 1"
```

## Быстрый запуск.
```bash
[chdkptp]$ mvn clean install
[chdkptp]$ mvn dependency:copy-dependencies
[chdkptp]$ java -cp target/chdk-1.0-SNAPSHOT.jar:dependency org.example.Main /home/ergnoore/f 0.1 0.1 0.5 1
```
