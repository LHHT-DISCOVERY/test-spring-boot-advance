package com.example.demo.mutiple_thread;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class MyTaskImplementRunnable_coche1 implements Runnable{
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
