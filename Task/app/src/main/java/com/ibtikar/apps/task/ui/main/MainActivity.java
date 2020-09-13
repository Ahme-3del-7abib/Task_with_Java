package com.ibtikar.apps.task.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

import com.ibtikar.apps.task.R;
import com.ibtikar.apps.task.Utils.ConstantsUtils;
import com.ibtikar.apps.task.databinding.ActivityMainBinding;
import com.ibtikar.apps.task.pojo.Actors;
import com.ibtikar.apps.task.ui.profile.ProfileActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener, MyAdapter.OnActorClickListener {

    private ActivityMainBinding binding;
    private ActorsViewModel viewModel;
    private MyAdapter adapter;
    List<Actors.Result> resultList = new ArrayList<>();
    private Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setView();

        if (ConstantsUtils.isNetworkConnected(this)) {
            getActors();

        } else {
            showEmptyView();
        }
    }

    private void showEmptyView() {
        binding.EmptyMsg.setVisibility(View.VISIBLE);
        binding.actorsRecyclerId.setVisibility(View.INVISIBLE);
    }

    private void setView() {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(ActorsViewModel.class);

        typeface = Typeface.createFromAsset(getAssets(), "fonts/fontfamily.ttf");
        binding.toolBarTv.setTypeface(typeface);
        binding.EmptyMsg.setTypeface(typeface);

        binding.swiperefresh.setOnRefreshListener(MainActivity.this);
        binding.swiperefresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

    }

    private void getActors() {

        binding.EmptyMsg.setVisibility(View.GONE);

        adapter = new MyAdapter(this, this);
        binding.actorsRecyclerId.setLayoutManager(new LinearLayoutManager(this));
        binding.actorsRecyclerId.setAdapter(adapter);

        viewModel.getActors(
                ConstantsUtils.CATEGORY,
                ConstantsUtils.API_KEY,
                ConstantsUtils.LANGUAGE,
                ConstantsUtils.PAGE
        );

        viewModel.data.observe(this, new Observer<List<Actors.Result>>() {
            @Override
            public void onChanged(List<Actors.Result> results) {
                binding.resourcesProgressBar.setVisibility(View.GONE);
                resultList = results;
                adapter.setList(resultList);
            }
        });
    }

    @Override
    public void onRefresh() {
        binding.swiperefresh.setRefreshing(false);
        if (ConstantsUtils.isNetworkConnected(this)) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            showEmptyView();
        }
    }

    @Override
    public void onActorClick(int position) {
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        setIntentExtra(intent, position);
        startActivity(intent);
    }

    private void setIntentExtra(Intent intent, int position) {
        intent.putExtra("id", resultList.get(position).getId());
        intent.putExtra("Name", resultList.get(position).getName());
        intent.putExtra("Gender", resultList.get(position).getGender());
        intent.putExtra("depart", resultList.get(position).getKnownForDepartment());
    }


}