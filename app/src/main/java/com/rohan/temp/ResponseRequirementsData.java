package com.rohan.temp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rohan on 25-Oct-16.
 */

public class ResponseRequirementsData {

    @SerializedName("id")
    @Expose
    private String id;

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

}
