package org.dptech.wx.sdk.util;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class FileUtil {

	public static void writeToFile(String file, byte[] bytes) throws IOException{
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			os.write(bytes);
			os.flush();
		} finally {
			if(os != null)
				os.close();
		}
	}
	
	public static void writeToFile(String file, String content, String charset) throws UnsupportedEncodingException, IOException{
		writeToFile(file, content.getBytes(charset));
	}
	
	public static void writeToFile(String file, List<String> lineContents) throws IOException{
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(file));
			int length = lineContents.size();
			for(int i = 0; i < length; i++){
				out.write(lineContents.get(i));
				if(i < length - 1){
					out.newLine();
				}
			}
			out.flush();
		} finally {
			if(out != null){
				out.close();
			}
		}
		
	}
	
}
