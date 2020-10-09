package tpsql.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeUtil {
	
	public static <T> IDataTreeNode<?> buildTree(List<IDataTreeNode<T>> nodes)
    {
		return buildTree(nodes,null);
    }
	
	public static <T> IDataTreeNode<T> buildTree(List<IDataTreeNode<T>> nodes,IDataTreeNode<T> root)
    {
		IDataTreeNode<T> tree = (root!=null)?root:nodes.get(0);
        setChildNodes(tree,getSortNodeMap(nodes));
        return tree;
    }
	
	private static <T> Map<String,List<IDataTreeNode<T>>> getSortNodeMap(List<IDataTreeNode<T>> nodes)
	{
		Map<String,List<IDataTreeNode<T>>> map = new HashMap<String,List<IDataTreeNode<T>>>();
		for(IDataTreeNode<T> node : nodes)
		{
			String key = node.getParentId().toString();
			if(!map.containsKey(key)) {
				map.put(key, new ArrayList<IDataTreeNode<T>>());
			}
			map.get(key).add(node);
		}
		return map;
	}

    @SuppressWarnings("unchecked")
	private static <T> void setChildNodes(IDataTreeNode<T> node,Map<String,List<IDataTreeNode<T>>> map)
    {
        String key = node.getId().toString();
        if(map.containsKey(key))
        {
        	List<IDataTreeNode<T>> nodes = map.get(key);
        	for(IDataTreeNode<T> n : nodes)
        	{
        		node.add((T)n);
        	}
        }

        if (node.getChildNodes() != null)
        {
        	for(Object obj : node.getChildNodes())
            {
        		IDataTreeNode<T> n = (IDataTreeNode<T>)obj;
        		setChildNodes(n, map);
            }
        }
    }
}
