package cn.yyx.contentassist.codepredict;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeMemberDeclaration;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;
import org.eclipse.jface.text.IDocument;

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
			
			
			System.err.println("Document:" + doc.get());
			
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	private String GetSimplifiedContent(List<AbstractTypeDeclaration> types, int offset, String doccnt)
	{
		for (AbstractTypeDeclaration at : types)
		{
			int tstart = at.getStartPosition();
			int tend = tstart + at.getLength();
			if (offset >= tstart && offset < tend)
			{
				List<BodyDeclaration> bds = at.bodyDeclarations();
				return OneClassNecessaryContentRetain(bds, offset, at, doccnt);
			}
			// System.out.println("type:"+at.getName().toString()+";start pos:"+start+";endpos:"+end+";invokeoffset:"+offset);
		}
		return null;
	}
	
	private String OneClassNecessaryContentRetain(List<BodyDeclaration> bds, int offset, AbstractTypeDeclaration parent, String doccnt)
	{
		Iterator<BodyDeclaration> itr2 = bds.iterator();
		boolean onlyRetainField = false;
		Object retainRef = null;
		String retainCnt = null;
		while (itr2.hasNext())
		{
			BodyDeclaration bd = itr2.next();
			if (offset < bd.getStartPosition())
			{
				// field.
				onlyRetainField = true;
				break;
			}
			if (offset < bd.getStartPosition() + bd.getLength())
			{
				retainRef = bd;
				// be caught.
				if (IsRawBody(bd))
				{
					// consider offset position in anonymous class declaration.
					if (!IsRawBlockBody(bd))
					{
						System.err.println("Not supportted completion type.");
						return null;
					}
					retainCnt = DetailClearAfterOffset(bd.toString(), offset - bd.getStartPosition());
				}
				else
				{
					retainCnt = OneClassNecessaryContentRetain(bds, offset, parent, doccnt);
				}
				break;
			}
		}
		StringBuffer sb = new StringBuffer("");
		boolean first = true;
		Iterator<BodyDeclaration> itr = bds.iterator();
		while (itr.hasNext())
		{
			BodyDeclaration bd = itr.next();
			if (first)
			{
				sb.append(doccnt.substring(parent.getStartPosition(), bd.getStartPosition()));
				first = false;
			}
			if (bd instanceof AnnotationTypeMemberDeclaration || bd instanceof EnumConstantDeclaration || bd instanceof FieldDeclaration)
			{
				sb.append(doccnt.substring(bd.getStartPosition(), bd.getStartPosition() + bd.getLength()));
			}
			if (!onlyRetainField && (bd == retainRef))
			{
				sb.append(retainCnt);
			}
			if (!itr.hasNext())
			{
				sb.append(doccnt.substring(bd.getStartPosition() + bd.getLength(), parent.getStartPosition() + parent.getLength()));
			}
		}
		return sb.toString();
	}
	
	private String DetailClearAfterOffset(String tempcnt, int tempoffset) {
		// TODO Auto-generated method stub
		// TODO Try delete directly tomorrow.
		return null;
	}

	private boolean IsRawBody(BodyDeclaration bd)
	{
		if (bd instanceof AnnotationTypeMemberDeclaration || bd instanceof EnumConstantDeclaration || bd instanceof FieldDeclaration || bd instanceof Initializer || bd instanceof MethodDeclaration)
		{
			return true;
		}
		return false;
	}
	
	private boolean IsRawBlockBody(BodyDeclaration bd)
	{
		if (bd instanceof Initializer || bd instanceof MethodDeclaration)
		{
			return true;
		}
		return false;
	}
	
}