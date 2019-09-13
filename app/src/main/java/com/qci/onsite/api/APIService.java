package com.qci.onsite.api;



import com.qci.onsite.pojo.AllocatedHospitalListPojo;
import com.qci.onsite.pojo.AllocatedHospitalResponse;
import com.qci.onsite.pojo.AssesorAccept_RejectResponse;
import com.qci.onsite.pojo.DataSyncRequest;
import com.qci.onsite.pojo.DataSyncResponse;
import com.qci.onsite.pojo.ImageUploadResponse;
import com.qci.onsite.pojo.LoginRequest;
import com.qci.onsite.pojo.LoginResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

/**
 * Created by raj on 3/16/2018.
 */

public interface APIService {

    @POST("tokens")
    Call<LoginResponse> loginrequest(@Header("Content-Type") String Content_Type, @Body LoginRequest request);

    @GET
    Call<AllocatedHospitalResponse> getHospitalList(@Header("Content-Type") String Content_Type, @Header("Authorization") String Authorization, @Url String url);

    @POST("assessors_app")
    Call<AssesorAccept_RejectResponse>PostAssessorResponse(@Header("Content-Type") String Content_Type, @Header("Authorization") String Authorization, @Body AllocatedHospitalListPojo pojo);

    @Multipart
    @POST("upload/s3")
    Call<ImageUploadResponse>ImageUploadRequest(@Header("Authorization") String Authorization, @Part MultipartBody.Part file);

    @POST("assessments_app/assessmentresults")
    Call<DataSyncResponse>DataSync(@Header("Content-Type") String Content_Type, @Header("Authorization") String Authorization, @Body DataSyncRequest pojo);

}
