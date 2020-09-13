package com.ibtikar.apps.task.ui.main;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ibtikar.apps.task.api.ActorsClient;
import com.ibtikar.apps.task.pojo.Actors;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ActorsViewModel extends ViewModel {

    private static final String TAG = "ActorsViewModel";
    public MutableLiveData<List<Actors.Result>> data = new MutableLiveData<>();

    public void getActors(String category, String api_key, String language, int page) {


        Single singleObservable = ActorsClient.getINSTANCE().getActors(category, api_key, language, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        singleObservable.subscribeWith(new DisposableSingleObserver<Actors>() {
            @Override
            public void onSuccess(Actors actors) {
                data.setValue(actors.getResults());
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e.getMessage());
            }
        });
    }
}
