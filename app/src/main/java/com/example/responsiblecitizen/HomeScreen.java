package com.example.responsiblecitizen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

public class HomeScreen extends AppCompatActivity {
    public static final String GOOGLE_ACCOUNT = "google_account";
    private TextView profileName, profileEmail;
    private ImageView profileImage;
    private Button signOut;
    GoogleSignInClient googleSignInClient;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        profileName = findViewById(R.id.profile_text);
        profileEmail = findViewById(R.id.profile_email);
        profileImage = findViewById(R.id.profile_image);
        setDataOnView();
        signOut = findViewById(R.id.sign_out);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);


        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent=new Intent(HomeScreen.this,SignInActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
            }
        });

    }


    private void setDataOnView() {
        GoogleSignInAccount googleSignInAccount = getIntent().getParcelableExtra(GOOGLE_ACCOUNT);
        Picasso.get().load(googleSignInAccount.getPhotoUrl()).centerInside().fit().into(profileImage);
        profileName.setText(googleSignInAccount.getDisplayName());
        profileEmail.setText(googleSignInAccount.getEmail());
    }



}

