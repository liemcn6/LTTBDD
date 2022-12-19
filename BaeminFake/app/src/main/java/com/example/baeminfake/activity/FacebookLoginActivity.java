package com.example.baeminfake.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.baeminfake.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import static com.example.baeminfake.activity.SplashActivity.login_code;


public class FacebookLoginActivity extends AppCompatActivity {

    private LoginButton login;
    private CallbackManager callbackManager;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private AccessTokenTracker accessTokenTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facebook_login_activity);

        firebaseAuth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());

        login = findViewById(R.id.login_fb);
        login.setReadPermissions("email", "public_profile");

        callbackManager = CallbackManager.Factory.create();
        login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException e) {
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull @NotNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    LoginManager.getInstance().logOut();
                }
            }
        };

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken
                    currentAccessToken) {
                if (currentAccessToken != null) {
                    LoginManager.getInstance().logOut();
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookToken(AccessToken accessToken) {
        Log.d("FACEBOOK_RESULT", "handle login user");
        AuthCredential authCredential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(FacebookLoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("FACEBOOK_RESULT", "sign in with credential successful");
                    nextActivity();
                } else {
                    Log.d("FACEBOOK_RESULT", "sign in with credential failure", task.getException());
                }
            }
        });
    }

    private void nextActivity() {
        login_code = 1;
        startActivity(new Intent(FacebookLoginActivity.this, MainActivity.class));
        FacebookLoginActivity.this.finish();
    }
}