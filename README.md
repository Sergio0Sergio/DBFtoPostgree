 DBFtoPostgre
 ==================
 
 Программа для копирования данных из файлов .dbf в БД PostgreSQL.
 ---------------------------------------------
 
 
 ### Назначение и процесс работы.
 
 Программа предназначена для копирования данных из файлов .dbf в БД PostgreSQL. Программа получает путь до файла .dbf,
 имя БД, имя таблицы в БД, адрес сервера БД, порт сервера БД, Имя пользователя БД, пароль пользователя БД в качестве
 параметров командной строки и начинает процедуру копирования данных, после чего необходимо дождаться окончания работы
 программы, о котором она уведомит сообщением.
 
 ### Сборка и компиляция.
 
 Исходный код программы представлен в виде проекта Maven. Необходимо учесть, что в проекте используется библиотека DbfEngine, не входящая
 в главный репозиторий Maven. Ссылка на сайт библиотеки: http://www.smart-flex.ru/htm/lgpl_info.shtml. Так же библиотека помещена 
 в этот проект и находится в репозитории git в папке /my-repository. Перед сборкой и компиляцией проекта необходимо добавить библиотеку к проекту. Один из 
 способов это сделать - добавить библиотеку в локальный репозиторий Maven, используя команду
 ```
 mvn install:install-file -Dfile=<path-to-file> -DgroupId=<group-id> -DartifactId=<artifact-id> -Dversion=<version> -Dpackaging=<packaging>
 ```
 В нашем случае команда будет выглядеть так:
 ```
 mvn install:install-file -Dfile=Engine_bin-1.08.jar -DgroupId=smart-flex  -DartifactId=DbfEngine -Dversion=1.08 -Dpackaging=jar
 ```
 После подключения библиотеки, можно провести стандартную процедуру сборки проекта Maven. Для упрощения задачи можно запустить файл 
 assembly.bat для Windows или assembly.sh для Linux, после чего будет запущена процедура сборки проекта, по окончании которой файл
 DBFtoPostgre.jar появится в корневой папке проекта.
 
 ### Использование программы.
 
 ```
 usage: DBFtoPostgre.jar [-d <db name>] [-f <path to .dbf file>] [-h <db server adress>] 
 [-p <server port>] [-t <table name>] [-u <user name>] [-w <db password>]
 -d,--database <db name>             database
 -f,--filename <path to .dbf file>   file
 -h,--host <db server adress>        host
 -p,--port <server port>             port
 -t,--table <table name>             table
 -u,--user <user name>               user
 -w,--password <db password>         password
```
параметры  -t, -d, -f являются обязательными, остальные параметры имеют значения по умолчанию:

```
-h = "127.0.0.1"
-p = "5432"
-u = "postgres"
-w = "" (пустая строка)
```  
Пример:
```
java DBFtoPostgre.jar -d test -t "public.\"HOUSE\" -f D:\fias\HOUSE01.DBF
```
