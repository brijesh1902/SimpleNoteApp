package com.bpal.simplenoteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bpal.simplenoteapp.Database.Constant;
import com.bpal.simplenoteapp.Fragments.HomeFragment;
import com.bpal.simplenoteapp.Fragments.LoginFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null) {
            Log.d("=== ID ===>", account.getEmail());
            Constant.email = account.getEmail();
            loadFragment(new HomeFragment());
            Toast.makeText(getApplicationContext(),"Sign in Successfully.",Toast.LENGTH_LONG).show();
        } else {
            loadFragment(new LoginFragment());
        }

    }

    private void loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
    }
}