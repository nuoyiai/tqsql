package tpsql.tree;

import tpsql.tree.ITreeNode;

public interface IDataTreeNode<T> extends ITreeNode<T> {
	
	Object getId();
	
	Object getParentId();
	
	String getName();
	
	boolean getChecked();
	
	String[] getParamNames();
	
	Object getParamValue(String name);
}
