package uni.fmi.masters.fireorganizer.ui.home;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import uni.fmi.masters.fireorganizer.R;
import uni.fmi.masters.fireorganizer.Authentication.RegisterActivity;

public class HomeFragment extends Fragment {

    ImageView homeAvatarIV;
    TextView greetingsTV;
    FirebaseAuth fAuth;
    FirebaseFirestore db;
    DocumentReference documentReference;
    String userID;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        homeAvatarIV = root.findViewById(R.id.homeImageView);
        greetingsTV = root.findViewById(R.id.greetingsTextView);

        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();

        documentReference = db.collection(RegisterActivity.COLLECTION_USERS).document(userID);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String avatarUri = documentSnapshot.getString(RegisterActivity.FIREBASE_AVATAR_PATH);
                Picasso.get().load(avatarUri).into(homeAvatarIV);
                greetingsTV.setText("Welcome, " + documentSnapshot.getString(RegisterActivity.FIREBASE_FIRST_NAME));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Something gone wrong", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}