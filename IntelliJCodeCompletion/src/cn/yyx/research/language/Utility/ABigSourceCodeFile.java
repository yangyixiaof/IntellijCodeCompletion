package cn.yyx.research.language.Utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ABigSourceCodeFile {
	
	String filename = null;
	File f = null;
	FileWriter writer = null;
	
	public ABigSourceCodeFile(File pf) {
		filename = pf.getAbsolutePath();
		f = pf;
	}
	
	/*public ABigSourceCodeFile(String prefixname) {
		filename = prefixname+"_"+"ABigSourceCodeFile.txt";
		f = new File(filename);
		if (!f.exists())
		{
			try {
				f.createNewFile();
				System.out.println("Create file:"+f.getName()+" succeeded.");
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Create file:"+f.getName()+" failed!!!!");
				System.exit(1);
			}
		}
	}*/
	
	public void Begin() throws IOException
	{
		writer = new FileWriter(filename, true);
	}

	public void appendContent(String content) {
		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			writer.write(content);
		} catch (IOException e) {
			e.printStackTrace();
			End();
			System.err.println("write to big file error!");
			System.exit(1);
		}
	}
	
	public void End()
	{
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}