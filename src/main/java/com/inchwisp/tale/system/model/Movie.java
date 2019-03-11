package com.inchwisp.tale.system.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: Movie
 * @Description: 电影实体模型类
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
    private String name; //名称
    private String alias = "无"; //别名
    private String region; //地区
    private String language; //语言
    private String tag; //标签
    private String image;//海报地址
    private String introduction; //简介
    private String resource; //资源链接
    @Column(precision = 1, scale = 2)
    private float score; //评分

    @Temporal(TemporalType.TIME)
    private Date lasts; //片长
    @Temporal(TemporalType.DATE)
    private Date releaseDate; //上映时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime = new Date(); //上架时间

    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER,optional = true)
    @JsonBackReference
    private Director director; //关联导演ID

    /*@JoinTable(
            name = "movie_performer",
            joinColumns = {@JoinColumn(name = "movie",referencedColumnName = "performer")},
            inverseJoinColumns = {@JoinColumn(name = "performer",referencedColumnName = "movie")}
    )*/
    @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Performer> performers; //演员ID，多对多

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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Date getLasts() {
        return lasts;
    }

    public void setLasts(Date lasts) {
        this.lasts = lasts;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public List<Performer> getPerformers() {
        return performers;
    }

    public void setPerformers(List<Performer> performers) {
        this.performers = performers;
    }
}
