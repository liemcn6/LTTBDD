package com.example.chat_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar myToolbar;
    private ViewPager myViewPager;
    private TabLayout myTabLayout;
    private TabsAcessorAdapter myTabsAcessorAdapter;
    private FirebaseUser currenUser;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private String currentUserID;

    private CircleImageView imageDrawer;
    private TextView textNameDrawer;
    private TextView textStatusDrawer;

    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();

        // t???o viewPager v?? th??m tablayout v??o
        myViewPager.setAdapter(myTabsAcessorAdapter);
        myTabLayout.setupWithViewPager(myViewPager);

        //
        mAuth = FirebaseAuth.getInstance();
        currenUser = mAuth.getCurrentUser();
        UpdateUserStatus("online");

        RetrieveUserInfo();
    }

    private void Anhxa() {
        myToolbar              = findViewById(R.id.main_page_toolbar);
        myViewPager            = findViewById(R.id.main_tabs_pager);
        myTabLayout            = findViewById(R.id.main_tabs);
        myTabsAcessorAdapter   = new TabsAcessorAdapter(getSupportFragmentManager());
        currenUser = null;
        RootRef = FirebaseDatabase.getInstance().getReference();

        // T???o toolbar
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Chat App");

        // Anh xa drawer, va toggle ??i???u khi???n ????ng m???
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,myToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Anh x??? Navigation
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        imageDrawer = header.findViewById(R.id.image_drawer);
        textNameDrawer = header.findViewById(R.id.name_drawer);
        textStatusDrawer = header.findViewById(R.id.status_drawer);

    }

    // B???t ?????u ch???y ktra xem ????ng nh???p ch??a, ch??a th?? quay l???i m??n h??nh ????ng nh???p
    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        currenUser = mAuth.getCurrentUser();
        if (currenUser == null){
            SentUserToLoginActivity();
        }else{
            UpdateUserStatus("online");
            VeriFyUserExistance();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAuth = FirebaseAuth.getInstance();
        currenUser = mAuth.getCurrentUser();
        if(currenUser != null){
            UpdateUserStatus("offline");
        }
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        mAuth = FirebaseAuth.getInstance();
//        currenUser = mAuth.getCurrentUser();
//        if(currenUser != null){
//            UpdateUserStatus("offline");
//        }
//    }

    // Ki???m tra th??ng tin ng d??ng ch??a c?? th?? chuy???n sang form setting
    private void VeriFyUserExistance() {
        // V??o database -> user . ki???m tra user ???? ????ng nh???p th??ng qua uID
        // N???u user c?? d??? li???u th?? th??i, ch??a c?? th?? sang setting th??m d??? li???u v??o
        String currenUserID = mAuth.getCurrentUser().getUid();
        RootRef.child("Users").child(currenUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // N???u ???? c?? c???t t??n l?? name th?? th??ng b??o l???i ch??o
                if(dataSnapshot.child("name").exists()){
                }
                // Ch??a c?? th?? sang setting ????? th??m
                else{
                    SendUserToSettingActivity();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    // ??i t???i m??n h??nh login
    private void SentUserToLoginActivity() {
        Intent intent = new Intent(MainActivity.this,Login_Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    // ??i t???i m??n h??nh setting
    private void SendUserToSettingActivity(){
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);

    }
    // T???o menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //B???t s??? ki???n tr??n menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.main_find_friends_option:
                startActivity(new Intent(MainActivity.this, FindFriendsActivity.class));
                break;
            case R.id.main_settings_option:
                startActivity(new Intent(MainActivity.this,SettingsActivity.class));
                break;
            case R.id.main_logout_option:
                UpdateUserStatus("offline");
                mAuth.signOut();
                SentUserToLoginActivity();
                break;
            case R.id.main_create_group_option:
                RequestNewGroup();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // T???o group chat m???i
    private void RequestNewGroup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Group name : ");

        final EditText groupNameField = new EditText(this);
        groupNameField.setHint("e.g Coding Cofe");

        builder.setView(groupNameField);
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String groupName = groupNameField.getText().toString();
                if(TextUtils.isEmpty(groupName)){
                    Toast.makeText(MainActivity.this, "Nh???p t??n group", Toast.LENGTH_SHORT).show();

                }else{
                    CreateNewGroup(groupName);
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void CreateNewGroup(final String groupName) {
        RootRef.child("Groups").child(groupName).setValue("")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "T???o th??nh c??ng group " + groupName, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    // C???p nh???t tr???ng th??i user online/ offline
    private void UpdateUserStatus(String state){
        String saveCurrentTime, saveCurrentDate;

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        HashMap<String, Object> onlineStateMap = new HashMap<>();
        onlineStateMap.put("time", saveCurrentTime);
        onlineStateMap.put("date",saveCurrentDate);
        onlineStateMap.put("state", state);

        currentUserID = mAuth.getCurrentUser().getUid();
        RootRef.child("Users").child(currentUserID).child("userState")
                .updateChildren(onlineStateMap);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.find_friends_drawer:
                startActivity(new Intent(MainActivity.this, FindFriendsActivity.class));
                break;
            case R.id.profile_drawer:
                startActivity(new Intent(MainActivity.this,SettingsActivity.class));
                break;
            case R.id.logout_drawer:
                UpdateUserStatus("offline");
                mAuth.signOut();
                SentUserToLoginActivity();
                break;
            case R.id.create_group_:
                RequestNewGroup();
                break;
            case R.id.contacts_author:
                startActivity(new Intent(MainActivity.this,AuthorActivity.class));
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // B???t t???t nav
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            mAuth = FirebaseAuth.getInstance();
            currenUser = mAuth.getCurrentUser();
            if(currenUser != null){

            }else{
                super.onBackPressed();
            }
        }


    }

    private void RetrieveUserInfo(){
        String currenUserID = mAuth.getCurrentUser().getUid();
        RootRef.child("Users").child(currenUserID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if((dataSnapshot.exists())&&(dataSnapshot.hasChild("name"))&&(dataSnapshot.hasChild("image"))){

                            String retrieveUserName = dataSnapshot.child("name").getValue().toString();
                            String retrieveUserStatus = dataSnapshot.child("status").getValue().toString();
                            String retrieveProfileImage = dataSnapshot.child("image").getValue().toString();

                            textNameDrawer.setText(retrieveUserName);
                            textStatusDrawer.setText(retrieveUserStatus);
                            Picasso.with(MainActivity.this).load(retrieveProfileImage)
                                    .into(imageDrawer);

                        }else if((dataSnapshot.exists())&&(dataSnapshot.hasChild("name"))){

                            String retrieveUserName = dataSnapshot.child("name").getValue().toString();
                            String retrieveUserStatus = dataSnapshot.child("status").getValue().toString();

                            textNameDrawer.setText(retrieveUserName);
                            textStatusDrawer.setText(retrieveUserStatus);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
