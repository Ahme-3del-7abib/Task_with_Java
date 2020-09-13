package com.ibtikar.apps.task.api;

import com.ibtikar.apps.task.Utils.ConstantsUtils;
import com.ibtikar.apps.task.pojo.Actors;
import com.ibtikar.apps.task.pojo.ActorsProfiles;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActorsClient {

    private ActorsInterface actorsInterface;
    private static ActorsClient INSTANCE;

    public ActorsClient() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantsUtils.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        actorsInterface = retrofit.create(ActorsInterface.class);
    }

    public static synchronized ActorsClient getINSTANCE() {

        if (null == INSTANCE) {
            INSTANCE = new ActorsClient();
        }
        return INSTANCE;
    }

    public Single<Actors> getActors(String category, String api_key, String language, int page) {
        return actorsInterface.getActors(category, api_key, language, page);
    }

    public Single<ActorsProfiles> getProfilesImg(int id, String api_key) {
        return actorsInterface.getProfilesImg(id, api_key);
    }
}
