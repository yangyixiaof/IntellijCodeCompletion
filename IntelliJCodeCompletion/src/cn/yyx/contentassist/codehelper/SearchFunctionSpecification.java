package cn.yyx.contentassist.codehelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.CompletionProposal;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.internal.codeassist.CompletionEngine;
import org.eclipse.jdt.internal.core.DefaultWorkingCopyOwner;
import org.eclipse.jdt.internal.core.JavaProject;
import org.eclipse.jdt.internal.core.SearchableEnvironment;
import org.eclipse.jdt.ui.text.java.CompletionProposalCollector;
import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;
import org.eclipse.jface.text.contentassist.ICompletionProposal;

@SuppressWarnings("restriction")
public class SearchFunctionSpecification {
	
	public static void SearchFunctionSpecificationByPrefix(String prefix, JavaContentAssistInvocationContext javacontext, IProgressMonitor monitor)
	{
		/*
		 * the prefix must be as the following form:
		 * <form:System.out.>
		 */
		CompletionProposalCollector collector = new CompletionProposalCollector(javacontext.getCompilationUnit(), true);
		collector.setInvocationContext(javacontext);

		// Allow completions for unresolved types - since 3.3
		collector.setAllowsRequiredProposals(CompletionProposal.FIELD_REF, CompletionProposal.TYPE_REF, true);
		collector.setAllowsRequiredProposals(CompletionProposal.FIELD_REF, CompletionProposal.TYPE_IMPORT, true);
		collector.setAllowsRequiredProposals(CompletionProposal.FIELD_REF, CompletionProposal.FIELD_IMPORT, true);

		collector.setAllowsRequiredProposals(CompletionProposal.METHOD_REF, CompletionProposal.TYPE_REF, true);
		collector.setAllowsRequiredProposals(CompletionProposal.METHOD_REF, CompletionProposal.TYPE_IMPORT, true);
		collector.setAllowsRequiredProposals(CompletionProposal.METHOD_REF, CompletionProposal.METHOD_IMPORT, true);

		collector.setAllowsRequiredProposals(CompletionProposal.CONSTRUCTOR_INVOCATION, CompletionProposal.TYPE_REF, true);

		collector.setAllowsRequiredProposals(CompletionProposal.ANONYMOUS_CLASS_CONSTRUCTOR_INVOCATION, CompletionProposal.TYPE_REF, true);
		collector.setAllowsRequiredProposals(CompletionProposal.ANONYMOUS_CLASS_DECLARATION, CompletionProposal.TYPE_REF, true);

		collector.setAllowsRequiredProposals(CompletionProposal.TYPE_REF, CompletionProposal.TYPE_REF, true);
		
		int rawoffset = javacontext.getInvocationOffset();
		int position = rawoffset + prefix.length();
		
		DefaultWorkingCopyOwner owner = DefaultWorkingCopyOwner.PRIMARY;
		
		try {
			ICompilationUnit sourceunit = javacontext.getCompilationUnit();
			MyCompilationUnit mcu = new MyCompilationUnit((org.eclipse.jdt.internal.compiler.env.ICompilationUnit)sourceunit, javacontext.getDocument(), prefix, rawoffset);
			JavaProject project = (JavaProject) sourceunit.getJavaProject();
			SearchableEnvironment environment = project.newSearchableNameEnvironment(owner);
			// code complete
			CompletionEngine engine = new CompletionEngine(environment, collector, project.getOptions(true), project, owner, null);
			engine.complete(mcu, position, 0, sourceunit);
			// TODO
		} catch (Exception x) {
			x.printStackTrace();
		}

		ICompletionProposal[] javaProposals= collector.getJavaCompletionProposals();

		List<ICompletionProposal> proposals= new ArrayList<ICompletionProposal>(Arrays.asList(javaProposals));
		if (proposals.size() == 0) {
			String error= collector.getErrorMessage();
			if (error.length() > 0)
				new Exception().printStackTrace();
		}
		
		// testing
		// TODO
		System.err.println("start print proposals. proposals length:" + proposals.size());
		Iterator<ICompletionProposal> itr = proposals.iterator();
		while (itr.hasNext())
		{
			System.err.println("proposals: " + itr.next().toString());
		}
		
		// return proposals;
	}
	
}