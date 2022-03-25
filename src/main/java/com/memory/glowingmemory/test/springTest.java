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

    @Test
    public void SecretKeySpec() throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        //32
        String key = "+WvwNIcNoISBgH9SL9qj5t8E4PuVIY6EK6OYkUYyRYs=";
        SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.decodeBase64(key), "AES");

        System.out.println(secretKeySpec.getEncoded().length);

        Cipher encryptCipher;
        Cipher decryptCipher;
        final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

        encryptCipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        encryptCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        decryptCipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        decryptCipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

    }


    @Test
    public void testLink() {
        String link = "?";
        String deepLink = "1111" + link;
        link = "&";

        if (true) {
            deepLink += "222" + link;
        }
        if (true) {
            deepLink += "333" + link;
        }
        if (true) {
            deepLink += "444" + link;
        }
        System.out.println(deepLink.substring(0, deepLink.length() - 1));
    }
}
