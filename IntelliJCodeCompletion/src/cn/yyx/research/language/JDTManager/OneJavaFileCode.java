package cn.yyx.research.language.JDTManager;

import cn.yyx.research.language.simplified.JDTManager.JavaCode;

public class OneJavaFileCode implements JavaCode{
	
	StringBuilder sb = new StringBuilder("");
	
	public OneJavaFileCode() {
	}
	
	public void AddOneMethodNodeCode(NodeCode nc)
	{
		AddOneNodeCode(nc, sb);
	}
	
	public void OneSentenceEnd()
	{
		/*System.err.println(sb);
		if (sb.length() == 0 || sb.charAt(sb.length()-1) != '.')
		{
			sb.append(".");
		}*/
	}
	
	@Override
	public String toString() {
		return sb.toString();
	}

	@Override
	public boolean IsEmpty() {
		return sb.length() == 0;
	}
	
}