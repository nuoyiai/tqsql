package tpsql.sql.cache.oscache;

import java.util.Properties;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

import tpsql.sql.cache.CacheModel;
import tpsql.sql.cache.ICache;

public class OSCache implements ICache {
	private static final GeneralCacheAdministrator cache = new GeneralCacheAdministrator();
	
	@Override
	public void flush(CacheModel cacheModel){
		cache.flushGroup(cacheModel.getId());
    }

    @Override
	public Object getObject(CacheModel cacheModel, Object key)
    {
        String keyString = key.toString();
        int refreshPeriod = (int)cacheModel.getFlushIntervalSeconds();
        try {
			return cache.getFromCache(keyString, refreshPeriod);
		} catch (NeedsRefreshException e) {
			cache.cancelUpdate(keyString);
			return null;
		}
    }

    @Override
	public Object removeObject(CacheModel cacheModel, Object key){
        Object result=null;
        String keyString=key.toString();
            
		try {
			int refreshPeriod = (int)cacheModel.getFlushIntervalSeconds();
			Object value = cache.getFromCache(keyString, refreshPeriod);
			if(value != null) {
				cache.flushEntry(keyString);
			}
            result = value;
		} catch (NeedsRefreshException e) {
			cache.flushEntry(keyString);
			cache.cancelUpdate(keyString);
	        result = null;
		}

        return result;
    }

    @Override
	public void putObject(CacheModel cacheModel, Object key, Object object){
        String keyString = key.toString();
        cache.putInCache(keyString, object, new String[] {
            cacheModel.getId()
        });
    }

	@Override
	public void onit(Properties properties) {
		
	}
}
