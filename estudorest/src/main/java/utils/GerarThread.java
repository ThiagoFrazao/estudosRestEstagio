package utils;

public class GerarThread {	
	public static void run(Runnable run) {
		new Thread(run).start();
	}
}
