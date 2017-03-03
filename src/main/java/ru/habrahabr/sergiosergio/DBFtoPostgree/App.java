package ru.habrahabr.sergiosergio.DBFtoPostgree;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {

	String path = args[0];
	BlockingQueue<String> copyBuffer = new ArrayBlockingQueue<String>(1000, false);

    }
}
