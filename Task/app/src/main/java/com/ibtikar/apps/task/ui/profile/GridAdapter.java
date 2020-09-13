package com.ibtikar.apps.task.ui.profile;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ibtikar.apps.task.R;
import com.ibtikar.apps.task.Utils.ConstantsUtils;
import com.ibtikar.apps.task.pojo.ActorsProfiles;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends ArrayAdapter<ActorsProfiles.Profile> {

    private Context context;
    List<ActorsProfiles.Profile> profileList = new ArrayList<>();
    int resource;

    public GridAdapter(@NonNull Context context, int resource, @NonNull List<ActorsProfiles.Profile> profileList) {
        super(context, resource, profileList);

        this.context = context;
        this.resource = resource;
        this.profileList = profileList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.custom_gridview_layout, null, true);
        }

        ActorsProfiles.Profile profile = getItem(position);
        ImageView img = convertView.findViewById(R.id.baseImgId);
        Picasso.get()
                .load(ConstantsUtils.BASE_IMAGE_SOURCE + profile.getFilePath())
                .into(img);

        return convertView;

    }
}
