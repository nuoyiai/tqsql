package tpsql.core.convert;

public interface IProxyConvert<T> {

    /**
     * 转成动态代理类
     * @param t
     * @return
     */
    Object toObj(T t);

}
