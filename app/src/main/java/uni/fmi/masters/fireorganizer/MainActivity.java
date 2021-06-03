package uni.fmi.masters.fireorganizer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import uni.fmi.masters.fireorganizer.Authentication.LoginActivity;
import uni.fmi.masters.fireorganizer.Authentication.RegisterActivity;
import uni.fmi.masters.fireorganizer.ui.notes.AddNoteActivity;
import uni.fmi.masters.fireorganizer.ui.profile.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public static Context contextOfApplication;
    public static Context getContextOfApplication() {
        return contextOfApplication;
    }
    public static boolean isLogged = true;

    FirebaseAuth fAuth;
    FirebaseFirestore db;
    DocumentReference documentReference;
    String userID;



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
        db = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        View headerView = navigationView.getHeaderView(0);
        TextView headerUsername = headerView.findViewById(R.id.headerUsernameTextView);
        TextView headerEmail = headerView.findViewById(R.id.headerEmailTextView);
        ImageView headerAvatar = headerView.findViewById(R.id.headerImageView);

        documentReference = db.collection(RegisterActivity.COLLECTION_USERS).document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(isLogged){
                    String fname = value.getString(RegisterActivity.FIREBASE_FIRST_NAME);
                    String lname = value.getString(RegisterActivity.FIREBASE_LAST_NAME);
                    String username = fname + " " + lname;
                    String email = value.getString(RegisterActivity.FIREBASE_EMAIL);
                    String avatarUri = value.getString(RegisterActivity.FIREBASE_AVATAR_PATH);


                    headerUsername.setText(username);
                    headerEmail.setText(email);
                    Picasso.get().load(avatarUri).into(headerAvatar);
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}