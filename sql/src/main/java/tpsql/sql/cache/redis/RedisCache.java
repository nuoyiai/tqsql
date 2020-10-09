package tpsql.sql.cache.redis;

import org.springframework.util.DigestUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import tpsql.convert.IDataConvert;
import tpsql.spring.SpringContext;
import tpsql.sql.cache.CacheModel;
import tpsql.sql.cache.ICache;

import java.util.Properties;
import java.util.Set;

public class RedisCache implements ICache {
    private static JedisPool jedisPool;
    private ThreadLocal<Boolean> flushFlag = new ThreadLocal<Boolean>();        //同一线程内只清一次缓存

    private String getRedisIdsKey(String id){
        return "TPSQL:ID:"+id+":*";
    }

    private String getRedisSqlKey(Object key){
        return "TPSQL:SQLKEY:"+DigestUtils.md5DigestAsHex(String.valueOf(key).getBytes());
    }

    @Override
    public void flush(CacheModel cachemodel) {
        if(jedisPool!=null) {       //没初始化不刷新
            Boolean flag = flushFlag.get();
            if (flag == null || !flag.booleanValue()) {
                flushFlag.set(true);
                String idKey = getRedisIdsKey(cachemodel.getId());
                Jedis jedis = this.getJedis();
                Set<String> keys = jedis.smembers(idKey);
                jedis.del(keys.toArray(new String[0]));
                jedis.del(idKey);
            }
        }
    }

    @Override
    public Object getObject(CacheModel cachemodel, Object key) {
        String sqlKey = getRedisSqlKey(key);
        byte[] bytes = this.getJedis().get(sqlKey.getBytes());
        return getConvert().toData(bytes);
    }

    @Override
    public Object removeObject(CacheModel cachemodel, Object key) {
        String sqlKey = getRedisSqlKey(key);
        this.getJedis().expire(sqlKey.getBytes(),0);
        return null;
    }

    @Override
    public void putObject(CacheModel cachemodel, Object key, Object obj) {
        String sqlKey = getRedisSqlKey(key);
        byte[] bytes = getConvert().toBytes(obj);
        this.getJedis().set(sqlKey.getBytes(),bytes);
    }

    private Jedis getJedis(){
        return jedisPool.getResource();
    }

    private IDataConvert getConvert(){
        return (IDataConvert)SpringContext.instance().get("DataConvert");
    }

    @Override
    public void onit(Properties properties) {
        if(jedisPool==null) {
            if(SpringContext.instance().contains("jedisPool")){
                jedisPool = (JedisPool)SpringContext.instance().get("jedisPool");
            }else {
                String host = properties.getProperty("redis.host");
                String port = properties.getProperty("redis.port");
                jedisPool = new JedisPool(host,Integer.parseInt(port));
            }
        }
    }
}
