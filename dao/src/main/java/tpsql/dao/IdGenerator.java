package tpsql.dao;

public interface IdGenerator {
	
	Long getId(Class<?> entityType);
	
	Long getId(String tableName);

	Long[] getId(String tableName,int num);
	
	Long[] getId(Class<?> entityType,int num);
	
	Long getId(Class<?> entityType,String dbConfig);
	
	Long getId(String tableName,String dbConfig);
	
	Long[] getId(Class<?> entityType,int num,String dbConfig);
	
	Long[] getId(String tableName,int num,String dbConfig);
	
	Long getPkId(String tableName,String pk);
	
	Long[] getPkId(String tableName,String pk,int num);
	
	Long getPkId(String tableName,String pk,String dbConfig);
	
	Long[] getPkId(String tableName,String pk,int num,String dbConfig);
	
}
