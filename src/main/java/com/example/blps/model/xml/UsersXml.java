package com.example.blps.model.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlRootElement(name = "userManagement")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersXml {
    @XmlElement(name = "user")
    @XmlElementWrapper(name = "users")
    private List<UserDetailsXmlImpl> users;

    public List<UserDetailsXmlImpl> getUsers() {
        return users;
    }

    public void setUsers(List<UserDetailsXmlImpl> users) {
        this.users = users;
    }
}
