package tpsql.core.spring;

import java.util.Map;

public interface ISpringContext {
	Object get(String name);

    Object get(String name, Object[] args);

    Object get(Class<?> c);

    <T> T get(String name,Class<T> c);

    <T> Map<String, T> getBeans(Class<T> c);

    boolean contains(String name);

    String[] getNames(Class<?> c);

    Map<String, ?> getMap(Class<?> c);
}
