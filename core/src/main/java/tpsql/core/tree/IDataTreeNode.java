package tpsql.core.tree;


public interface IDataTreeNode<T> extends ITreeNode<T> {
	
	Object getId();
	
	Object getParentId();
	
	String getName();
	
	Boolean getChecked();
	
	String[] getParamNames();
	
	Object getParamValue(String name);
}
