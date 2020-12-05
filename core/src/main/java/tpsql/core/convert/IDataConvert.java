package tpsql.core.convert;

public interface IDataConvert<T> {

    /**
     * 转化成二进制数组
     * @param t
     * @return
     */
    byte[] toBytes(T t);

    /**
     * 二进制数组转化成数据对象
     * @param bytes
     * @return
     */
    T toData(byte[] bytes);

}
