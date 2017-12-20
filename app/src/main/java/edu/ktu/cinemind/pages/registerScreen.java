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

public class registerScreen extends AppCompatActivity implements View.OnClickListener{

    private Button signIn,signUp;
    private EditText password,email;
    private Context context = this;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerscreendesign);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() !=null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        progressDialog=new ProgressDialog(this);

        signIn=(Button) findViewById(R.id.signInButtonReg);
        signUp=(Button) findViewById(R.id.signUpButtonReg);
        password=(EditText) findViewById(R.id.passwordRegisterText);
        email=(EditText) findViewById(R.id.emailRegisterText);

        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);

    }

    private void registerUser(){
        String userEmail = email.getText().toString().trim();
        String userPass = password.getText().toString().trim();

        if(TextUtils.isEmpty(userEmail)){
            Toast.makeText(context,"Email can not be empty.", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(userPass)){
            Toast.makeText(context,"Password can not be empty.", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(userEmail,userPass)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(registerScreen.this,"User is registered successfully.",Toast.LENGTH_LONG).show();
                    progressDialog.hide();
                    firebaseAuth.signOut();
                    Intent intent = new Intent(context, loginScreen.class);
                    startActivity(intent);
                    finish();
                }else{
                    progressDialog.hide();
                    Toast.makeText(registerScreen.this,"User couldn't register, please try again.",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public void onClick(View view) {
        if(view == signIn){
            Intent intent = new Intent(context, loginScreen.class);
            startActivity(intent);
            finish();
        }
        if(view == signUp){
            registerUser();
        }

    }

}
