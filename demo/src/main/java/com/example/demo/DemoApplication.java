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
}
