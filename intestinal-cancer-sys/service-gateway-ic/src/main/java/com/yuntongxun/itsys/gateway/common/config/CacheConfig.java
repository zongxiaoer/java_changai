package com.yuntongxun.itsys.gateway.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.yuntongxun.itsys.gateway.common.base.AbstractLogger;

import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.DiskStoreConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
//@EnableCaching//标注启动缓存.
//@EnableAutoConfiguration
public class CacheConfig extends AbstractLogger{
    
    @Value("${ytx.gateway.token.expire}")
    private int tokenExpire;//token失效时间秒
    @Value("${ytx.gateway.permissions.expire}")
    private int permissionExpire;//权限失效时间秒
    
    /*
    name:缓存名称。
    maxElementsInMemory:缓存最大数目
    maxElementsOnDisk：硬盘最大缓存个数。 
    eternal:对象是否永久有效，一但设置了，timeout将不起作用。 
    overflowToDisk:是否保存到磁盘，当系统当机时
    timeToIdleSeconds:设置对象在失效前的允许闲置时间（单位：秒）。仅当eternal=false对象不是永久有效时使用，可选属性，默认值是0，也就是可闲置时间无穷大。
    timeToLiveSeconds:设置对象在失效前允许存活时间（单位：秒）。最大时间介于创建时间和失效时间之间。仅当eternal=false对象不是永久有效时使用，默认是0.，也就是对象存活时间无穷大。
    diskPersistent：是否缓存虚拟机重启期数据 Whether the disk store persists between restarts of the Virtual Machine. The default value is false. 
    diskSpoolBufferSizeMB：这个参数设置DiskStore（磁盘缓存）的缓存区大小。默认是30MB。每个Cache都应该有自己的一个缓冲区。 
    diskExpiryThreadIntervalSeconds：磁盘失效线程运行时间间隔，默认是120秒。
    memoryStoreEvictionPolicy：当达到maxElementsInMemory限制时，Ehcache将会根据指定的策略去清理内存。默认策略是LRU（最近最少使用）。你可以设置为FIFO（先进先出）或是LFU（较少使用）。 
     clearOnFlush：内存数量最大时是否清除。
      memoryStoreEvictionPolicy:可选策略有：LRU（最近最少使用，默认策略）、FIFO（先进先出）、LFU（最少访问次数）。
         FIFO，first in first out，这个是大家最熟的，先进先出。
         LFU， Less Frequently Used，就是上面例子中使用的策略，直白一点就是讲一直以来最少被使用的。如上面所讲，缓存的元素有一个hit属性，hit值最小的将会被清出缓存。
         LRU，Least Recently Used，最近最少使用的，缓存的元素有一个时间戳，当缓存容量满了，而又需要腾出地方来缓存新的元素的时候，那么现有缓存元素中时间戳离当前时间最远的元素将被清出缓存。
 */

    /**
     *  ehcache 主要的管理器
     * @param bean
     * @return
     */
    @Bean(name = "ehCacheManager")
    public net.sf.ehcache.CacheManager ehCacheManager(){
        net.sf.ehcache.config.Configuration configuration = new net.sf.ehcache.config.Configuration()//  
                .diskStore(new DiskStoreConfiguration().path("java.io.tmpdir"))//临时文件目录  
                //指定除自身之外的网络群体中其他提供同步的主机列表，用“|”分开不同的主机  
//                .cacheManagerPeerProviderFactory(new FactoryConfiguration<FactoryConfiguration<?>>()//  
//                        .className(RMICacheManagerPeerProviderFactory.class.getName())//  
//                        .properties("peerDiscovery=manual,rmiUrls=//localhost:40004/metaCache|//localhost:40005/metaCache")//  
//                )//  
                //配宿主主机配置监听程序  
//                .cacheManagerPeerListenerFactory(new FactoryConfiguration<FactoryConfiguration<?>>()//  
//                        .className(RMICacheManagerPeerListenerFactory.class.getName())//  
//                        .properties("port=40004,socketTimeoutMillis=2000")//  
//                )//  
                .cache(new CacheConfiguration("userToken", 5000)//缓存名称(必须唯一),maxElements内存最多可以存放的元素的数量  
                        .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU)//清理机制：LRU最近最少使用 FIFO先进先出 LFU较少使用  
                        .timeToIdleSeconds(0)//元素最大闲置时间  
                        .timeToLiveSeconds(tokenExpire)//元素最大生存时间  
                        .eternal(false)//元素是否永久缓存
                ).cache(new CacheConfiguration("permissions", 5000)
                    .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU) 
                    .timeToIdleSeconds(0)
                    .timeToLiveSeconds(permissionExpire) 
                    .eternal(false)
                ); 
        net.sf.ehcache.CacheManager manager = net.sf.ehcache.CacheManager.create(configuration);  
//       return new EhCacheCacheManager(manager);
        logger.info("ehCacheManagerr bean init success");
        return manager;
    }
   
      /**
       * 据shared与否的设置,
       * Spring分别通过CacheManager.create()
       * 或new CacheManager()方式来创建一个ehcache基地.
       *
       * 也说是说通过这个来设置cache的基地是这里的Spring独用,还是跟别的(如hibernate的Ehcache共享)
       *
       */
//      @Bean
//      public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(){
//        super.debug("CacheConfiguration.ehCacheManagerFactoryBean()");
//        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean ();
//        cacheManagerFactoryBean.setConfigLocation (new ClassPathResource("conf/ehcache.xml"));
//        cacheManagerFactoryBean.setShared(true);
//        return cacheManagerFactoryBean;
//      }
      
//      private String host;
//      private int port;
//      private int timeout;
//      private int maxIdle;
//      private long maxWaitMillis;
//      private String password;
//      
//      @Bean
//      public JedisPool redisPoolFactory() {
//          logger.info("JedisPool注入成功！！");
//          logger.info("redis地址：" + host + ":" + port);
//          JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//          jedisPoolConfig.setMaxIdle(maxIdle);
//          jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
//
//          JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
//
//          return jedisPool;
//      }
//      
      
      
      @Bean(name="jedisPoolConfig")
      @ConfigurationProperties(prefix="spring.redis.pool")
      public JedisPoolConfig getRedisConfig(){
          JedisPoolConfig config = new JedisPoolConfig();
          return config;
      }
      
      @Bean(name="jedisConnectionFactory")
      @ConfigurationProperties(prefix="spring.redis")
      public JedisConnectionFactory getConnectionFactory(){
          JedisConnectionFactory factory = new JedisConnectionFactory();
          JedisPoolConfig config = getRedisConfig();
          factory.setPoolConfig(config);
          logger.info("JedisConnectionFactory bean init success.");
          return factory;
      }
      
      
      @Bean
      public RedisTemplate<?, ?> getRedisTemplate(){
          RedisTemplate<?,?> template = new StringRedisTemplate(getConnectionFactory());
          return template;
      }

    @Override
    protected Class<?> getClassName() {
        return CacheConfig.class;
    }
}
