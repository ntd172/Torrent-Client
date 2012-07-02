package bittorrent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Merge {
	public static void main(String[] args) throws IOException { 
		int size = 179;
		long total = 0;
		long totalRead = 0;
		byte[] buffer = new byte[1000];
		DataOutputStream out = new DataOutputStream(new FileOutputStream("data/music.mp3"));
		for (int i = 0; i <= 178; i++) { 
			int length = 4;
			if (i == 178) { 
				length = 1;
			}
			for (int j = 0; j < length ; j++) { 
				File file = new File("data/" + i + "_" + j);
				total += file.length();
				DataInputStream in = new DataInputStream(new FileInputStream(file));
				int read = 0;
				while ((read = in.read(buffer)) != -1) { 
					totalRead += read;
					out.write(buffer, 0, read);
				}
				out.flush();
				in.close();
			}
		}
		System.out.println(total);
		System.out.println(totalRead);
		System.out.println(new File("data/music.mp3").length());
		out.flush();
		out.close();
	}
}
