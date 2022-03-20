package com.example.blps.util;

import com.example.blps.model.xml.UsersXml;
import org.springframework.core.io.ClassPathResource;

import javax.xml.bind.JAXBContext;
import java.io.InputStreamReader;

public class UserLoader {
    private static final String FILENAME = "users.xml";

    public static UsersXml loadUsers() throws Exception {
        InputStreamReader reader = new InputStreamReader(new ClassPathResource(FILENAME).getInputStream());

        JAXBContext context = JAXBContext.newInstance(UsersXml.class);
        return (UsersXml) context.createUnmarshaller().unmarshal(reader);
    }
}
