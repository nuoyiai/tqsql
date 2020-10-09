package tpsql.entity;

import java.util.Map;

public interface IPersistEntity<E> {
	
	/**
	 * 得到实体对像的类型
	 * @return
	 */
	Class<E> getGenericType();
	
	/**
	 * 得到属性性型
	 * @return
	 */
	Class<?> getPropertyType(String propertyName);
	
	/**
	 * 动态得到实体的属性值
	 * @param propertName
	 * @return
	 */
	Object getPropertyValue(String propertyName);
	
	/**
	 * 动态设置实体的属性值
	 * @param propertName
	 * @return
	 */
	void setPropertyValue(String propertyName,Object propertyValue);
	
	/**
	 * 拷贝数据到实体
	 * @param data 数据 Map DataRow ... 等等
	 */
	void copyProperties(Object data);
	
	/**
	 * 拷贝数据到实体
	 * @param data 数据 Map DataRow ... 等等
	 * @param properties 指定要拷贝属性
	 */
	void copyProperties(Object data,Map<String,String> propertyMap);
	
	/**
	 * 是否包含该属性
	 * @param propertyName
	 * @return
	 */
	boolean contains(String propertyName);
	
	/**
	 * 克隆一个实体
	 * @return
	 */
	E clone();

}
