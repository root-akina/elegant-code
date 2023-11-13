package ${base.basePackage}.cache.core;

import ${base.basePackage}.common.constant.DataSecurityConstant;
import ${base.basePackage}.common.context.SubjectContext;
import ${base.basePackage}.common.vo.DataSecurity;
import ${base.basePackage}.common.vo.UserVo;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.util.List;

public class CustomRedisCacheManager extends RedisCacheManager {

    public CustomRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    private List<String> keyList;

    public CustomRedisCacheManager(RedisConnectionFactory redisConnectionFactory ,RedisCacheConfiguration config,List<String> keyList){
        this( RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory), config  );
        this.keyList = keyList;
    }

    @Override
    public Cache getCache(String name) {
        if(keyList.contains( name )){
            UserVo userVo = SubjectContext.getSubject();//得到当前登录用户
            DataSecurity dataSecurity = userVo.getDataSecurity(); //获取数据权限
            //仅个人
            if(DataSecurityConstant.DATA_SCOPE_ONLY_ME.equals( dataSecurity.getDataScope() )){
                name += ":user_"+ userVo.getId();
            }
            //按部门
            if(DataSecurityConstant.DATA_SCOPE_CUSTOM.equals( dataSecurity.getDataScope() )){
                name +=":dept_"+ dataSecurity.getDeptNos().hashCode();
            }
        }
        return super.getCache(name);
    }

}
