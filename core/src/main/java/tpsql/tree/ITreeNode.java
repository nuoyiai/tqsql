package tpsql.tree;

public interface ITreeNode<T> {
	/**
	 * 得到父节点
	 * @return
	 */
	T getParent();
	
	/**
	 * 设置父节点
	 * @param parent
	 */
	void setParent(T parent);
	
	T add(T node);
	
	/**
	 * 子节点的集合，只包括下一级
	 * @return
	 */
	Iterable<T> getChildNodes();
	
	/**
	 * 子节点的个数，只包括下一级
	 * @return
	 */
	int size();
}
