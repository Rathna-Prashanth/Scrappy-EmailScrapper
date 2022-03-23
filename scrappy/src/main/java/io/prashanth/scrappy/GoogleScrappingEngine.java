package io.prashanth.scrappy;

import io.prashanth.scrappy.extensions.ExcelRead;
import io.prashanth.scrappy.extensions.ExcelWrite;
import io.prashanth.scrappy.extensions.SMPTHandler;

import java.io.IOException;

public class GoogleScrappingEngine {

	public static boolean DriverInfo = false;

	public static void main(String[] args) throws InterruptedException, IOException {
//		PrintStream fileStream = new PrintStream("log.txt");
//		System.setOut(fileStream);
		AppEngine.appEngineStart();

		String[][] getNameNCompany = ExcelRead.GetNameNCompany();
		String name, firstName, lastname, company, companyFirst;

		System.out.println("Total Record Size: " + getNameNCompany.length);
		int a = Integer.parseInt(args[2]);
		int aa;
		if (args[3].equals("-d")) {
			aa = getNameNCompany.length;
			System.out.println("Switching Default");
		} else {
			aa = Integer.parseInt(args[3]);
			System.out.println("Stop ID: " + aa);
		}
		if (args[0].equals("-driver")) {
			DriverInfo = true;
			System.out.println("----------- Using Driver ----------");
		}
		try {
			for (int i = a; i < aa; i++) {
				name = getNameNCompany[i][1];
				company = getNameNCompany[i][2];
				try {
				String[] mName = name.split(" ");
				firstName = mName[0];
				lastname = mName[1];
				}catch (Exception e) {
					String[] mNameEx = name.split("(?=\\p{Upper})");
					firstName = mNameEx[0];
					lastname = mNameEx[1];
				}
				String[] mCompany = company.split(" ");
				companyFirst = mCompany[0];

				System.out.println(" Name: " + name + " Firstname: " + firstName
						+ " lastname:" + lastname + " company: " + company + " ComoanyFirst:" + companyFirst);
				System.out.println("                                                                       ");
				String[] filterModal = new ScrappingModal().filterModal(firstName, lastname,
						company, companyFirst);

				new ExcelWrite().WrtiteData(filterModal[0], filterModal[1], i);
			}

			System.out.println("====================================================");
			System.out.println("====================================================");
			System.out.println("============ Scrapping Completed ===================");
			System.out.println("====================================================");
			System.out.println("===============" + new java.util.Date() + "===============================");
			if (args[1].equals("-c")) {
				new SMPTHandler().getMail("NMLS Scrapping Completed", "System Name: "+AppEngine.config.getProperty("SystemName")+"\n Executor: "+AppEngine.config.getProperty("Executor")+"\n  Completed "+args[2]+"-"+args[3],true);
			} else {
				new SMPTHandler().getMail("NMLS Scrapping Completed", "System Name: "+AppEngine.config.getProperty("SystemName")+"\n Executor: "+AppEngine.config.getProperty("Executor")+"\n  Completed "+args[2]+"-"+args[3], false);
			}
			// fileStream.close();
		} catch (Exception e) {
			if (args[1].equals("-c")) {
				System.out.println(e);
				new SMPTHandler().getMail("Error!! NMLS Scrapping", "System Name: "+AppEngine.config.getProperty("SystemName")+"\n \n"+e.toString() + "   "
						+ e.getStackTrace()[0].getLineNumber(), true);
				// fileStream.close();
			} else {
				System.out.println(e);
				new SMPTHandler().getMail("Error!! NMLS Scrapping","System Name: "+AppEngine.config.getProperty("SystemName")+"\n \n"+
						e.toString() + "   " + e.getStackTrace()[0].getLineNumber(), false);
			}
		}
	}
}
