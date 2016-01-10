package org.dptech.wx.sdk.cache;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 
 * @author lichao
 * 满足集群分片
 */
public class RedisDBUtil extends RedisSource{

	private static Logger logger = LoggerFactory.getLogger(RedisDBUtil.class);
	private static JedisSentinelPool pool;
	private static ShardedJedisPool sharededPool;
	
	static {
		if("shared".equals(mode)){
			initSharededPool();
		} else if("sentinel".equals(mode)) {
			initSentinelPool();
		}
	}
	
	private static void initSentinelPool(){
		logger.info("开始初始化redis连接池");
		String[] addressArr = redis_address.split(":");
		Set<String> set = new HashSet<String>();
		set.add(addressArr[0] + ":" + addressArr[1]);
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxIdle(Integer.valueOf(max_idle));
		config.setMaxTotal(Integer.valueOf(max_active));
		config.setMaxWaitMillis(Long.valueOf(max_wait));
		config.setTestOnBorrow(Boolean.valueOf(isTest));
		pool = new JedisSentinelPool(addressArr[2], set, config);
		logger.info("成功初始化Sentinel redis连接池");
	}
	
	private static void initSharededPool(){
		logger.info("开始初始化redis连接池");
		JedisPoolConfig config = new JedisPoolConfig();
//		config.setMaxActive(Integer.valueOf(max_active));
		config.setMaxTotal(Integer.valueOf(max_active) + Integer.valueOf(max_idle));
		config.setMaxIdle(Integer.valueOf(max_idle));
//		config.setMaxWait(Integer.valueOf(max_wait));
		config.setTestOnBorrow(Boolean.valueOf(isTest));
		List<JedisShardInfo> list = new ArrayList<JedisShardInfo>();
		String[] addressArr = redis_address.split(",");
		for (String str : addressArr) {
			JedisShardInfo shardInfo = new JedisShardInfo(str.split(":")[0], Integer.parseInt(str.split(":")[1]),str.split(":")[2]);
			shardInfo.setTimeout(Integer.valueOf(timeout));
			list.add(shardInfo);
		}
		sharededPool = new ShardedJedisPool(config, list);
		logger.info("成功初始化shared redis连接池");
	}
	
	public static JedisCommands getRedisTemplate() {
		JedisCommands jedis = null;
		if("shared".equals(mode)){
			jedis = sharededPool.getResource();
		} else if("sentinel".equals(mode)) {
			jedis = pool.getResource();
		}
		return jedis;
	}
	
	/**
	 * 正常连接池回收
	 * @param jedis
	 */
	public static void closeJedis(JedisCommands jedis){
		if(jedis != null){
			if("shared".equals(mode) && (jedis instanceof ShardedJedis)){
				sharededPool.returnResource((ShardedJedis) jedis);
			} else if("sentinel".equals(mode) && (jedis instanceof Jedis)){
				pool.returnResource((Jedis) jedis);
			}
		}
	}
	
	/**
	 * 异常连接池回收
	 * @param jedis
	 */
	public static void closeBreakJedis(JedisCommands jedis){
		if(jedis != null){
			if("shared".equals(mode) && (jedis instanceof ShardedJedis)){
				sharededPool.returnBrokenResource((ShardedJedis) jedis);
			} else if("sentinel".equals(mode) && (jedis instanceof Jedis)){
				pool.returnBrokenResource((Jedis) jedis);
			}
		}
	}
}