package com.iiit.iiitkalyani;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PassReset extends AppCompatActivity {
    private EditText email;
    private TextView txt;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_reset);
        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        Button confirm = findViewById(R.id.confirm);
        txt = findViewById(R.id.txt);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(email.getText())) {
                    Toast.makeText(PassReset.this, "Enter a Valid Email", Toast.LENGTH_SHORT).show();
                } else {
                    auth.sendPasswordResetEmail(email.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(PassReset.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                        txt.setText("Recovery Email Sent!");
                                    } else {
                                        Toast.makeText(PassReset.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                        txt.setText("Enter Recovery Email");
                                    }
                                }
                            });
                }
            }
        });
    }
}
