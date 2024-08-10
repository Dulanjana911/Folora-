package com.dsmini.folorauserapp.Reviews;

import static android.content.Context.CLIPBOARD_SERVICE;

import static androidx.core.app.ActivityCompat.finishAffinity;
import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dsmini.folorauserapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


import android.content.ClipboardManager;

import java.util.Random;


public class  ReviewAdapter extends FirebaseRecyclerAdapter<Review,ReviewAdapter.myViewHoder> {

    private Context context;




    public ReviewAdapter(@NonNull FirebaseRecyclerOptions<Review> options) {

        super(options);
        this. context = context;




    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHoder holder, int position, @NonNull Review model) {
        holder.rv.setText(model.getReview());
        holder.name.setText(model.getName());



    }

    @NonNull
    @Override
    public myViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviewlist,parent,false);


        return new myViewHoder(view);
    }



    class myViewHoder extends RecyclerView.ViewHolder{

        TextView name,rv;



        public myViewHoder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.review_name);
             rv= itemView.findViewById(R.id.review_comment);


            return;

        }
    }




}
