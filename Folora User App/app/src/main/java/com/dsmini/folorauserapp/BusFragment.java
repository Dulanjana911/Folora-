package com.dsmini.folorauserapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class BusFragment extends Fragment {


    private RecyclerView recyclerView;
    private DatabaseReference Userref;


    private  TextView main;

    private  AutoCompleteTextView autoCompleteTextView;

    private ImageView search,refresh;

    Context context;


    public BusFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bus, container, false);




        Userref = FirebaseDatabase.getInstance().getReference().child("Users");

        autoCompleteTextView = view.findViewById(R.id.txt_search);
        search = view.findViewById(R.id.search_button);
        refresh = view.findViewById(R.id.refresh_btn);
        main= view.findViewById(R.id.maintext);

        String[] cityArray = getResources().getStringArray(R.array.city_names);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, cityArray);
        autoCompleteTextView.setAdapter(arrayAdapter);


        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseRecyclerOptions<Contacts> options = new FirebaseRecyclerOptions.Builder<Contacts>()
                        .setQuery(Userref.orderByChild("Type").equalTo("Bus"), Contacts.class)
                        .build();


                FirebaseRecyclerAdapter<Contacts, FindFriendsViewHolder> adapter =
                        new FirebaseRecyclerAdapter<Contacts, FindFriendsViewHolder>(options) {
                            @Override
                            protected void onBindViewHolder(@NonNull FindFriendsViewHolder holder, final int position, @NonNull Contacts model) {

                                holder.username.setText(model.getName());
                                holder.userstatus.setText(model.getStatus());
                                Picasso.get().load(model.getImage()).placeholder(R.drawable.p).into(holder.profile);
                                main.setVisibility(View.INVISIBLE);
                                recyclerView.setVisibility(View.VISIBLE);

                                holder.itemView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String visit_user_id = getRef(position).getKey();
                                        Intent profileintent = new Intent(getActivity(), ProfileActivity.class);
                                        profileintent.putExtra("visit_user_id", visit_user_id);
                                        startActivity(profileintent);
                                    }
                                });
                            }

                            @NonNull
                            @Override
                            public FindFriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_display_layout, parent, false);
                                FindFriendsViewHolder viewHolder = new FindFriendsViewHolder(view);
                                return viewHolder;
                            }
                        };
                recyclerView.setAdapter(adapter);
                adapter.startListening();
            }
        });







        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseRecyclerOptions<Contacts> options = new FirebaseRecyclerOptions.Builder<Contacts>()
                        .setQuery(Userref.orderByChild("Type").equalTo("Bus"), Contacts.class)
                        .build();



                final FirebaseRecyclerAdapter<Contacts, FindFriendsViewHolder> adapter =
                        new FirebaseRecyclerAdapter<Contacts, FindFriendsViewHolder>(options) {

                            @Override
                            protected void onBindViewHolder(@NonNull FindFriendsViewHolder holder, final int position, @NonNull Contacts model) {

                                if(Objects.equals(model.getStatus(), autoCompleteTextView.getText().toString())) {

                                    holder.username.setText(model.getName());
                                    holder.userstatus.setText(model.getStatus());
                                    Picasso.get().load(model.getImage()).placeholder(R.drawable.p).into(holder.profile);
                                    main.setVisibility(View.INVISIBLE);
                                    recyclerView.setVisibility(View.VISIBLE);

                                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String visit_user_id = getRef(position).getKey();
                                            Intent profileintent = new Intent(getActivity(), ProfileActivity.class);
                                            profileintent.putExtra("visit_user_id", visit_user_id);
                                            startActivity(profileintent);
                                        }
                                    });

                                }




                            }

                            @NonNull
                            @Override
                            public FindFriendsViewHolder onCreateViewHolder (@NonNull ViewGroup
                                                                                     parent,int viewType) {


                                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_display_layout, parent, false);
                                FindFriendsViewHolder viewHolder = new FindFriendsViewHolder(view);
                                return viewHolder;

                            }

                        };

                recyclerView.setAdapter(adapter);
                adapter.startListening();





            }
        });




        recyclerView = view.findViewById(R.id.find_friends_recyclerlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));




        return view;
    }




    @Override
    public void onStart() {
        super.onStart();

    }

    public static class FindFriendsViewHolder extends RecyclerView.ViewHolder {
        TextView username, userstatus;
        CircularImageView profile;


        public FindFriendsViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.users_profile_name);
            userstatus = itemView.findViewById(R.id.users_status);
            profile = itemView.findViewById(R.id.users_profile_image);

        }
    }
}
