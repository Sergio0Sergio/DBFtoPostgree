package ru.habrahabr.sergiosergio.DBFtoPostgree;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.sound.sampled.Line;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Hello world!
 *
 */
public class App {

	public static void main(String[] args) {

		BlockingQueue<String> buf;
		String bdServerAdress = "127.0.0.1";
		String bdServerPort = "5432";
		String bdName;
		String tableName;
		String bdUserName = "postgres";
		String bdPassword = "";
		String filePath;

		Option bdServerAdressKey = new Option("-h", true, "Адрес сервера (по умолчанию 127.0.0.1)");
		Option bdServerPortKey = new Option("-p", true, "Порт сервера (по умолчанию 5432)");
		Option bdNameKey = new Option("-d", true, "Имя БД (Обязательный параметр)");
		Option tableNameKey = new Option("-t", true, "Имя таблицы в БД (Обязательный параметр)");
		Option bdUserNameKey = new Option("-u", true, "Имя пользователя (по умолчанию \"postgres\")");
		Option bdPasswordKey = new Option("-w", true, "Адрес сервера (по умолчанию 127.0.0.1)");
		Option filePathKey = new Option("-f", true, "Путь к файлу .dbf (Обязательный параметр)");

		Options options = new Options();

		options.addOption(bdServerAdressKey);
		options.addOption(bdServerPortKey);
		options.addOption(bdNameKey);
		options.addOption(tableNameKey);
		options.addOption(bdUserNameKey);
		options.addOption(bdPasswordKey);
		options.addOption(filePathKey);

		CommandLineParser parser = new DefaultParser();
		
		try {
			CommandLine line = parser.parse(options, args);
		} catch (ParseException e) {

			System.out.println("Невозможно прочитать параметры командной строки");
			System.out.println("Работа программы завершена");
			e.printStackTrace();
			System.exit(1);
		}
		
		if () {
			
		}
		
		
			
		}

	buf=new ArrayBlockingQueue<String>(1000,false);

}}
