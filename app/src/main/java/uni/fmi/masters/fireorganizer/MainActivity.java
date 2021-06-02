package uni.fmi.masters.fireorganizer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import uni.fmi.masters.fireorganizer.Authentication.LoginActivity;
import uni.fmi.masters.fireorganizer.ui.profile.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public static Context contextOfApplication;
    public static Context getContextOfApplication() {
        return contextOfApplication;
    }
    public static boolean isLogged = true;

    FirebaseAuth fAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_profile, R.id.nav_notes)
//                .setDrawerLayout(drawer) this is deprecate and should use setOpenableLayout()
                .setOpenableLayout(drawer)
                .build();
        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                int id = item.getItemId();
                if(id == R.id.nav_logout){
                    isLogged = false;
                    fAuth.signOut();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }else {
                    NavigationUI.onNavDestinationSelected(item,navController);
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        fAuth = FirebaseAuth.getInstance();
        View headerView = navigationView.getHeaderView(0);
        TextView username = headerView.findViewById(R.id.headerUsernameTextView);
        TextView email = headerView.findViewById(R.id.headerEmailTextView);

        username.setText(fAuth.getCurrentUser().getDisplayName());
        email.setText(fAuth.getCurrentUser().getEmail());

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if(item.getItemId() == R.id.action_settings){
//            Toast.makeText(this, "Setting Menu is Clicked", Toast.LENGTH_SHORT).show();
//        }
//        return super.onOptionsItemSelected(item);
//    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}