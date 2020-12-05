package tpsql.dao.entity;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

import tpsql.core.util.ReflectorUtil;

public abstract class Entity<E> implements IPersistEntity<E> {
	private Class<E> type;
	
	/**
	 * 得到实体对像的类型
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Class<E> getGenericType(){
		if(this.type==null){
			Class<?> cls = this.getClass();
			while(cls!=null){
				if(cls.getGenericSuperclass() instanceof ParameterizedType){
					ParameterizedType pt = (ParameterizedType)cls.getGenericSuperclass();
					this.type = (Class<E>)pt.getActualTypeArguments()[0];
					return this.type;
				}else {
					cls = cls.getSuperclass();
				}
			}
		}
		return this.type;
	}
	
	/**
	 * 得到属性性型
	 * @return
	 */
	@Override
	public Class<?> getPropertyType(String propertyName){
		return EntityUtil.getPropertyType(this, propertyName);
	}
	
	/**
	 * 动态得到实体的属性值
	 * @param propertName
	 * @return
	 */
	@Override
	public Object getPropertyValue(String propertyName){
		return EntityUtil.getPropertyValue(this, propertyName);
	}
	
	/**
	 * 动态设置实体的属性值
	 * @param propertName
	 * @return
	 */
	@Override
	public void setPropertyValue(String propertyName, Object propertyValue){
		EntityUtil.setPropertyValue(this,propertyName,propertyValue);
	}
	
	/**
	 * 拷贝数据到实体
	 * @param data 数据 Map DataRow ... 等等
	 */
	@Override
	public void copyProperties(Object data){
		EntityUtil.copyProperties(this,data);
	}
	
	/**
	 * 拷贝数据到实体
	 * @param data 数据 Map DataRow ... 等等
	 * @param properties 指定要拷贝属性
	 */
	@Override
	public void copyProperties(Object data, Map<String,String> propertyMap){
		EntityUtil.copyProperties(this,data);
		EntityUtil.copyProperties(this,data,propertyMap);
	}
	
	/**
	 * 是否包含该属性
	 * @param propertyName
	 * @return
	 */
	@Override
	public boolean contains(String propertyName){
		return EntityUtil.containProperty(this,propertyName);
	}
	
	/**
	 * 克隆一个实体
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public E clone(){
		IPersistEntity<E> entity = (IPersistEntity<E>)ReflectorUtil.newInstance(this.getClass());
		entity.copyProperties(this);
		return (E)entity;
	}
	
}
