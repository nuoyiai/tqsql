package tpsql.dao.support;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Deprecated
public class DaoBeanHandler extends ObjectDao implements InvocationHandler {
    private Object[] params;

    public DaoBeanHandler(Object[] params){
        this.params = params;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        Object result = methodInvoke(o,method,args);
        return result;
    }

    private Object methodInvoke(Object o,Method method,Object[] args) throws Throwable {
        return method.invoke(o,args);
    }
}
