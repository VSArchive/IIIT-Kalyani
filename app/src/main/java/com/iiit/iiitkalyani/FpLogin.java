package com.iiit.iiitkalyani;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;

import static android.widget.Toast.LENGTH_SHORT;
import static com.iiit.iiitkalyani.Settings.SHARED_PREFS;
import static com.iiit.iiitkalyani.Settings.SWITCH;
import static com.iiit.iiitkalyani.Settings.switchOn;

public class FpLogin extends AppCompatActivity {

    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private ImageView fp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fp_login);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        switchOn = sharedPreferences.getBoolean(SWITCH, false);
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("IIIT Kalyani")
                .setSubtitle("Authenticate to Login to IIIT Kalyani App")
                .setDeviceCredentialAllowed(true)
                .build();

        Executor executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(FpLogin.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                finish();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "Authentication Successful!", LENGTH_SHORT).show();
                changeActivity();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed", LENGTH_SHORT).show();
            }
        });
        if (switchOn) {
            auth();
        } else {
            changeActivity();
        }
    }

    private void changeActivity() {
        Intent intent = new Intent(FpLogin.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    private void auth() {
        if (promptInfo != null) {
            biometricPrompt.authenticate(promptInfo);
        }
    }
}
