package cn.xiaochi.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * guava 进行本地缓存 token
 */
public class TokenCache {

    public static final String TOKEN_PREFIX = "token_";

    public static Logger logger = LoggerFactory.getLogger(TokenCache.class);
    // guava 缓存， 初始化容量为1000，最大10000，超过则移除缓存项，有效期 12 小时
    public static LoadingCache<String,String> localCache = CacheBuilder.newBuilder().initialCapacity(1000).maximumSize(10000).expireAfterAccess(12, TimeUnit.HOURS).build(new CacheLoader<String, String>() {
        @Override
        public String load(String key) throws Exception {
            // 默认的数据加载实现,当调用get取值的时候,如果key没有对应的值,就调用这个方法进行加载.
            return "null";
        }
    });

    /**
     * 设置缓存
     * @param key
     * @param value
     */
    public static void setKey(String key,String value){
        localCache.put(key,value);
    }

    /**
     * 获取缓存
     * @param key
     * @return
     */
    public static String getKey(String key){
        String value = null;
        try{
            value = localCache.get(key);
            if ("null".equals(value)){
                return null;
            }
            return value;
        }catch (Exception e){
            logger.error("localCache get error",e);
            return null;
        }
    }
}
