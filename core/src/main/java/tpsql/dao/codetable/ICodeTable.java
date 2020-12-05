package tpsql.dao.codetable;

import java.util.List;

public interface ICodeTable
{
	List<IItem> getItems(Object ... conditions);
	
	interface IItem {
		
		String getValue();
		
		String getText();
		
		int getOrder();
		
		String getIcon();
		
		String checked();
		
		void checked(String cheched);
	}
}
