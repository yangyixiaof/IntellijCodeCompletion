package cn.yyx.contentassist.commonutils;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;

public class DetailedASTTreeReducerVisitor extends ASTVisitor{
	
	int offset = -1;
	
	public DetailedASTTreeReducerVisitor(int offset) {
		this.offset = offset;
	}
	
	@Override
	public void preVisit(ASTNode node) {
		if (offset < node.getStartPosition())
		{
			node.delete();
		}
	}
	
}