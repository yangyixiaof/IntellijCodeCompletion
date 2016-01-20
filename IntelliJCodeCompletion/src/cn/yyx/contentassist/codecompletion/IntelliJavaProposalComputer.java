package cn.yyx.contentassist.codecompletion;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.ui.text.java.ContentAssistInvocationContext;
import org.eclipse.jdt.ui.text.java.IJavaCompletionProposalComputer;
import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;

import cn.yyx.contentassist.specification.SearchSpecificationOfAReference;

public class IntelliJavaProposalComputer implements IJavaCompletionProposalComputer {
	
	public final static String OnlyJavaSupport = "<Advanced Completion: Only support java code completion, other formats are not supported yet.>";
	public final static String OnlyExpressionSupport = "<Advanced Completion: Only support expressions completions, other formats are not supported yet.>";
	
	public IntelliJavaProposalComputer() {
	}

	@Override
	public List<ICompletionProposal> computeCompletionProposals(ContentAssistInvocationContext context,
			IProgressMonitor monitor) {
		ArrayList<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();
		proposals.add(new CompletionProposal("www.yyx.com", context.getInvocationOffset(), 0, "www.yyx.com".length()));
		proposals.add(new CompletionProposal("<yyx proposal here>", context.getInvocationOffset(), 0,
				"<yyx proposal here>".length()));
		if (context instanceof JavaContentAssistInvocationContext)
		{
			try {
				JavaContentAssistInvocationContext javacontext = (JavaContentAssistInvocationContext)context;
				IDocument doc = javacontext.getDocument();
				int offset = javacontext.getInvocationOffset();
				String precontentraw = doc.get(0, offset);
				String precontent = precontentraw.trim();
				char lastchar = precontent.charAt(precontent.length()-1);
				if (lastchar == ';' || lastchar == '}' || lastchar == ',' || lastchar == '{' || lastchar == '(' || lastchar == ':')
				{
					// String rawaddedtext = "System.out." + ConstantVariable.LineSeperator;
					// doc.set(precontentraw + rawaddedtext + postcontentraw);
					// System.err.println("jcc: " + javacontext.getDocument().get());
					// ICompilationUnit sourceunit = javacontext.getCompilationUnit();
					// System.out.println("sourceunit type: " + sourceunit.getClass());
					SearchSpecificationOfAReference.SearchFunctionSpecificationByPrefix("System.", javacontext, monitor);
				}
				else
				{
					proposals.add(new CompletionProposal(OnlyExpressionSupport, context.getInvocationOffset(), 0,
							OnlyExpressionSupport.length()));
				}
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
		else
		{
			System.err.println(OnlyJavaSupport);
			proposals.add(new CompletionProposal(OnlyJavaSupport, context.getInvocationOffset(), 0,
					OnlyJavaSupport.length()));
		}
		return proposals;
	}

	@Override
	public List<IContextInformation> computeContextInformation(ContentAssistInvocationContext context,
			IProgressMonitor monitor) {
		return null;
	}

	@Override
	public String getErrorMessage() {
		return "I don't really know the reason.";
	}

	@Override
	public void sessionEnded() {
		//System.err.println("YyxContentAssitEndInvoked");
	}

	@Override
	public void sessionStarted() {
		//System.err.println("YyxContentAssitBeginToInvoke");
	}

}