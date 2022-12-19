package com.example.serviceorientedsoftware.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.serviceorientedsoftware.R;
import com.example.serviceorientedsoftware.constants.Constants;
import com.example.serviceorientedsoftware.databinding.ActivityDetailPetBinding;
import com.example.serviceorientedsoftware.model.Order;
import com.example.serviceorientedsoftware.model.OrderPet;
import com.example.serviceorientedsoftware.model.Pet;
import com.example.serviceorientedsoftware.retrofit.APIUtils;
import com.example.serviceorientedsoftware.retrofit.DataClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.util.ApiUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPetActivity extends AppCompatActivity {

    private ActivityDetailPetBinding binding;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailPetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Pet pet = (Pet) getIntent().getSerializableExtra("pet");
        mAuth = FirebaseAuth.getInstance();

        // set image
        Glide.with(this)
                .load(pet.getImg_url())
                .centerCrop()
                .placeholder(R.drawable.dog1)
                .into(binding.imagePetDetails);

        // set Name
        binding.txtNameDetail.setText(pet.getName());
        if (pet.getSex().equals("male")){
            binding.txtNameDetail.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.ic_male,0);
        }else{
            binding.txtNameDetail.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.ic_female,0);
        }

        // set weight,..
        binding.txtWeightDetail.setText(pet.getWeight() + " Kg");
        binding.txtColorDetail.setText(pet.getColor());
        binding.txtAgeDetail.setText(pet.getAge() + " years");

        // description
        binding.txtDescriptionDetail.setText(pet.getDescription());

        // price
        binding.priceDetail.setText(pet.getPrice() + " $");

        // specice
        binding.speciesDetail.setText(pet.getSpecies());

        binding.btnBuyPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataClient dataClient = APIUtils.getData(Constants.sale_url);
                List<OrderPet> orderPet = new ArrayList<>();
                orderPet.add(new OrderPet(pet.getId(), pet.getName(),
                        1, (int) pet.getPrice()));

                Order order = new Order((int) pet.getPrice(), mAuth.getCurrentUser().getUid(), orderPet);
                Call<Order> createOrder = dataClient.createOrder(order);
                createOrder.enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        Order or = response.body();
                        Toast.makeText(DetailPetActivity.this, or.getState(), Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {

                    }
                });
            }
        });
    }
}