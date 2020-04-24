package juc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author  wyg_edu
 * @date  2020年4月24日 上午8:18:26
 * @version 1.0
 * 读写锁
 * 读读共享
 * 读写不共存
 * 写写不共存
 * 写操作 ： 原子性，独占性
 */
public class ReadWriteLockDemo {
	public static void main(String[] args) {
		MyCache cache = new MyCache();
		
		for(int i=0;i<5;i++) {
			final int tempInt = i;
			
			new Thread(()->{
				try {
					cache.put(tempInt+"", tempInt+"");
				} catch (Exception e) {
					e.printStackTrace();
				}
			},"Write Thread"+i).start();
		}
		
		for(int i=0;i<5;i++) {
			final int tempInt = i;
			
			new Thread(()->{
				try {
					cache.get(String.valueOf(tempInt));
				} catch (Exception e) {
					e.printStackTrace();
				}
			},"Read Thread"+i).start();
		}
		
	}

}

/**
 * 资源类
 * @author a1553
 *
 */
class MyCache{
	
	private volatile Map<String, Object> map = new HashMap<String, Object>();
	
	private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
	
	public void put(String key,Object value) throws Exception {
		
		rwLock.writeLock().lock();
		
		try {
			System.out.println(Thread.currentThread().getName()+"正在写入key:"+ key+" value: " +value);
			TimeUnit.SECONDS.sleep(1);
			map.put(key, value);
			System.out.println(Thread.currentThread().getName()+"写入完成key:"+ key+" value: " +value);
		} catch (Exception e) {
			rwLock.writeLock().unlock();
		}
		
	}
	
	public void get(String key) throws Exception {
		rwLock.readLock().lock();
		
		try {
			System.out.println(Thread.currentThread().getName()+"正在读取key:"+ key);
			TimeUnit.SECONDS.sleep(1);
			Object result = map.get(key);
			System.out.println(Thread.currentThread().getName()+"读取完成key:"+ key+" result: " +result);
		} catch (Exception e) {
			rwLock.readLock().unlock();
		}
		
	}
	
	public void clearMap() {
		map.clear();
	}
	
}
