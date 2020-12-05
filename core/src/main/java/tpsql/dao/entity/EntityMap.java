package tpsql.dao.entity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import tpsql.dao.EntityMapException;
import tpsql.sql.mapping.SqlMap;
import tpsql.core.util.ReflectorUtil;
import tpsql.core.util.StringUtil;
import tpsql.core.xml.XmlDocument;
import tpsql.core.xml.XmlNode;
import tpsql.core.xml.XmlNodeType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

public class EntityMap implements IEntityMap,InitializingBean {
	private Resource[] mappingLocations;
	private List<Entity> entities;
	private Map<Object,Entity> entityMap;
	private Set<Class<?>> filterSet;
	private static Log log = LogFactory.getLog(SqlMap.class);
	
	public List<Entity> getEntities(){
		return this.entities;
	}

	public void setMappingLocations(Resource[] mappingLocations) {
		this.mappingLocations = mappingLocations;
	}

	public void afterPropertiesSet() throws Exception {
		if(mappingLocations != null){
			this.entities = ClassMapLoader.loadResource(mappingLocations);
        }
		
		this.filterSet = new HashSet<Class<?>>();
		this.entityMap = new HashMap<Object,Entity>();
		for(Entity entity : this.entities){
			this.entityMap.put(entity.getType(),entity);
			this.entityMap.put(entity.getTableName(),entity);
		}
	}

	/**
	 * 得到属性字段映射
	 * @param type
	 * @return
	 */
	public Map<String,String> getMap(Class<?> type){
		if(this.containKey(type)){
			return this.entityMap.get(type).getPropertyMap();
		}
		return null;
	}
	
	/**
	 * 得到表名
	 * @param type
	 * @return
	 */
	public String getTable(Class<?> type){
		if(this.containKey(type)){
			return this.entityMap.get(type).getTableName();
		}
		return null;
	}
	
	/**
	 * 得到类
	 * @param tableName 表名
	 * @return
	 */
	public Class<?> getType(String tableName){
		if(this.containKey(tableName)){
			return this.entityMap.get(tableName).getType();
		}
		return null;
	}
	
	/**
	 * 通过属性名
	 * @param type
	 * @param columnName
	 * @return
	 */
	public String getProperty(Class<?> type, String columnName){
		if(this.containKey(type)){
			if(this.entityMap.get(type).contains(columnName)) {
				return this.entityMap.get(type).getProperty(columnName).propertyName;
			}
		}
		return null;
	}
	
	/**
	 * 得到列名
	 * @param type
	 * @param propertyName
	 * @return
	 */
	public String getColumn(Class<?> type, String propertyName){
		if(this.containKey(type)){
			if(this.entityMap.get(type).contains(propertyName)) {
				return this.entityMap.get(type).getProperty(propertyName).columnName;
			}
		}
		return null;
	}
	
	/**
	 * 得到主键列
	 * @param type
	 * @return
	 */
	public String getIdColumn(Class<?> type){
		if(this.containKey(type)){
			if(this.entityMap.get(type).id!=null) {
				return this.entityMap.get(type).id.getColumnName();
			}
		}
		return null;
	}

	/**
	 * 得到主键属性
	 * @param type
	 * @return
	 */
	public String getIdProperty(Class<?> type){
		if(this.containKey(type)){
			if(this.entityMap.get(type).id!=null) {
				return this.entityMap.get(type).id.getPropertyName();
			}
		}
		return null;
	}

	public String getIdColumn(String tableName){
		if(this.containKey(tableName)){
			if(this.entityMap.get(tableName).id!=null) {
				return this.entityMap.get(tableName).id.getColumnName();
			}
		}
		return null;
	}
	
	/**
	 * 是否包含实体映射类
	 * @param key
	 * @return
	 */
	public boolean containKey(String key){
		return this.entityMap.containsKey(key);
	}
	
	/**
	 * 是否包含实体映射类或继承类
	 * @param type
	 * @return
	 */
	public boolean containKey(Class<?> type){
		if(filterSet.contains(type)) {
			return false;
		}
		Class<?> cls = type;
		while(cls!=null) {
			if(this.entityMap.containsKey(cls)){
				if(!this.entityMap.containsKey(type)) {
					this.entityMap.put(type, this.entityMap.get(cls));
				}
				return true;
			}else {
				cls = cls.getSuperclass();
			}
		}
		filterSet.add(type);
		return false;
	}
	
	/**
	 * 类映射信息
	 */
	static class Entity {
		private String tableName;			//表名例名
		private Class<?> type;
		private List<Property> propertyList;
		private Map<String,String> propertyMap;
		private Map<String,Property> propertyCache;
		private Id id;
		
		public Entity(){
			this.propertyList = new ArrayList<Property>();
			this.propertyCache = new HashMap<String,Property>();
			this.propertyMap = new HashMap<String,String>();
		}
		
		public void addProperty(Property pi){
			this.propertyList.add(pi);
			this.propertyMap.put(pi.propertyName, pi.columnName);
			propertyCache.put(pi.propertyName, pi);
			propertyCache.put(pi.columnName, pi);
		}
		
		public Property getProperty(String key){
			return this.propertyCache.get(key);
		}
		
		public boolean contains(String key){
			return this.propertyCache.containsKey(key);
		}

		public String getTableName() {
			return tableName;
		}

		public void setTableName(String tableName) {
			this.tableName = tableName;
		}

		public Class<?> getType() {
			return this.type;
		}
		
		public Map<String,String> getPropertyMap(){
			return this.propertyMap;
		}
	}
	
	static class Id extends Property {
		
	}
	
	/**
	 * 属性映射信息
	 */
	static class Property extends Column {
		private String propertyName;
		private String propertyType;
		
		public String getPropertyName() {
			return propertyName;
		}
		public void setPropertyName(String propertyName) {
			this.propertyName = propertyName;
		}
		public String getPropertyType() {
			return propertyType;
		}
		public void setPropertyType(String propertyType) {
			this.propertyType = propertyType;
		}
	}
	
	/**
	 * 例映射信息
	 */
	static class Column {
		protected String columnName;
		protected Integer length;
		protected Integer precision;
		protected Integer scale;
	    private boolean nullable;
	    private boolean unique;
		
		public Column(){
			this.nullable = true;
			this.unique = false;
		}
		
		public String getColumnName() {
			return columnName;
		}
		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}
		public Integer getLength() {
			return length;
		}
		public void setLength(Integer length) {
			this.length = length;
		}
		public Integer getPrecision() {
			return precision;
		}
		public void setPrecision(Integer precision) {
			this.precision = precision;
		}
		public Integer getScale() {
			return scale;
		}
		public void setScale(Integer scale) {
			this.scale = scale;
		}

		public boolean isNullable() {
			return nullable;
		}
		public void setNullable(boolean nullable) {
			this.nullable = nullable;
		}
		public boolean isUnique() {
			return unique;
		}

		public void setUnique(boolean unique) {
			this.unique = unique;
		}
	}
	
	static class ClassMapLoader {
		
		public static List<Entity> loadResource(Resource[] mappingLocations){
			List<Entity> entities = new ArrayList<Entity>();
			for(int i = 0; i < mappingLocations.length; i++){
            	String filename = mappingLocations[i].getFilename();
				try {
					InputStream stream = mappingLocations[i].getInputStream();
					XmlDocument xdoc = new XmlDocument(stream);
					for(XmlNode root : xdoc.getChildNodes()){
			        	if(root.getName().equalsIgnoreCase("mapping")){
			        		for(XmlNode node : root.getChildNodes()){
			        			if(node.getNodeType() == XmlNodeType.Element){
			        				Entity entity = buildEntity(node,filename);
			        				if(entity!=null) {
										entities.add(entity);
									}
			        			}
			        		}
			        	}
			        }
				} catch (IOException e) {
					throw new EntityMapException(StringUtil.format("加载实体映射文件 {0} 出错", filename));
				} catch (EntityMapException e){
					throw e;
				}
				if(log.isInfoEnabled()){
					log.info(StringUtil.format("load entity mapping file :"+filename));
				}
            }
			return entities;
		}

		private static Entity buildEntity(XmlNode node,String filename){
			if(node.getName().equalsIgnoreCase("class")){
				if(!node.getAttributes().containsKey("name")) {
					throw new EntityMapException("class节点 name属性未定义 文件 " + filename);
				}
				if(!node.getAttributes().containsKey("table")) {
					throw new EntityMapException("class节点 table属性未定义 文件 " + filename);
				}
				String name = node.getAttributes().get("name").getValue();
				String table = node.getAttributes().get("table").getValue();
				Entity e = new Entity();
				e.type = ReflectorUtil.getClassForName(name);
				e.tableName = table;
				for(XmlNode n : node.getChildNodes()){
					Property prop = buildProperty(n,filename);
					if(prop!=null){
						e.addProperty(prop);
						if(prop instanceof Id) {
							e.id = (Id) prop;
						}
					}
				}
				return e;
			}
			return null;
		}
		
		private static Property buildProperty(XmlNode node,String filename){
			if(node.getName().equalsIgnoreCase("property") || node.getName().equalsIgnoreCase("id")){
				if(!node.getAttributes().containsKey("name")) {
					throw new EntityMapException("property节点 name属性未定义 文件 " + filename);
				}
				if(!node.getAttributes().containsKey("type")) {
					throw new EntityMapException("property节点 type属性未定义 文件 " + filename);
				}
				String name = node.getAttributes().get("name").getValue();
				String type = node.getAttributes().get("type").getValue();
				Property prop = node.getName().equalsIgnoreCase("id")?new Id():new Property();
				prop.propertyName = name;
				prop.propertyType = type;
				for(XmlNode n : node.getChildNodes()){
					Column col = buildColumn(n,filename);
					if(col!=null){
						prop.columnName = col.columnName;
						prop.length = col.length;
						prop.precision = col.precision;
						prop.scale = col.scale;
					}
				}
				return prop;
			}
			return null;
		}
		
		private static Column buildColumn(XmlNode node,String filename){
			if(node.getName().equalsIgnoreCase("column")){
				if(!node.getAttributes().containsKey("name")) {
					throw new EntityMapException("column节点 name属性未定义 文件 " + filename);
				}
				String name = node.getAttributes().get("name").getValue();
				Column col = new Column();
				col.columnName = name;
				if(node.getAttributes().containsKey("length")) {
					col.length = Integer.parseInt(node.getAttributes().get("length").getValue());
				}
				if(node.getAttributes().containsKey("precision")) {
					col.precision = Integer.parseInt(node.getAttributes().get("precision").getValue());
				}
				if(node.getAttributes().containsKey("scale")) {
					col.scale = Integer.parseInt(node.getAttributes().get("scale").getValue());
				}
				if(node.getAttributes().containsKey("nullable")) {
					col.nullable = "true".equalsIgnoreCase(node.getAttributes().get("nullable").getValue());
				}
				if(node.getAttributes().containsKey("unique")) {
					col.unique = "true".equalsIgnoreCase(node.getAttributes().get("unique").getValue());
				}
				return col;
			}
			return null;
		}
		
	}
}
