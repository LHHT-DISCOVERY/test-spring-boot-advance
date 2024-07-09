package com.example.demo.mutiple_thread;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Account { // tạo account và rút tiền
    String name;
    double amount;

    public Account(double amount) {
        this.amount = amount;
    }

    // kiểm tra đủ điều kiện thì vào cho rút , nhưng khi vào rút rồi mà bị chập mạng, thèn khác cx vào cùng tài nguyên cx vào rút đc
    // khi chạy mutiple-Thread sảy ra vấn đề ở đây
    public synchronized void withdraw(double a) throws InterruptedException {
        if (this.getAmount() >= a){
            Thread.sleep(1000); // giả sử chạy mutiple- thread bị lỗi mạng , dừng 1s , để tranh chấp dữ liệu
            this.setAmount(this.getAmount() - a);
            System.out.println("withdraw successful : " + a + " remain balance: " + this.getAmount());
        }else {
            System.out.println("balance not enough");
        }
    }

}
