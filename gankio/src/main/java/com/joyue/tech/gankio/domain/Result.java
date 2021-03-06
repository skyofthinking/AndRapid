package com.joyue.tech.gankio.domain;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result extends MultiItemEntity {

    public static final int TEXT = 1;
    public static final int IMG = 2;

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("publishedAt")
    @Expose
    private String publishedAt;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("used")
    @Expose
    private String used;
    @SerializedName("who")
    @Expose
    private String who;

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The _id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt The createdAt
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return The desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc The desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return The publishedAt
     */
    public String getPublishedAt() {
        return publishedAt;
    }

    /**
     * @param publishedAt The publishedAt
     */
    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    /**
     * @return The source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source The source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return The used
     */
    public String getUsed() {
        return used;
    }

    /**
     * @param used The used
     */
    public void setUsed(String used) {
        this.used = used;
    }

    /**
     * @return The who
     */
    public String getWho() {
        return who;
    }

    /**
     * @param who The who
     */
    public void setWho(String who) {
        this.who = who;
    }

    @SerializedName("Android")
    @Expose
    private List<DayResult> android = null;
    @SerializedName("iOS")
    @Expose
    private List<DayResult> ios = null;
    @SerializedName("休息视频")
    @Expose
    private List<DayResult> video = null;
    @SerializedName("福利")
    @Expose
    private List<DayResult> meizi = null;
    @SerializedName("拓展资源")
    @Expose
    private List<DayResult> expandres = null;
    @SerializedName("前端")
    @Expose
    private List<DayResult> frontend = null;
    @SerializedName("瞎推荐")
    @Expose
    private List<DayResult> recommend = null;
    @SerializedName("App")
    @Expose
    private List<DayResult> app = null;

    public List<DayResult> getAndroid() {
        return android;
    }

    public void setAndroid(List<DayResult> android) {
        this.android = android;
    }

    public List<DayResult> getIos() {
        return ios;
    }

    public void setIos(List<DayResult> ios) {
        this.ios = ios;
    }

    public List<DayResult> getVideo() {
        return video;
    }

    public void setVideo(List<DayResult> video) {
        this.video = video;
    }

    public List<DayResult> getMeizi() {
        return meizi;
    }

    public void setMeizi(List<DayResult> meizi) {
        this.meizi = meizi;
    }

    public List<DayResult> getExpandres() {
        return expandres;
    }

    public void setExpandres(List<DayResult> expandres) {
        this.expandres = expandres;
    }

    public List<DayResult> getFrontend() {
        return frontend;
    }

    public void setFrontend(List<DayResult> frontend) {
        this.frontend = frontend;
    }

    public List<DayResult> getRecommend() {
        return recommend;
    }

    public void setRecommend(List<DayResult> recommend) {
        this.recommend = recommend;
    }

    public List<DayResult> getApp() {
        return app;
    }

    public void setApp(List<DayResult> app) {
        this.app = app;
    }
}
