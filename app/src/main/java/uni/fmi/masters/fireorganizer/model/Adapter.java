package uni.fmi.masters.fireorganizer.model;

import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uni.fmi.masters.fireorganizer.ui.notes.NodeDetails;
import uni.fmi.masters.fireorganizer.R;

public class Adapter  extends RecyclerView.Adapter<Adapter.ViewHolder> {
    List<String> titles;
    List<String> contents;

    public Adapter(List<String> title, List<String> content){
        this.titles = title;
        this.contents = content;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_view_layout, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.noteTitle.setText(titles.get(position));
        holder.noteContent.setText(contents.get(position));
        int backgroundColor = getRandomColor();
        holder.myCardView.setCardBackgroundColor(holder.view.getResources().getColor(backgroundColor,null));

        holder.view.setOnClickListener((v) -> {
            Intent intent = new Intent(v.getContext(), NodeDetails.class);
            intent.putExtra("title", titles.get(position));
            intent.putExtra("content", contents.get(position));
            intent.putExtra("bgColor", backgroundColor);
            v.getContext().startActivity(intent);
        });
    }

    private int getRandomColor() {

        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.yellow);
        colorCode.add(R.color.lightGreen);
        colorCode.add(R.color.pink);
        colorCode.add(R.color.lighterPurple);
        colorCode.add(R.color.skyBlue);
        colorCode.add(R.color.gray);
        colorCode.add(R.color.blue);
        colorCode.add(R.color.greenLight);
        colorCode.add(R.color.lightPurple);

        Random randomColor = new Random();
        int number = randomColor.nextInt(colorCode.size());

        return  colorCode.get(number);
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView noteTitle, noteContent;
        CardView myCardView;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            noteTitle = itemView.findViewById(R.id.titles);
            noteContent = itemView.findViewById(R.id.content);
            myCardView = itemView.findViewById(R.id.noteCard);
            view = itemView;
        }
    }
}
