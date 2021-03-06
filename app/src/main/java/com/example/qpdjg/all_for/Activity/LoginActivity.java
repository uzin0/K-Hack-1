package com.example.qpdjg.all_for.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qpdjg.all_for.R;
import com.example.qpdjg.all_for.Util.TitlebarActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import static com.example.qpdjg.all_for.R.string.progess_in_login;

public class LoginActivity extends TitlebarActivity implements View.OnClickListener {

    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    //define view objects
    EditText editTextEmail;
    EditText editTextPassword;
    Button buttonSignin;
    TextView textviewSingin;
    TextView textviewMessage;
    TextView logintext;
    TextView textviewFindPassword;
    ProgressDialog progressDialog;
    //define firebase object
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //initializig firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            //이미 로그인 되었다면 이 액티비티를 종료함
            finish();
            //그리고 profile 액티비티를 연다.
            startActivity(new Intent(getApplicationContext(), MainActivity.class)); //추가해 줄 ProfileActivity
        }

        //log-in 객체 생성
        logintext = (TextView) findViewById(R.id.login);
        //initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textviewSingin= (TextView) findViewById(R.id.textViewSignin);
        textviewMessage = (TextView) findViewById(R.id.textviewMessage);
        textviewFindPassword = (TextView) findViewById(R.id.textViewFindpassword);
        buttonSignin = (Button) findViewById(R.id.buttonSignup);
        progressDialog = new ProgressDialog(this);
        //button click event
        buttonSignin.setOnClickListener(this);
        textviewSingin.setOnClickListener(this);
        textviewFindPassword.setOnClickListener(this);
        //언어별 text 설정
        logintext.setText(R.string.login);
        editTextEmail.setHint(R.string.email);
        editTextPassword.setHint(R.string.password);
        buttonSignin.setText(R.string.loginbutton);
        buttonSignin.setText(R.string.loginbutton);
        textviewSingin.setText(R.string.newenter);
        textviewFindPassword.setText(R.string.find);
    }
    //firebase userLogin method
    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, R.string.plz_enter_email, Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, R.string.plz_enter_Password, Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage(getResources().getString(R.string.progess_in_login));
        progressDialog.show();
        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.fail_login, Toast.LENGTH_LONG).show();
                            textviewMessage.setText(R.string.why_fail);
                        }
                    }
                });
    }
    @Override
    public void onClick(View view) {
        if(view == buttonSignin) {
            userLogin();
        }
        if(view == textviewSingin) {
            finish();
            startActivity(new Intent(this, SignUpActivity.class));
        }
        if(view == textviewFindPassword) {
            finish();
            startActivity(new Intent(this, FindActivity.class));
        }
    }
    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
        {
            super.onBackPressed();
        }
        else
        {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), R.string.double_press_end, Toast.LENGTH_SHORT).show();
        }
    }
}