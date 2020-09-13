package com.ibtikar.apps.task.api;

import com.ibtikar.apps.task.pojo.Actors;
import com.ibtikar.apps.task.pojo.ActorsProfiles;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ActorsInterface {


    @GET("/3/person/{category}")
    Single<Actors> getActors(
            @Path("category") String category,
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("/3/person/{person_id}/images")
    Single<ActorsProfiles> getProfilesImg(
            @Path("person_id") int id,
            @Query("api_key") String api_key
    );
}
