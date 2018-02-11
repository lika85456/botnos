package nostale;

import java.util.Arrays;
import java.util.HashMap;

import nostale.data.AccountData;
import nostale.resources.FileLoader;

public class Config {
	//md5(nostalex.dat)+md5(nostale.dat)
	public static String HASH = "F2877F880E2B94F260D6FD27B08688FF3955B30822A19DD3FA564472DD64208B";
	public static String version = "0.9.3.3082";
	
	public static void load()
	{
		String[] file = FileLoader.loadFile("config.ini");
		for(int i = 0;i<file.length;i++)
		{
			String line = file[i];
			String[] currentLine = line.split("\\s+");
			switch(currentLine[0])
			{
			//TODO add some config.ini params
			}
		}
	}
	
	public static HashMap<String,AccountData> loadAccounts()
	{
		HashMap<String,AccountData> toRet = new HashMap<String,AccountData>();
		String[] file = FileLoader.loadFile("accounts");
		for(int i = 0;i<file.length-1;i+=7)
		{
			AccountData n = AccountData.fromString(Arrays.copyOfRange(file, i, i+7));
			toRet.put(n.name,n);
		}
		return toRet;
	}
}
