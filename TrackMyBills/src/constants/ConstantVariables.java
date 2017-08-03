package constants;

import java.io.File;

/**
 * Constant variables for referencing in other classes. Consists of the AppData folder and the TrackMyBills folder in the AppData folder.
 * 
 * @author Ryan Caldwell
 * @version Version 1, 2-AUG-2017
 */
public class ConstantVariables {
	final public static File APP_DATA_FILE = new File(System.getenv("APPDATA"));
	final public static File TRACK_MY_BILLS_FOLDER = new File(System.getenv("APPDATA") + "/TrackMyBills");
	final public static String APP_DATA_STRING = System.getenv("APPDATA");
	final public static String TRACK_MY_BILLS_FOLDER_STRING = System.getenv("APPDATA") + "/TrackMyBills";
}
