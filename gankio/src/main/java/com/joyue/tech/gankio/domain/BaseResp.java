package com.joyue.tech.gankio.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author JiangYH
 */

public class BaseResp<T>  {
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("results")
    @Expose
    private T results = null;

    /**
     * @return The error
     */
    public String getError() {
        return error;
    }

    /**
     * @param error The error
     */
    public void setError(String error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
