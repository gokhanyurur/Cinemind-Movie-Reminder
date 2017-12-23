package edu.ktu.cinemind.pages;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import edu.ktu.cinemind.MainActivity;
import edu.ktu.cinemind.R;

public class welcomeScreen extends AppCompatActivity implements View.OnClickListener{

    private Button signIn,signUp;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomescreendesign);


        signIn=(Button) findViewById(R.id.signInWelcome);
        signUp=(Button) findViewById(R.id.signUpWelcome);

        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        if(view == signIn){
            Intent myIntent=new Intent(context,loginScreen.class);
            startActivity(myIntent);
            finish();
        }
        if(view == signUp){
            Intent intent = new Intent(context, registerScreen.class);
            startActivity(intent);
            finish();
        }

    }
}
