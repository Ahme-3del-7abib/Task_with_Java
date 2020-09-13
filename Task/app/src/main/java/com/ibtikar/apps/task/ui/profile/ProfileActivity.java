package com.ibtikar.apps.task.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.ibtikar.apps.task.R;
import com.ibtikar.apps.task.Utils.ConstantsUtils;
import com.ibtikar.apps.task.databinding.ActivityProfileBinding;
import com.ibtikar.apps.task.pojo.ActorsProfiles;
import com.ibtikar.apps.task.ui.details.DetailsImagesActivity;
import com.ibtikar.apps.task.ui.main.MainActivity;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private int id;

    ActivityProfileBinding binding;
    private GridAdapter adapter;
    ProfileViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setView();

        getProfileData();

        binding.gridViewID.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ActorsProfiles.Profile item = (ActorsProfiles.Profile) parent.getItemAtPosition(position);
                Intent intent = new Intent(ProfileActivity.this, DetailsImagesActivity.class);
                intent.putExtra("image", item.getFilePath());
                startActivity(intent);
            }
        });
    }

    private void setView() {

        Intent intent = getIntent();
        id = intent.getExtras().getInt("id");

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/fontfamily.ttf");
        binding.detailsId.setTypeface(typeface);
        binding.nameID.setTypeface(typeface);
        binding.genderID.setTypeface(typeface);
        binding.departID.setTypeface(typeface);

        binding.profileNameID.setText(intent.getExtras().getString("Name"));

        int gender = intent.getExtras().getInt("Gender");
        if (gender == 1) {
            binding.profileGenderID.setText("Female");
        } else {
            binding.profileGenderID.setText("Male");
        }

        binding.profileDepartmentId.setText(intent.getExtras().getString("depart"));

    }

    private void getProfileData() {
        viewModel.getProfileImages(id, ConstantsUtils.API_KEY);
        viewModel.data.observe(this, new Observer<List<ActorsProfiles.Profile>>() {
            @Override
            public void onChanged(List<ActorsProfiles.Profile> profileList) {
                adapter = new GridAdapter(getApplicationContext(), R.layout.custom_gridview_layout, profileList);
                binding.gridViewID.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
    }
}