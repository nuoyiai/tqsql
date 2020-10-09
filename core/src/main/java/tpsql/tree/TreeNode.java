package tpsql.tree;

import java.util.HashMap;
import java.util.Map;

/**
 * 树节点的基类,实现常用的节点操作
 * @author zhusw
 *
 */
@SuppressWarnings("rawtypes")
public class TreeNode <T extends TreeNode> implements ITreeNode<T> {
	T parent;
    private int index;
    private int depth;
    private boolean isRoot;
	private TreeNodePosition position;
    private TreeNodeCollection<T> childs;

    /**
     * 子节点集合
     * @return
     */
    @Override
	public TreeNodeCollection<T> getChildNodes()
    {
        return this.childs;
    }
    
    /**
     * 设置子节点集合
     * @param childs
     */
    public void setChildNodes(TreeNodeCollection<T> childs)
    {
        this.childs = childs;
    }

    /**
     * 得到节点序号
     * @return
     */
    protected int getIndex()
    {
        return this.index;
    }

    /**
     * 设置节点序号
     * @param index
     */
    protected void setIndex(int index)
    {
        this.index = index;
    }

    /**
     * 得到节点的位置 	Top,Middle,Bottom
     * @return
     */
    public TreeNodePosition getPosition()
    {
        return this.position;
    }
    
    /**
     * 设置节点的位置 	Top,Middle,Bottom
     * @param position
     */
    public void setPosition(TreeNodePosition position)
    {
        this.position = position;
    }

    /**
     * 是否为根节点
     * @return
     */
    public boolean isRoot()
    {
        return this.isRoot;
    }
    
    /**
     * 设置是否为根节点
     * @param isRoot
     */
    public void setRoot(boolean isRoot){
    	this.isRoot = isRoot;
    }

    /**
     * 子节点的数量
     * @return
     */
    @Override
	public int size()
    {
        return (this.getChildNodes() != null) ? this.getChildNodes().size() : 0;
    }

    /**
     * 得到父节点
     * @return
     */
    @Override
	public T getParent()
    {
        return this.parent;
    }

    /**
     * 设置父节点
     * @param parent
     */
    @Override
	public void setParent(T parent)
    {
        this.parent = parent;
    }

    /**
     * 得到节点深度
     * @return
     */
    public int getDepth()
    {
        return this.depth;
    }

    /**
     * 设置节点深度
     * @param depth
     */
    public void setDepth(int depth)
    {
        this.depth=depth;
    }

    public TreeNode()
    {
        this.index = 0;
        this.position = TreeNodePosition.Top;
        this.depth = 0;
        this.isRoot=true;
    }

    /**
     * 添加一个节点
     * @param node
     * @return
     */
    @Override
	@SuppressWarnings("unchecked")
	public T add(T node)
    {
        if (this.getChildNodes() == null)
        {
            Map<String, T> tstore;
            if (this.getParent() != null) {
				tstore = this.getParent().getChildNodes().getNodesStore();
			} else {
				tstore = new HashMap<String, T>();
			}

            this.setChildNodes(new TreeNodeCollection<T>(tstore));
        }

        if(node.getChildNodes()!=null)
        {
        	node.getChildNodes().ContactCollection(this.getChildNodes().getNodesStore());
        	
        }
        else
        {
        	node.setChildNodes(new TreeNodeCollection<T>(this.getChildNodes().getNodesStore()));
        }

        node.setParent(this);
        node.setDepth(this.getDepth() + 1);
        node.setRoot(false);
        node.setIndex(this.getChildNodes().size());

        if (this.getChildNodes().size() == 0)
        {
            node.setPosition(TreeNodePosition.Bottom);
            this.getChildNodes().setFirstTreeNode(node);
            this.getChildNodes().setLastTreeNode(node);
        }
        else
        {
            for(T n : this.getChildNodes())
            {
                if (n.getPosition() == TreeNodePosition.Bottom)
                {
                    n.setPosition(TreeNodePosition.Middle);
                }
            }
            node.setPosition(TreeNodePosition.Bottom);
            this.getChildNodes().setLastTreeNode(node);
        }
        this.getChildNodes().length++;
        int index = this.getAddIndex();
        this.getChildNodes().set(index,node);
        return node;
    }

    private int getAddIndex()
    {
        return this.getChildNodes().size() - 1;
    }
}
