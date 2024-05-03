package com.example.demo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class DemoApplication {
//    @Autowired
//    private IService<Book> bookService;
//    @Autowired
//    private IService<Role> roleService;
//    @Autowired
//    private IService<User> userService;

    private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(new Class[]{DemoApplication.class});
        app.setBannerMode(Banner.Mode.CONSOLE);
        Environment env = app.run(args).getEnvironment();
        log.info("Access URLs:\n ------------------------------------------------\n\tLocalhost: http://localhost:{} " +
                        "\n \tExternal:  http://{}:{}\n ------------------------------------------------",
                new Object[]{
                        env.getProperty("server.port"),
                        InetAddress.getLocalHost().getHostAddress(),
                        env.getProperty("server.port")
                }
        );
    }

//    @Override
//    public void run(String... args) throws Exception {
//        roleService.saveOrUpdate(Role.builder().id(1).name("admin").build());
//        roleService.saveOrUpdate(Role.builder().id(2).name("user").build());
//
//        User user1 = new User();
//        user1.setEmail("test@user.com");
//        user1.setName("Test User");
//        user1.setMobile("9787456545");
//        user1.setRole(roleService.findById(2).get());
//        user1.setPassword(new BCryptPasswordEncoder().encode("testuser"));
//        userService.saveOrUpdate(user1);
//
//        User user2 = new User();
//        user2.setEmail("test@admin.com");
//        user2.setName("Test Admin");
//        user2.setMobile("9787456545");
//        user2.setRole(roleService.findById(1).get());
//        user2.setPassword(new BCryptPasswordEncoder().encode("testadmin"));
//        userService.saveOrUpdate(user2);
//
//        Book book = new Book();
//        book.setTitle("Spring Microservices in Action");
//        book.setAuthor("John Carnell");
//        book.setCoverPhotoURL("https://images-na.ssl-images-amazon.com/images/I/417zLTa1uqL._SX397_BO1,204,203,200_.jpg");
//        book.setIsbnNumber(1617293989L);
//        book.setPrice(2776.00);
//        book.setLanguage("English");
//        bookService.saveOrUpdate(book);
//    }//..,,.op2,op3,tro,tri2,test2-commit-first
//(())
//    tri_1,12, 1212312 ,tri_1,12,11
//    }
//    neww 1243
//    á dfgasdf---123
//    123----123
}
