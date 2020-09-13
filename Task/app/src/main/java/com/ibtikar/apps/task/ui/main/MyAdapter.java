package com.ibtikar.apps.task.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ibtikar.apps.task.R;
import com.ibtikar.apps.task.Utils.ConstantsUtils;
import com.ibtikar.apps.task.pojo.Actors;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ActorsViewHolders> {

    List<Actors.Result> actorList = new ArrayList<>();
    OnActorClickListener onActorClickListener;
    Context context;

    public MyAdapter(Context context, OnActorClickListener listener) {
        this.context = context;
        this.onActorClickListener = listener;
    }

    @NonNull
    @Override
    public ActorsViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyAdapter.ActorsViewHolders(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.actors_list_items, parent, false), onActorClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ActorsViewHolders holder, int position) {

        Picasso.get()
                .load(ConstantsUtils.BASE_IMAGE_SOURCE + actorList.get(position).getProfilePath())
                .into(holder.profileImg);

        holder.name.setText("Name : " + actorList.get(position).getName());

        holder.depart.setText("Department : " + actorList.get(position).getKnownForDepartment());

        if (actorList.get(position).getGender() == 1) {
            holder.gender.setText("Female");
        } else {
            holder.gender.setText("Male");
        }
    }

    @Override
    public int getItemCount() {
        return actorList.size();
    }

    public void setList(List<Actors.Result> list) {
        this.actorList = list;
        notifyDataSetChanged();
    }

    public class ActorsViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnActorClickListener onActorClickListener;
        TextView name, depart, gender;
        CircleImageView profileImg;

        public ActorsViewHolders(@NonNull View itemView, OnActorClickListener listener) {
            super(itemView);

            name = itemView.findViewById(R.id.nameID);
            depart = itemView.findViewById(R.id.departID);
            gender = itemView.findViewById(R.id.genderId);
            profileImg = itemView.findViewById(R.id.imgID);

            this.onActorClickListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onActorClickListener.onActorClick(getAdapterPosition());
        }
    }

    public interface OnActorClickListener {
        void onActorClick(int position);
    }
}
