package com.inchwisp.tale.system.model;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Date;

/**
 * @ClassName: Movie
 * @Description: TODO
 * @Author: MSI
 * @Date: 2019/1/18 15:16
 * @Vresion: 1.0.0
 **/
@Entity
@Table(name = "movie")
public class Movie {

    //@Id @GeneratedValue()
    //@Column(length = 8)
    @Id
    @TableGenerator(name="USER_GENERATOR",
            table="PK_GENERATOR", //对应第三方主键生成表名
            pkColumnName="PK_COLUMN", //指定第三方表中对应的某个列名
            pkColumnValue="movie", //指定第三方表中对应的某个列的值，某个列指 pkColumnName属性中的指定的列名
            valueColumnName="PK_VALUE", //指定生成的列名
            initialValue=2000000, //ID序列起始值
            allocationSize=1 //分配大小，指主键增长步长。
    )
    @GeneratedValue(strategy=GenerationType.TABLE, generator="USER_GENERATOR")
    private Long id;
    private String name;
    private String alias;
    private String region;
    private Date releaseDate;
    private String image;
    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "director_id")
    private Director director;

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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }
}
