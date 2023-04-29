# _CHDKPTP-MAKESHOOTER_

### Шаг 1. Установка CHDK
Для установки на chdk на фотоаппарат, необходимо:

+ Загрузить совместимую версию пришивки;
+ Отформатировать CD карту;
+ Поместить файлы прошивки на карту;
+ Вставить карту в фотоаппарат;
+ В режиме _play_ зайти в настройки и выбрать _Update Firmware_;
+ Выключть камеру;
+ Перевести карту в режим _Lock_ механическим способом;

Подробнее [здесь]("https://chdk.fandom.com/wiki/Prepare_your_SD_card") (Method 3 - Using EOSCard to make the SD card bootable).

###  Шаг 2. Установка chdkptp.
```bash
yay -S chdkptp
```
###  Шаг 3. Снимок из командой оболочки.
Для того чтобы сделать фото с помощью камеры, подключенной по _USB_, с помощью утилиты _chdkptp_, необходимо выполнить команду:
```bash
chdkptp -c -e"remoteshoot -dng '<path>'"
```
Где -с - подключение к камере, a -e указывает, какую команду необходимо выполнить.
Так же можно использовать интерактивную оболочку.

```bash
chdkptp -i
___> connect
___> rec
___> remoteshoot -dng '<path>'
```

## Запуск и сборка.
```bash
[chdk]$ mvn compile exec:java -Dexec.mainClass="org.example.Main" -Dexec.args="~/ 1.1 10"
```
