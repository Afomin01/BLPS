package com.example.blps.util;

import com.example.blps.model.xml.UsersXml;
import lombok.SneakyThrows;

import javax.xml.bind.JAXBContext;
import java.io.File;
import java.io.FileReader;
import java.net.URL;

public class UserLoader {
    private static final String FILENAME = "users.xml";

    @SneakyThrows
    public static UsersXml loadUsers() {
        URL url = ClassLoader.getSystemResource(FILENAME);
        FileReader reader = new FileReader(new File(url.toURI()));

        JAXBContext context = JAXBContext.newInstance(UsersXml.class);
        return (UsersXml) context.createUnmarshaller().unmarshal(reader);
    }
}
