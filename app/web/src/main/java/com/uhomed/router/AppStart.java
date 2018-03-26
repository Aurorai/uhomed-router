package com.uhomed.router;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * @author
 * @version $$Id: , v 0.1    Exp $$
 */
@SpringBootApplication
//@MapperScan("com.uhomed.router.dal.dao")
@ImportResource({"classpath:config/*/spring-*.xml"})
//,"classpath:config/spring/spring-mvc-servlet.xml"
//
//@PropertySource("classpath:/config/${spring.profiles.active}/jdbc.properties")
@PropertySource("file:${user.dir}/config/${spring.profiles.active}/jdbc.properties")
public class AppStart {

    private static String[] DEFAULT_ARGS = null;

    static {
        //设置开发环境
        DEFAULT_ARGS = new String[]{
                "--spring.profiles.active=dev",
//                "--Ddubbo.shutdown.hook=true"
        };
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AppStart.class);

        if (args.length == 0) {
            args = DEFAULT_ARGS;
        }

        app.run(args);

    }


    /*@Bean
    public HttpMessageConverters fastJsonHttpMessageConverters(){
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.PrettyFormat);
        converter.setFastJsonConfig(config);
        return new HttpMessageConverters(converter);
    }*/


}
