package com.inchwisp.tale.system.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * @ClassName: Performer
 * @Description: TODO
 * @Author: MSI
 * @Date: 2019/1/30 11:05
 * @Vresion: 1.0.0
 **/
@Entity
public class Performer {
    @Id
    @TableGenerator(name="PERFORMER_GENERATOR",
            table="PK_GENERATOR",
            pkColumnName="PK_COLUMN",
            pkColumnValue="performer",
            valueColumnName="PK_VALUE",
            initialValue=4000000,
            allocationSize=1
    )
    @GeneratedValue(strategy= GenerationType.TABLE, generator="PERFORMER_GENERATOR")
    private Long id; //主键
    private String name; //姓名
    @Column(length = 1)
    private String sex; //性别
    @Column(columnDefinition = "DATE")
    private Date birthday; //出生日期
    private String nationality; //国籍
    private String image; //头像路径
    ///——简介采用文本类型的JPA注解
    //@Lob
    //@Basic(fetch=FetchType.LAZY)
    //@Column(columnDefinition = "TEXT",nullable = true)
    @Column(length = 5000)
    private String introduction; //简介

    @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.LAZY,mappedBy = "performers")
    @JsonBackReference
    private List<Movie> movies; //电影集合

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
