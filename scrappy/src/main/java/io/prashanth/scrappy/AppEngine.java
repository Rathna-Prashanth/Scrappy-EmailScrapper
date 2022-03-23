
package io.prashanth.scrappy;

import io.prashanth.scrappy.extensions.ExcelRead;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class AppEngine {
	
	public static Properties config;
	public static int AStart,AStop,BStart,BStop,CStart,CStop;
	public static String[] eMailsIDs= null  ;

	public static void appEngineStart() throws IOException {
		FileReader reader = null;
		try {
			reader = new FileReader(
					System.getProperty("user.dir") + "/App.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		config = new Properties();
		try {
			config.load(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		AStart = Integer.parseInt(config.getProperty("Core1.Start"));
		AStop = Integer.parseInt(config.getProperty("Core1.Stop"));
		BStart = Integer.parseInt(config.getProperty("Core2.Start"));
		BStop = Integer.parseInt(config.getProperty("Core2.Stop"));
		CStart = Integer.parseInt(config.getProperty("Core3.Start"));
		CStop = Integer.parseInt(config.getProperty("Core3.Stop"));
		System.out.println("--- NMLS WEB SCRAPPING Engine Started ---");
		System.out.println(new java.util.Date());
		
		System.out.println("Core1 Start :"+AStart+"  "+"Stop :"+AStop);
		System.out.println("Core2 Start :"+BStart+"  "+"Stop :"+BStop);
		System.out.println("Core3 Start :"+CStart+"  "+"Stop :"+CStop);
		System.out.println("Sheet Name :"+config.getProperty("SheetName"));
		eMailsIDs = ExcelRead.GetExcelEmailIDs();
		
//		System.out.println("===============================================================================");
//		for (String id : eMailsIDs) {
//			System.out.println(id);
//		}
//		System.out.println("===============================================================================");
//		
	}

}
