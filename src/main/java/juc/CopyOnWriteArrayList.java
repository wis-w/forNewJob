package juc;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author wyg_edu
 * @version 1.0
 * @date 2020/4/21 8:25
 * 集合不安全类问题
 */
public class CopyOnWriteArrayList {

    public static void main(String[] args) {
//        Map<String,Object> map = new HashMap();
        Map<String, Object> map =new ConcurrentHashMap <String, Object>();

        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 8));
                System.out.println(map);
            }).start();

        }
    }

    private static void setNotSafe() {
        // Set<String> set = new HashSet<String>();  // 线程不安全
        // Set<String> set = Collections.synchronizedSet(new HashSet<String>()); // 线程安全
        Set<String> set = new CopyOnWriteArraySet<String>(); //线程安全 读写分离 底层是CopyOnWriteArrayList
        for (int i = 1; i < 100; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(set);
            }, "Thread:" + i).start();
        }
    }

    private static void listNotSafe() {
        List<String> list = new java.util.concurrent.CopyOnWriteArrayList<String>();
//        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }).start();

        }
    }

}
