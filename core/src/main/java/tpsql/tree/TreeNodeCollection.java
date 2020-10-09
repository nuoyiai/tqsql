package tpsql.tree;

import java.util.Iterator;
import java.util.Map;

/**
 * 树的子节点集合,整棵树共享一个Hash,并实现的迭代接口
 * @author zhusw
 *
 * @param <T>
 */
public class TreeNodeCollection<T> implements Iterable<T> {
	public int length;
    private Map<String,T> nodes;
    private String code;
    private T firstTreeNode;
    private T lastTreeNode;

    protected TreeNodeCollection() {
	}

	public int size()
    {
        return this.length;
    }

    /**
     * 得到节点的存贮容器
     * @return
     */
    public Map<String, T> getNodesStore()
    {
        return this.nodes;
    }

    public String getCode()
    {
        return this.code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }
    
    /**
     * 得到第一个节点
     * @return
     */
    public T getFirstTreeNode() {
		return firstTreeNode;
	}

    /**
     * 设置第一个节点
     * @param firstTreeNode
     */
	public void setFirstTreeNode(T firstTreeNode) {
		this.firstTreeNode = firstTreeNode;
	}

	/** 得到最后一个节点
	 */
	public T getLastTreeNode() {
		return lastTreeNode;
	}

	/**
	 * 设置最后一个节点
	 * @param lastTreeNode
	 */
	public void setLastTreeNode(T lastTreeNode) {
		this.lastTreeNode = lastTreeNode;
	}

	/**
	 * 得到节点
	 * @param index
	 * @return
	 */
    public T get(int index)
    {
        String key = this.getKey(index);
        return this.nodes.get(key);
    }
    
    /**
     * 设置节点
     * @param index
     * @param value
     */
    public void set(int index,T value)
    {
        String key = this.getKey(index);
        this.nodes.put(key,value);
    }

    public TreeNodeCollection(Map<String, T> map)
    {
        this.nodes = map;
        this.code = Integer.toString(this.hashCode()); 
    }
    
    public void ContactCollection(Map<String, T> map)
    {
    	for(String key : this.nodes.keySet())
    	{
    		if(!map.containsKey(key))
    		{
    			map.put(key,this.nodes.get(key));
    		}
    	}
    }

    private String getKey(int index)
    {
        int num = index + 1;
        return this.getCode() + "_" + Integer.toString(num);
    }

    /**
     * 迭代器
     * @author Administrator
     *
     * @param <TN>
     */
	class TreeNodeIterator<TN> implements Iterator<TN>
    {
        private TN currentElement;
        private int index;
        private TreeNodeCollection<TN> list;

		public TreeNodeIterator(TreeNodeCollection<TN> list)
        {
            this.list = list;
            this.index = -1;
            this.currentElement=null;
        }
        
        @Override
		public boolean hasNext() {
        	if (this.index < (this.list.size() - 1))
            {
                return true;
            }
			return false;
		}
        
		@Override
		public TN next() {
			if (this.index < (this.list.size() - 1))
            {
                this.index++;
                this.currentElement = this.list.get(this.index);
                return this.currentElement;
            }
			return null;
		}
		
		@Override
		public void remove() {
		}
    }

	@Override
	public Iterator<T> iterator() {
		return new TreeNodeIterator<T>(this);
	}
}
