package com.baizhi.cache;

import com.baizhi.util.SerializeUtils;
import com.baizhi.util.SpringContextUtil;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.locks.ReadWriteLock;

public class MybatisCache implements Cache {
    private String id;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        StringRedisTemplate srt = (StringRedisTemplate) SpringContextUtil.getBean(StringRedisTemplate.class);
        srt.opsForHash().put(id,key.toString(), SerializeUtils.serialize(value));
    }

    @Override
    public Object getObject(Object key) {
        StringRedisTemplate srt = (StringRedisTemplate) SpringContextUtil.getBean(StringRedisTemplate.class);
        String o = (String) srt.opsForHash().get(id, key.toString());
        Object value = SerializeUtils.serializeToObject(o);
        if(value==null){
            return null;
        }else{

            return value;
        }
    }

    @Override
    public Object removeObject(Object o) {
        return null;
    }

    @Override
    public void clear() {
        StringRedisTemplate srt = (StringRedisTemplate) SpringContextUtil.getBean(StringRedisTemplate.class);
        srt.delete(id);
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }
}
