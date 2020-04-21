package juc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author wyg_edu
 * @version 1.0
 * @date 2020/4/21 8:25
 */
public class CopyOnWriteArrayList {

    public static void main(String[] args) {
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
