package com.joyue.tech.gankio.api;

import com.joyue.tech.gankio.domain.BaseResp;
import com.joyue.tech.gankio.domain.Result;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author JiangYH
 */

public interface ApiService {

    // 所有干货，支持配图数据返回 （除搜索 Api）
    @GET("api/data/{category}/{count}/{page}")
    Observable<BaseResp<List<Result>>> date(@Path("category") String category, @Path("count") int count, @Path("page") int page);

    // 每日数据
    @GET("api/day/{year}/{month}/{day}")
    Observable<BaseResp<Result>> day(@Path("year") String year, @Path("month") String month, @Path("day") String day);

    // 搜索 API
    @GET("api/search/query/listview/category/{category}/count/{count}/page/{page}")
    Observable<BaseResp<List<Result>>> search(@Path("category") String category, @Path("count") int count, @Path("page") int page);

    // 获取某几日干货网站数据
    @GET("api/history/content/{count}/{page}")
    Observable<BaseResp<List<Result>>> history(@Path("count") int count, @Path("page") int page);

    // 获取特定日期网站数据
    @GET("api/history/content/day/{year}/{month}/{day}")
    Observable<BaseResp<List<Result>>> history(@Path("year") String year, @Path("month") String month, @Path("day") String day);

    // 获取发过干货日期
    @GET("api/day/history")
    Observable<BaseResp<String[]>> history();

    // 随机数据
    @GET("api/random/data/{category}/{count}")
    Observable<BaseResp<List<Result>>> random(@Path("category") String category, @Path("count") int count);

    // 提交数据
    /**
     * url 想要提交的网页地址
     * desc 对干货内容的描述 单独的文字描述
     * who 提交者ID 干货提交者的网络ID
     * type 干货类型 可选参数: Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
     * debug 当前提交为测试数据 如果想要测试数据是否合法, 请设置 debug 为 true 可选参数: true | false
     */
    @FormUrlEncoded
    @POST("api/add2gank")
    Observable<BaseResp<List<Result>>> add2gank(@Field("url") String url, @Field("desc") String desc, @Field("who") String who, @Field("type") String type, @Field("debug") String debug);
}
