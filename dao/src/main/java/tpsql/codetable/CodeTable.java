package tpsql.codetable;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CodeTable {
	
	
	/**
	 * 代码表名称
	 * @return
	 */
	String name();
	
	/**
	 * 代码表键字段名
	 * @return
	 */
    String key();
    
    /**
     * 查询代码表的参数
     * @return
     */
    String params() default "";
    
    /**
     * 分隔符
     * @return
     */
    String split() default "";

}
