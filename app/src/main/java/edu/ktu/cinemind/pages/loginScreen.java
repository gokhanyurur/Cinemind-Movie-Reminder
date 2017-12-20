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

public class loginScreen extends AppCompatActivity implements View.OnClickListener{

    private Button signIn,signUp;
    private EditText email,password;
    private Context context = this;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreendesign);

        progressDialog=new ProgressDialog(this);

        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() !=null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        signIn=(Button) findViewById(R.id.signInButton);
        signUp=(Button) findViewById(R.id.signUpButton);
        email=(EditText) findViewById(R.id.emailText);
        password=(EditText) findViewById(R.id.passwordText);

        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);

    }

    private void userLogin(){
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

        progressDialog.setMessage("Loginning User.");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userEmail,userPass)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.hide();
                    finish();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                else{
                    Toast.makeText(context,"Wrong username or password.",Toast.LENGTH_SHORT);
                    progressDialog.hide();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == signIn){
            userLogin();
        }
        if(view == signUp){
            Intent intent = new Intent(context, registerScreen.class);
            startActivity(intent);
            finish();
        }

    }
}
