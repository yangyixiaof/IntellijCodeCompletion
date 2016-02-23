package cn.yyx.contentassist.commonutils;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;

public class DetailedASTTreeReducerVisitor extends ASTVisitor{
	
	int offset = -1;
	List<ASTNode> needToDelete = null;
	
	public DetailedASTTreeReducerVisitor(int offset, List<ASTNode> needToDelete) {
		this.offset = offset;
		this.needToDelete = needToDelete;
	}
	
	@Override
	public boolean preVisit2(ASTNode node) {
		if (offset < node.getStartPosition())
		{
			needToDelete.add(node);
			return false;
		}
		return super.preVisit2(node);
	}
	
}