package com.ibtikar.apps.task.ui.profile;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ibtikar.apps.task.api.ActorsClient;
import com.ibtikar.apps.task.pojo.ActorsProfiles;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ProfileViewModel extends ViewModel {

    private static final String TAG = "ProfileViewModel";
    public MutableLiveData<List<ActorsProfiles.Profile>> data = new MutableLiveData<>();

    public void getProfileImages(int id, String api_key) {

        Single singleObservable = ActorsClient.getINSTANCE().getProfilesImg(id, api_key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        singleObservable.subscribeWith(new DisposableSingleObserver<ActorsProfiles>() {

            @Override
            public void onSuccess(ActorsProfiles actorsProfiles) {
                data.setValue(actorsProfiles.getProfiles());
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e.getMessage());
            }
        });
    }
}
