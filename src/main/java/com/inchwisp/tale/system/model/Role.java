package com.inchwisp.tale.system.model;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @ClassName: Role
 * @Description: 权限类，目前没用
 * @Author: MSI
 * @Date: 2018/12/28 18:35
 * @Vresion: 1.0.0
 **/
@Entity
public class Role {
    @Id@GeneratedValue
    private Integer id = 1;
    private String name;
    @NotFound(action= NotFoundAction.IGNORE)
    @ManyToOne
    User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
