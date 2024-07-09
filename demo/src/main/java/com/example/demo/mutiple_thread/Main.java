package com.example.demo.mutiple_thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {

        // ************* CHẠY ĐƠN LUỒNG *************
//        MyTask task1 = new MyTaskImplementRunnable_coche1("a", 50);
//        MyTask task2 = new MyTaskImplementRunnable_coche1("b", 10);
//        task1.run();
//        task2.run();
        // giải thích : chạy từ trên xuống dưới , hết task 1 , thì nhảy vào chạy task 2

        // **********************************************************************





        // ************* CHẠY ĐA LUỒNG THEO CƠ CHẾ RUNNABLE *************
        // nghĩa là task 1 chưa chạy xong nhưng nếu cpu thấy task1 rảnh thì task2 nhảy vào chạy
        // cứ xen kẽ đến khi hoàn thành cả 2 task

        // Kiểu dữ liệu là thèn cha(là Runnable được implement) và new thèn con để tuân theo nguyên lý solid phụ thuộc thèn cha
//        Runnable task1 = new MyTaskImplementRunnable_coche1("a", 50);
//        Thread t1 = new Thread(task1); // tạo Thread
//        Runnable task2 = new MyTaskImplementRunnable_coche1("b", 10);
//        Thread t2 = new Thread(task2); // tạo Thread

        // thực thi Thread
        // start() tự động kích hoạt Thread và tự chạy phương thức run() được overriding trong class MyTask
//        t1.start();
//        t2.start();
        // giải thích: khi đang chạy Thread t1, cứ nếu  cpu mà rảnh là thread t2 nhảy vào chạy ,
        // tiếp tục đang chay Thread t2 mà nếu CPU lại rảnh , lại nhảy vào tiếp tục công việc của Thread t1 đang chạy dở mà nghĩ trước đó
        // chứ ko đợi Thread t1 hoàn thành rồi Thread t2 mới vào chạy (t1 vs t2 chạy song song đến khi 2 task hoàn thành)

        // **********************************************************************






        // ************* CHẠY ĐA LUỒNG THEO CƠ CHẾ EXTEND THREAD *************


//        Thread t1 = new MyTaskExtendThread_coche2("a" , 50); // tạo Thread
//        Thread t2 = new MyTaskExtendThread_coche2("b" , 50); // tạo Thread
//        t1.start(); // kích hoạt Thread
//        t2.start(); // kích hoạt Thread

        // **********************************************************************






        // ************* CHẠY ĐA LUỒNG THEO CƠ CHẾ THREAD POOL CƠ CHẾ 1 *************


        // tạo pool với 2 thread cố định
        ExecutorService executorService = Executors.newFixedThreadPool(2); // cơ chế 1 : chờ đến khi Thread trong pool trống thì vào

        // đặc các task vào excutor
        // tuy đặt 3 nhưng tạo 2 Thread, 2 đồng chí 1 vs 2,  đồng chí nào xong trước thì đồng chí 3 sẽ
        // nhào vào đồng chí đó để chạy
//        executorService.execute(new MyTaskExtendThread_coche2("a", 50)); // đồng chí 1, tạo thread 1
//        executorService.execute(new MyTaskExtendThread_coche2("b", 10)); // đồng chí 2, tạo thread 2
//        executorService.execute(new MyTaskExtendThread_coche2("c", 5)); // đồng chí 3, ko đc tạo thread mới ở đây nữa
        // nghĩa là đồng chí 3 đứng chờ miết đến khi nào mà đồng chí 1 hoặc 2 xong thì nhảy vào chỗ trống đó để làm

        // ngừng excutor
        executorService.shutdown();

        // **********************************************************************







        // ************* CHẠY ĐA LUỒNG THEO CƠ CHẾ THREAD POOL CƠ CHẾ 2 *************


        // cơ chế 2 : ko chờ, nếu Thread trong pool bận hết thì tạo Thread mới, còn nếu có Thread rảnh thì nhảy vào làm ko tạo mới Thread
        ExecutorService executorService2 = Executors.newCachedThreadPool();

        // đặc các task vào excutor
        // tuy đặt 3 nhưng tạo 2 Thread, 2 đồng chí 1 vs 2,  đồng chí nào xong trước thì đồng chí 3 sẽ
        // nhào vào đồng chí đó để chạy
//        executorService2.execute(new MyTaskExtendThread_coche2("a", 50)); // đồng chí 1, tạo thread 1
//        executorService2.execute(new MyTaskExtendThread_coche2("b", 10)); // đồng chí 2, tạo thread 2
//        executorService2.execute(new MyTaskExtendThread_coche2("c", 5)); // đồng chí 3, sẽ tạo thread 3 nếu như thấy thread 1 vs 2 ko còn trống nữa
        // nghĩa là chạy thread "a" xong chạy tiếp thread "b" nhưng khi định tạo tiếp thread "c" để chạy đồng chí 3
        // thì thấy thread "b" xong rồi, đang rảnh, thì lập tức bỏ ý định tạo => ko tạo thread "c" mà nhảy vào thread "b" để chạy
        // => đó là cơ chế pool thứ 2

        // ngừng excutor
//        executorService2.shutdown();
        // **********************************************************************




        // ************* CHẠY ĐA LUỒNG THEO VẤN ĐỀ TRANH CHẤP DỮ LIỆU *************

        Account account = new Account(15);
        // 2 Thread đều rút trên accout (tranh chấp dữ liệu trên account)
        // lần đầu ở sg rút 10 đồng rồi
        Runnable task1 = new WithdrawTask(account); // ta chạy đa luồng cho phương thức withdraw
        Thread ts1 = new Thread(task1);
        // cùng lúc đó ở QN cx rút 10 đồng -> ko rút đc do ko đủ tiền (nếu chạy đơn luồng sẽ lỗi ko rút đc)
        // nhưng chúng ta chạy đa luồng -> đều rút đc => cần sài synchronized trogn method để tránh trường hợp tranh chấp tài nguyên
        Runnable task2 = new WithdrawTask(account);
        Thread ts2 = new Thread(task2);

        // Lưu ý synchronized được đánh dấu ở method nghĩa là tại một thời điểm chỉ có 1 thread đc vào function để chạy thôi
        // khi chúng ta nghi ngờ phương thức nào chạy đa luồng có vấn đề thì bật synchronized lên, nó sẽ khóa các đối tượng nó tương tác trong phương thức đó ,
        // ko ai động vào đối tượng đó đc

        ts1.start();
        ts2.start();

    }
}
