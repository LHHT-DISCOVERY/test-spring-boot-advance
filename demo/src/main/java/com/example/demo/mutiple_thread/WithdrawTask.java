package com.example.demo.mutiple_thread;

public class WithdrawTask implements Runnable {
    private Account account;

    public WithdrawTask(Account account) {
        this.account = account;
    }


    // cho chạy đa luồng, mỗi lần rút 10 đồng
    @Override
    public void run() {
        try {
            this.account.withdraw(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
