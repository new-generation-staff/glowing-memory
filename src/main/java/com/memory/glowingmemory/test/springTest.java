package com.memory.glowingmemory.test;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author zc
 */
public class springTest {

    @Test
    public void testUser() {
        //BeanFactory 加载配置文件的时候不会创建对象，使用时才会创建对象
        //BeanFactory context = new ClassPathXmlApplicationContext("beans/bean.xml");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans/bean.xml");
        User user = context.getBean("user", User.class);
        System.out.println("第四步 获取创建的bean对象");
        user.getUser();
        context.close();
    }

    @Test
    public void testBook() {
        //ApplicationContext 加载配置文件时就会创建对象
        ApplicationContext context = new ClassPathXmlApplicationContext("beans/bean.xml");
        Book book = context.getBean("book", Book.class);
        book.getBook();
    }
}
