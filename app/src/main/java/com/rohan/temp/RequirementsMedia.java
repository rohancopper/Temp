package com.rohan.temp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit.mime.TypedFile;

/**
 * Created by Rohan on 25-Oct-16.
 */

public class RequirementsMedia {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("type_id")
    @Expose
    private Integer typeId;
    @SerializedName("scope")
    @Expose
    private Integer scope;
    @SerializedName("requirement_id")
    @Expose
    private String requirementId;
    @SerializedName("media")
    @Expose
    private String media;

    /**
     * @return The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId The user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return The typeId
     */
    public Integer getTypeId() {
        return typeId;
    }

    /**
     * @param typeId The type_id
     */
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    /**
     * @return The scope
     */
    public Integer getScope() {
        return scope;
    }

    /**
     * @param scope The scope
     */
    public void setScope(Integer scope) {
        this.scope = scope;
    }

    /**
     * @return The requirementId
     */
    public String getRequirementId() {
        return requirementId;
    }

    /**
     * @param requirementId The requirement_id
     */
    public void setRequirementId(String requirementId) {
        this.requirementId = requirementId;
    }

    /**
     * @return The media
     */
    public String getMedia() {
        return media;
    }

    /**
     * @param media The media
     */
    public void setMedia(String media) {
        this.media = media;
    }

    public RequirementsMedia(String userId, Integer typeId, Integer scope, String requirementId, String media) {
        this.userId = userId;
        this.typeId = typeId;
        this.scope = scope;
        this.requirementId = requirementId;
        this.media = media;
    }
}
