package com.rohan.temp;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * Created by Rohan on 25-Oct-16.
 */

public interface APIService {

//    @POST("/WebServices/upload")
//    void uploadRequirementsMedia(@Body RequirementsMedia responseRequirements, Callback<ResponseRequirements> callback);

    @Multipart
    @POST("/WebServices/upload")
    void uploadRequirementsMedia(
            @Part("user_id") String userID,
            @Part("type_id") int typeID,
            @Part("scope") int scope,
            @Part("requirement_id") String requirementID,
            @Part("media") TypedFile media,
            Callback<ResponseRequirements> callback);
}
