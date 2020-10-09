package tpsql.entity;

import tpsql.spring.SpringContext;
import tpsql.sql.command.SqlObjectReader;
import tpsql.util.ReflectorUtil;
import tpsql.util.StringUtil;

public class EntityReader extends SqlObjectReader {
	
	private static final String EntityMapKey = "EntityMap";
	private IEntityMap entityMap;
	
	protected IEntityMap getEntityMap(){
		if(entityMap==null && SpringContext.instance().contains(EntityMapKey)) {
			entityMap = (IEntityMap) SpringContext.instance().get(EntityMapKey);
		}
		return entityMap;
	}
	
	@Override
	public Object getValue(Object entity, String propertyName) {
		if(entity instanceof IPersistEntity){
			return ((IPersistEntity<?>)entity).getPropertyValue(propertyName);
		}else{
			if(entity != null) {
				if(getEntityMap()!=null && this.getEntityMap().containKey(entity.getClass())){
					String property = getEntityMap().getProperty(entity.getClass(), propertyName);
					if(StringUtil.isNullOrEmpty(property)) {
						property = propertyName;
					}
					if(ReflectorUtil.containProperty(entity.getClass(),property)) {
						return ReflectorUtil.getPropertyValue(entity,property);
					}else {
						return null;
					}
				}
			}
			return super.getValue(entity, propertyName);
		}
	}

	@Override
	public Object getValue(Object entity, int propertyIndex) {
		if(entity instanceof IPersistEntity){
			throw new RuntimeException("实体对像属性不支持,索引读取");
		}else{
			return super.getValue(entity, propertyIndex);
		}
	}
}
