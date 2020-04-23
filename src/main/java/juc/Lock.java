package juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class Lock {
	
	static Lock lock = new Lock();

	AtomicReference<Thread> atomicReference = new AtomicReference<Thread>();
	public void lock() {
		while(!atomicReference.compareAndSet(null, Thread.currentThread()));
		System.out.println(Thread.currentThread().getName()+"\t lock");
	}
	
	public void UnLock() {
		while(!atomicReference.compareAndSet(Thread.currentThread(), null));
		System.out.println(Thread.currentThread().getName()+"\t UnLock");
	}
	

	
	public static void main(String[] args) throws Exception {
		new Thread(()->{
			lock.lock();
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lock.UnLock();
		},"AA").start();
		TimeUnit.SECONDS.sleep(1);
		new Thread(()->{
			lock.lock();
			lock.UnLock();
		},"BB").start();

	}
}

