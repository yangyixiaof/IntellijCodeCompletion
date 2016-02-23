package cn.yyx.contentassist.codepredict;

import java.util.ArrayList;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;
import org.eclipse.jface.text.IDocument;

import cn.yyx.contentassist.commonutils.ASTTreeReducer;
import cn.yyx.research.language.JDTHelper.ASTTraversal;

public class CodeNGramAnalyzer {

	@SuppressWarnings("unchecked")
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
			AbstractTypeDeclaration atype = ASTTreeReducer.GetSimplifiedContent(cu.types(), offset);
			
			System.err.println("Document:" + doc.get());
			System.err.println("========================== ==========================");
			System.err.println("RetainedDocument:" + atype.toString());
			
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return list;
	}
	
}