package com.example.serviceorientedsoftware.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.serviceorientedsoftware.R;
import com.example.serviceorientedsoftware.adapter.PetAdapter;
import com.example.serviceorientedsoftware.adapter.TypePetAdapter;
import com.example.serviceorientedsoftware.constants.Constants;
import com.example.serviceorientedsoftware.databinding.ActivityPetsBinding;
import com.example.serviceorientedsoftware.googlemap.ShopLocationActivity;
import com.example.serviceorientedsoftware.model.Pet;
import com.example.serviceorientedsoftware.model.TypePet;
import com.example.serviceorientedsoftware.retrofit.APIUtils;
import com.example.serviceorientedsoftware.retrofit.DataClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityPetsBinding binding;
    private FirebaseAuth mAuth;
    private TextView txtNameDrawer;
    private TextView txtEmailDrawer;
    private ImageView imageAvataDrawer;
    private ArrayList<Pet> pets;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPetsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        setTypePet();

        setProfile();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        binding.recyclerListPets.setLayoutManager(gridLayoutManager);

        setPets("ALL");

        binding.imgAvataUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PetsActivity.this, ProfileUserActivity.class));
            }
        });

        binding.imgAvataUser.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(PetsActivity.this, LoginActivity.class));
                fileList();
                return true;
            }
        });

        binding.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                }else{
                    binding.drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        binding.searchPets.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int counter, int after) {
                if(TextUtils.isEmpty(charSequence) || charSequence.equals("")){
                    PetAdapter adapter = new PetAdapter(pets, PetsActivity.this);
                    binding.recyclerListPets.setAdapter(adapter);
                }
                else{

                    // Biến đổi chuỗi viết hoa chữ cái đầu
                    String str = charSequence.toString();

                    // Tìm kiếm gần đúng
                    String regex = ".*" + str ; // tìm từ bắt đầu bằng str
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher;
                    ArrayList<Pet> newList = new ArrayList<>();
                    if (pets != null && pets.size() > 0){
                        for(int i = 0; i < pets.size(); i++){
                            matcher = pattern.matcher(pets.get(i).getName());
                            if(matcher.find()){
                                newList.add(pets.get(i));
                            }
                        }
                    }
                    PetAdapter adapterNew = new PetAdapter(newList, PetsActivity.this);
                    adapterNew.notifyDataSetChanged();
                    binding.recyclerListPets.setAdapter(adapterNew);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout,null,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);

        View header = binding.navView.getHeaderView(0);
        imageAvataDrawer = header.findViewById(R.id.img_avata_profile_drawer);
        txtEmailDrawer = header.findViewById(R.id.email_nav_drawer);
        txtNameDrawer = header.findViewById(R.id.name_nav_drawer);

    }

    private void setProfile() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(mAuth.getCurrentUser().getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String address = document.getString(Constants.KEY_ADDESS);
                        if (!TextUtils.isEmpty(address)){
                            binding.userLocation.setText(address);
                        }
                        String email = document.getString(Constants.KEY_EMAIL);
                        String name = document.getString(Constants.KEY_NAME);
                        String image = document.getString(Constants.KEY_IMAGE);

                        if(image != null && !TextUtils.isEmpty(image) ){
                            //Toast.makeText(PetsActivity.this, image, Toast.LENGTH_SHORT).show();
                            Glide.with(PetsActivity.this)
                                    .load(image)
                                    .centerCrop()
                                    .error(R.drawable.profile_image)
                                    .placeholder(R.drawable.profile_image)
                                    .into(binding.imgAvataUser);

                            Glide.with(PetsActivity.this)
                                    .load(image)
                                    .centerCrop()
                                    .error(R.drawable.profile_image)
                                    .placeholder(R.drawable.profile_image)
                                    .into(imageAvataDrawer);
                        }

                        txtNameDrawer.setText(name);
                        txtEmailDrawer.setText(email);

                    } else {

                    }
                } else {

                }
            }
        });
    }

    public void setTypePet(){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // set Horizontal Orientation
        binding.recyclerChosenPets.setLayoutManager(linearLayoutManager);

        List<TypePet> types = new ArrayList<>();
        types.add(new TypePet(0,R.drawable.all_type,"All", "ALL"));
        types.add(new TypePet(0,R.drawable.dog_type,"Dogs","DOG"));
        types.add(new TypePet(0,R.drawable.cat_type,"Cats","CAT"));
        types.add(new TypePet(0,R.drawable.bird_type,"Birds","BIRD"));
        types.add(new TypePet(0,R.drawable.fish_type,"Fishs","FISH"));

        TypePetAdapter adapter = new TypePetAdapter(types,this);
        binding.recyclerChosenPets.setAdapter(adapter);
        binding.recyclerChosenPets.setHasFixedSize(true);
    }

    public void setPets(String type){
        DataClient dataClient = APIUtils.getData(Constants.sale_url);
        Call<List<Pet>> getPet;
        if (type.equals("ALL")){
            getPet = dataClient.getListPet();
        }else{
            getPet = dataClient.getListPet(type);
        }

        getPet.enqueue(new Callback<List<Pet>>() {
            @Override
            public void onResponse(Call<List<Pet>> call, Response<List<Pet>> response) {
                pets = (ArrayList<Pet>) response.body();
                if (pets != null && pets.size() != 0){
                    Collections.shuffle(pets);
                    PetAdapter adapter = new PetAdapter(pets, PetsActivity.this);
                    binding.recyclerListPets.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                    binding.recyclerListPets.setAdapter(adapter);
                    binding.recyclerListPets.setHasFixedSize(true);
                }else{
                    Toast.makeText(PetsActivity.this, "Không có dữ liệu hiển thị", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<Pet>> call, Throwable t) {
                //Toast.makeText(PetsActivity.this, "Không thể gọi...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.location_drawer:
                startActivity(new Intent(PetsActivity.this, ShopLocationActivity.class));
                break;
            case R.id.profile_drawer:
                startActivity(new Intent(PetsActivity.this, ProfileUserActivity.class));
                break;
            case R.id.drawer_share:
                Toast.makeText(this, "Go to Share!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.drawer_send:
                Toast.makeText(this, "Go to Send!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout_drawer:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(PetsActivity.this, LoginActivity.class));
                break;
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            binding.searchPets.setText("");
        }
    }
}