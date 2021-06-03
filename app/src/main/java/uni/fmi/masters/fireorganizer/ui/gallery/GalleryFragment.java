package uni.fmi.masters.fireorganizer.ui.gallery;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import uni.fmi.masters.fireorganizer.Authentication.RegisterActivity;
import uni.fmi.masters.fireorganizer.R;
import uni.fmi.masters.fireorganizer.model.ImageModel;
import uni.fmi.masters.fireorganizer.model.Note;
import uni.fmi.masters.fireorganizer.ui.notes.NotesFragment;
import uni.fmi.masters.fireorganizer.ui.profile.ProfileFragment;

public class GalleryFragment extends Fragment {

    RecyclerView imagesList;
    String userID;
    FirebaseAuth fAuth;
    FirebaseFirestore db;
    FirestoreRecyclerAdapter<ImageModel, ImageViewHolder> imageAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        fAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        Query query = db.collection(RegisterActivity.COLLECTION_USERS).document(userID)
                .collection(ProfileFragment.FIREBASE_COLLECTION_IMAGES).orderBy(ProfileFragment.FIREBASE_IMAGE_TIMESTAMP, Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ImageModel> allImages = new FirestoreRecyclerOptions.Builder<ImageModel>()
                .setQuery(query,ImageModel.class)
                .build();

        imageAdapter = new FirestoreRecyclerAdapter<ImageModel, ImageViewHolder>(allImages) {
            @Override
            protected void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, int i, @NonNull ImageModel imageModel) {
                imageViewHolder.timestamp.setText(imageModel.getUploadedAt());
                Picasso.get().load(imageModel.getAvatarPath())
                        .fit()
                        .centerCrop()
                        .into(imageViewHolder.galleryImg);
            }

            @NonNull
            @Override
            public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
                return new ImageViewHolder(view);
            }
        };

        imagesList = root.findViewById(R.id.galleryRecyclerView);
        imagesList.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        imagesList.setAdapter(imageAdapter);



        return root;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        TextView timestamp;
        ImageView galleryImg;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            timestamp = itemView.findViewById(R.id.imageTimeStampTextView);
            galleryImg = itemView.findViewById(R.id.galleryImageView);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        imageAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();

        if(imageAdapter != null) {
            imageAdapter.stopListening();
        }
    }
}