package com;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class FileCSV {
	
public static String fileLocation = "C:/Temp/inputFile.cvs"; 

public static void writeToCVS(ArrayList<String> array) {
	
	try {

        File file = new  File(fileLocation);

        if (!file.exists()) {file.createNewFile();}

        FileWriter fw = new FileWriter(file.getAbsoluteFile()); 
        BufferedWriter buf = new BufferedWriter(fw);       
        Iterator<String> it = array.iterator();
        
        while (it.hasNext()) {   	
        	buf.write(it.next());
        	buf.newLine();
        }

        buf.close();

    } catch (IOException e) {
        e.printStackTrace();
    }
}	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
