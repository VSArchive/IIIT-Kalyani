package com.iiit.iiitkalyani;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.r0adkll.slidr.Slidr;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Settings extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String SWITCH = "switch";
    public static final String SWITCHPFIMG = "switchpfimg";
    public static boolean switchOn;
    public static boolean switchPf;
    public Switch fp;
    public Switch PfImg;
    String personName;
    String personEmail;
    Uri personPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        Slidr.attach(this);

        TextView name = findViewById(R.id.txtName);
        CircleImageView profileImg = findViewById(R.id.image);
        TextView Email = findViewById(R.id.Email);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(Settings.this);
        if (acct != null) {
            personName = acct.getDisplayName();
            personEmail = acct.getEmail();
            personPhoto = acct.getPhotoUrl();
        } else {
            personName = Objects.requireNonNull(auth.getCurrentUser()).getDisplayName();
            personPhoto = auth.getCurrentUser().getPhotoUrl();
            personEmail = auth.getCurrentUser().getEmail();
        }
        name.setText(personName);
        Email.setText(personEmail);
        Glide.with(this).load(personPhoto).into(profileImg);

        fp = findViewById(R.id.fp);
        fp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveData();
            }
        });
        PfImg = findViewById(R.id.pfimg);
        PfImg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveData();
            }
        });
        loadData();
        updateViews();

    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SWITCH, fp.isChecked());
        editor.putBoolean(SWITCHPFIMG, PfImg.isChecked());
        editor.apply();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        switchOn = sharedPreferences.getBoolean(SWITCH, Boolean.parseBoolean(SWITCH));
        switchPf = sharedPreferences.getBoolean(SWITCHPFIMG, Boolean.parseBoolean(SWITCHPFIMG));
    }

    public void updateViews() {
        fp.setChecked(switchOn);
        PfImg.setChecked(switchPf);
    }
}
