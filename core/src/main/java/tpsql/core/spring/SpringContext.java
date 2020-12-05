package tpsql.core.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class SpringContext implements ISpringContext,ApplicationContextAware {
	private ApplicationContext context;
	private static ISpringContext instance;

	public boolean contains(String name) {
		return context.containsBean(name);
	}

	public Object get(String name) {
		return context.getBean(name);
	}

	public Object get(String name, Object[] args) {
		return context.getBean(name, args);
	}

	public Object get(Class<?> c) {
		return context.getBean(c);
	}

	public <T> T get(String name,Class<T> c){
		return context.getBean(name,c);
	}

	public <T> Map<String, T> getBeans(Class<T> c){
		return context.getBeansOfType(c);
	}

	public String[] getNames(Class<?> c) {
		return context.getBeanNamesForType(c);
	}

	public Map<String, ?> getMap(Class<?> c){
        return context.getBeansOfType(c);
    }

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.context = applicationContext;
		instance = this;
	}
	
	public static ISpringContext instance(){
		return instance;
	}
}
