package com.example.serviceorientedsoftware.retrofit;

import com.example.serviceorientedsoftware.model.Order;
import com.example.serviceorientedsoftware.model.Pet;
import com.example.serviceorientedsoftware.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DataClient {


    @Headers({
            "Content-TypePet: application/json"
    })
    @GET("products")
    Call<List<Pet>> getListPet(@Query("type") String type);

    @Headers({
            "Content-TypePet: application/json"
    })
    @GET("products")
    Call<List<Pet>> getListPet();

    @Headers({
            "Content-TypePet: application/json"
    })
    @POST("orders")
    Call<Order> createOrder(@Body Order order);

    @POST("users")
    Call<User> createUser(@Body User user);

}
