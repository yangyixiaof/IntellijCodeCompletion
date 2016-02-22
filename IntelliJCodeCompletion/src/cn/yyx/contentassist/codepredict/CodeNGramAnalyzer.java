package cn.yyx.contentassist.codepredict;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;
import org.eclipse.jface.text.IDocument;

import cn.yyx.research.language.JDTHelper.ASTTraversal;

public class CodeNGramAnalyzer {

	public static ArrayList<String> PossibleCodes(JavaContentAssistInvocationContext javacontext) {
		// TODO Auto-generated method stub
		System.err.println("HaHa Test!!!!!!!!!!!!!!");
		ArrayList<String> list = new ArrayList<String>();
		try {
			int offset = javacontext.getInvocationOffset();
			
			ICompilationUnit icu = javacontext.getCompilationUnit();
			String javaname = icu.getCorrespondingResource().getName();
			
			IDocument doc = javacontext.getDocument();
			ASTTraversal astmdf = new ASTTraversal(javaname, doc.get());
			CompilationUnit cu = astmdf.getCompilationUnit();
			List<AbstractTypeDeclaration> types = cu.types();
			for (AbstractTypeDeclaration at : types)
			{
				int start = at.getStartPosition();
				int end = start + at.getLength();
				System.out.println("type:"+at.getName().toString()+";start pos:"+start+";endpos:"+end);
			}
			
			System.err.println("Document:" + doc.get());
			
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return list;
	}
	
}