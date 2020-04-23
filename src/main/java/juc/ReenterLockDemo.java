package juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wis_edu
 * @version 1.0
 * @date 2020年4月22日
 */
public class ReenterLockDemo {
	public static void main(String[] args) throws Exception {

		Phone phone = new Phone();

		new Thread(() -> {
			try {
				phone.sendSMS();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, "t1").start();

		new Thread(() -> {
			try {
				phone.sendSMS();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, "t2").start();

		TimeUnit.SECONDS.sleep(2);

		Thread t3 = new Thread(phone, "t2");
		Thread t4 = new Thread(phone, "t4");
		t4.start();
		t3.start();
	}
}

class Phone implements Runnable {
	public synchronized void sendSMS() throws Exception {
		System.out.println(Thread.currentThread().getName() + "\t invoked SendSMS()");
		sendEMail();
	}

	public synchronized void sendEMail() throws Exception {
		System.out.println(Thread.currentThread().getName() + "\t invoked sendEMail()=========");
	}

	Lock lock = new ReentrantLock();

	@Override
	public void run() {
		get();
	}

	private void get() {
		lock.lock();
		try {
			set();
		} finally {
			lock.unlock();
		}

	}

	private void set() {
		lock.lock();
		try {
			System.out.println(Thread.currentThread().getName() + "\t invoked set()(*&(&(&(*&(&(&(&(*");
		} finally {
			lock.unlock();
		}

	}
}