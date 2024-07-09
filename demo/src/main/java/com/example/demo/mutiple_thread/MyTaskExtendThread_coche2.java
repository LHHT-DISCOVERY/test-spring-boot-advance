package com.example.demo.mutiple_thread;

import lombok.*;
import lombok.experimental.FieldDefaults;


@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class MyTaskExtendThread_coche2 extends Thread{
    String name;
    int times;



    // task vụ muốn thực hiện đối với class này
    @Override
    public void run() {
        for (int i = 0; i < times; i++) {
            System.out.print(name);
        }
    }
}
