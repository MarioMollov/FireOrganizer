package uni.fmi.masters.fireorganizer.ui.notes;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import uni.fmi.masters.fireorganizer.R;

public class NodeDetails extends AppCompatActivity {

    Intent data;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        data = getIntent();


        TextView titleTV = findViewById(R.id.nodeDetailsTitleTextView);
        TextView contentTV = findViewById(R.id.nodeDetailsContentTextView);
        contentTV.setMovementMethod(new ScrollingMovementMethod());

        contentTV.setText(data.getStringExtra("content"));
        titleTV.setText(data.getStringExtra("title"));
        contentTV.setBackgroundColor(getResources().getColor(data.getIntExtra("bgColor",0),null));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),EditNoteActivity.class);
                intent.putExtra("title", data.getStringExtra("title"));
                intent.putExtra("content", data.getStringExtra("content"));
                intent.putExtra("noteID", data.getStringExtra("noteID"));
                startActivity(intent);
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}