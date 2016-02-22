package cn.yyx.contentassist.codepredict;

import java.util.ArrayList;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;

public class CodeNGramAnalyzer {

	public static ArrayList<String> PossibleCodes(JavaContentAssistInvocationContext javacontext, int offset) {
		// TODO Auto-generated method stub
		ArrayList<String> list = new ArrayList<String>();
		try {
			ICompilationUnit cu = javacontext.getCompilationUnit();
			IJavaElement[] childs = cu.getChildren();
			for (IJavaElement child : childs)
			{
				System.out.println("ElementName:"+child.toString());
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return list;
	}
	
}