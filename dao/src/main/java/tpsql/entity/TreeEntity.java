package tpsql.entity;

import java.util.Collection;

import tpsql.tree.IDataTreeNode;
import tpsql.tree.IDataTreeNode;
import tpsql.tree.IDataTreeNode;

public abstract class TreeEntity<T> extends Entity<T> implements IDataTreeNode<T>
{
	/**
	 * 父节点
	 */
	protected T parent;
	
	/**
	 * @param idProdName 主键ID字段名称
	 * @param parentIdProdName 上级主键ID字段名称
	 * @param childrenProdName 子节点集合属性名称
	 */
	public TreeEntity()
	{
		this.parent = null;
	}

	/**
	 * 得到上级节点
	 */
	@Override
	public T getParent() {
		return this.parent;
	}

	/**
	 * 设置上级节点
	 */
	@Override
	public void setParent(T parent) {
		this.parent = parent;
	}

	/**
	 * 添加子节点
	 */
	@Override
	public T add(T node) {
		this.getChildren().add(node);
		return node;
	}

	/**
	 * 得到子节点集合
	 */
	@Override
	public Iterable<T> getChildNodes() {
		return this.getChildren();
	}
	
	protected abstract Collection<T> getChildren();

	/**
	 * 得到子节点的个数
	 */
	@Override
	public int size() {
		return getChildren().size();
	}
}
