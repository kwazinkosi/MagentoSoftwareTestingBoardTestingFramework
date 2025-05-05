package constants;

import utils.ConfigReader;

/**
 * This class defines various wait times used in the application. The wait times
 * are read from a configuration file. The class is designed to be
 * non-instantiable.
 */
public final class WaitTime {

	public static final int SLOW = Integer.parseInt(ConfigReader.getProperty("slow_wait_time"));
	public static final int NORMAL = Integer.parseInt(ConfigReader.getProperty("normal_wait_time"));
	public static final int FAST = Integer.parseInt(ConfigReader.getProperty("fast_wait_time"));
	public static final int FASTER = Integer.parseInt(ConfigReader.getProperty("faster_wait_time"));

	private WaitTime() {} // Prevent instantiation
}