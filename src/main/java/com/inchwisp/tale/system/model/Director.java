package com.inchwisp.tale.system.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: Director
 * @Description: TODO
 * @Author: MSI
 * @Date: 2019/1/18 14:57
 * @Vresion: 1.0.0
 **/
@Entity
@Table(name = "director")
public class Director {

    @Id
    @TableGenerator(name="DIRECTOR_GENERATOR",
            table="PK_GENERATOR",
            pkColumnName="PK_COLUMN",
            pkColumnValue="director",
            valueColumnName="PK_VALUE",
            initialValue=3000000,
            allocationSize=1
    )
    @GeneratedValue(strategy=GenerationType.TABLE, generator="DIRECTOR_GENERATOR")
    private Long id; //主键
    private String name; //姓名
    @Column(length = 1)
    private String sex; //性别
//    @Column(columnDefinition = "DATE")
//    private Date birthday; //出生日期
    private String birthday; //出生日期
    private String nationality; //国籍
    private String image; //头像路径
    ///——简介采用文本类型的JPA注解
    //@Lob
    //@Basic(fetch=FetchType.LAZY)
    //@Column(columnDefinition = "TEXT",nullable = true)
    @Column(length = 5000)
    private String introduction; //简介
    @OneToMany(mappedBy = "director")
    private List<Movie> movies; //关联电影集合

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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
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
