package tpsql.sql.cache;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import tpsql.sql.SqlMapException;
import tpsql.sql.event.SqlExecuteEvent;
import tpsql.sql.event.SqlExecuteListener;
import tpsql.util.PropertiesUtil;
import tpsql.util.StringUtil;
import tpsql.sql.SqlMapException;
import tpsql.sql.event.SqlExecuteEvent;
import tpsql.sql.event.SqlExecuteListener;

public class CacheModel implements SqlExecuteListener {
	private long lastFlush;
	private long flushInterval;
	private int hits = 0;
	private int requests = 0;
	private String id;
	private ICache cache;
	public static final Object NULL_OBJECT = new Object();
	private Set<String> flushStatements;
	private boolean used;		//有没有被引用到
	
	public CacheModel(){
		requests = 0;
        hits = 0;
        used = false;
        flushInterval = -99999L;
        lastFlush = System.currentTimeMillis();
        flushStatements = new HashSet<String>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public double getHitRatio(){
        return (double)hits / (double)requests;
    }
	
	public long getFlushIntervalSeconds(){
        return flushInterval / 1000L;
    }
	
	public long getFlushInterval(){
		return this.flushInterval;
	}
	
	public void setFlushInterval(Long interval){
		this.flushInterval = interval;
	}
	
	public void addFlushStatement(String statementName){
        flushStatements.add(statementName);
        SqlExecuteEvent.addListener(statementName, this);
    }
	
	/**
	 * 
	 */
	public void flush(){
        //synchronized(this){
            this.cache.flush(this);
            lastFlush = System.currentTimeMillis();
        //}
    }
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public Object getObject(CacheKey key){
		Object value = null;
        //synchronized(this){
            if(flushInterval != -99999L && System.currentTimeMillis() - lastFlush > flushInterval) {
				this.flush();
			}
            value = this.cache.getObject(this, key);
            requests++;
            if(value != null) {
				hits++;
			}
        //}
        return value;
	}
	
	public void setCacheType(String cacheType){
		String className=null;
		Properties properties=null;
		if("OSCACHE".equalsIgnoreCase(cacheType)){
			className = "tpsql.sql.cache.oscache.OSCache";
		}else if("REDIS".equalsIgnoreCase(cacheType)){
			className = "tpsql.sql.cache.redis.RedisCache";
			properties = PropertiesUtil.loadPropertyMapByResource("classpath:redis.properties");
		}
		if(className!=null){
			try {
				Class<?> clazz = Class.forName(className);
				cache = (ICache)clazz.newInstance();
				cache.onit(properties);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new SqlMapException(StringUtil.format("实列化缓存类 {0} 出错",className));
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new SqlMapException(StringUtil.format("实列化缓存类 {0} 出错",className));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				throw new SqlMapException(StringUtil.format("实列化缓存类 {0} 出错",className));
			}
		}else {
			throw new SqlMapException(StringUtil.format("未知缓存类型 {0}", cacheType));
		}
    }
	
	/**
	 * 
	 * @param key
	 * @param value
	 */
	public void putObject(CacheKey key, Object value){
		if(value == null) {
			value = NULL_OBJECT;
		}
        synchronized(this){
            cache.putObject(this, key, value);
        }
	}

	@Override
	public void onSqlExecute() {
		if(this.used) {		//没使用就不刷新缓存
			this.flush();
		}
	}
}
