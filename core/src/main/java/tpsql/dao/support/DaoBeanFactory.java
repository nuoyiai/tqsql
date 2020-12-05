package tpsql.dao.support;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

@Deprecated
public class DaoBeanFactory<T> implements FactoryBean<T> {
    private Class<T> interfaceClass;
    private Object[] params;

    public void setInterfaceClass(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    @Override
    public T getObject() throws Exception {
        T o = (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new DaoBeanHandler(params));
        return o;
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
