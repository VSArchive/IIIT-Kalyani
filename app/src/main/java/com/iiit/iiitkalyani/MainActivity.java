package com.iiit.iiitkalyani;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.iiit.iiitkalyani.ui.CalenderFragment;
import com.iiit.iiitkalyani.ui.FestFragment;
import com.iiit.iiitkalyani.ui.GalleryFragment;
import com.iiit.iiitkalyani.ui.GpaFragment;
import com.iiit.iiitkalyani.ui.HomeFragment;
import com.iiit.iiitkalyani.ui.MessageFragment;
import com.iiit.iiitkalyani.ui.UsersFragment;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.iiit.iiitkalyani.Settings.SHARED_PREFS;
import static com.iiit.iiitkalyani.Settings.SWITCHPFIMG;
import static com.iiit.iiitkalyani.Settings.switchPf;

public class MainActivity extends AppCompatActivity {

    public TextView name;
    public TextView email;
    public ImageView img;
    String personName;
    String personEmail;
    Uri personPhoto;
    private AppBarConfiguration AppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        AppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_tools, R.id.nav_home, R.id.nav_gallery, R.id.nav_cal)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, AppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        toolbar.setTitle("Home");
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                                new HomeFragment()).commit();
                        break;
                    case R.id.nav_gallery:
                        toolbar.setTitle("Gallery");
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                                new GalleryFragment()).commit();
                        break;
                    case R.id.nav_tools:
                        toolbar.setTitle("Fest");
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                                new FestFragment()).commit();
                        break;
                    case R.id.nav_cal:
                        toolbar.setTitle("Academic Calender");
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                                new CalenderFragment()).commit();
                        break;
                    case R.id.nav_cgpa:
                        toolbar.setTitle("GPA Calculator");
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                                new GpaFragment()).commit();
                        break;
                    case R.id.nav_messages:
                        toolbar.setTitle("Group Chat");
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                                new MessageFragment()).commit();
                        break;
                    case R.id.nav_users:
                        toolbar.setTitle("Users");
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                                new UsersFragment()).commit();
                        break;
                    case R.id.nav_settings:
                        Intent intent = new Intent(MainActivity.this, Settings.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_logout:
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(MainActivity.this, "Signed Out", Toast.LENGTH_LONG).show();
                        Intent logout = new Intent(MainActivity.this, StartActivity.class);
                        startActivity(logout);
                        finish();
                        break;
                    default:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                                new HomeFragment()).commit();
                        toolbar.setTitle("Home");
                        break;
                }

                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        FirebaseAuth auth = FirebaseAuth.getInstance();
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(MainActivity.this);
        if (acct != null) {
            personName = acct.getDisplayName();
            personEmail = acct.getEmail();
            personPhoto = acct.getPhotoUrl();
        } else {
            personName = Objects.requireNonNull(auth.getCurrentUser()).getDisplayName();
            personPhoto = auth.getCurrentUser().getPhotoUrl();
            personEmail = auth.getCurrentUser().getEmail();
        }
        View header = navigationView.getHeaderView(0);
        name = header.findViewById(R.id.usrname);
        email = header.findViewById(R.id.usremail);
        img = header.findViewById(R.id.usrimg);
        name.setText(personName);
        email.setText(personEmail);
        Glide.with(this).load(personPhoto).circleCrop().placeholder(R.mipmap.profileloader).into(img);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.pfimg);
        View view = menuItem.getActionView();
        CircleImageView img = view.findViewById(R.id.prfimg);
        Glide.with(this).load(personPhoto).circleCrop().placeholder(R.mipmap.profileloader).into(img);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        switchPf = sharedPreferences.getBoolean(SWITCHPFIMG, Boolean.parseBoolean(SWITCHPFIMG));
        if (switchPf) {
            img.setVisibility(View.INVISIBLE);
        } else {
            img.setVisibility(View.VISIBLE);
        }

        return true;
    }

    //    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_logout) {
//            FirebaseAuth.getInstance().signOut();
//            Toast.makeText(this, "Logged Out", Toast.LENGTH_LONG).show();
//            Intent intent = new Intent(MainActivity.this,StartActivity.class);
//            startActivity(intent);
//            finish();
//            return true;
//        }
//        if (id == R.id.action_settings) {
//            Intent sett = new Intent(MainActivity.this,Settings.class);
//            startActivity(sett);
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, AppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}