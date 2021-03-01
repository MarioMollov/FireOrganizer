package uni.fmi.masters.fireorganizer.ui.notes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import uni.fmi.masters.fireorganizer.R;
import uni.fmi.masters.fireorganizer.model.Adapter;

public class NotesFragment extends Fragment {

    RecyclerView notesList;
    Adapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notes, container, false);

        notesList= root.findViewById(R.id.nodeList);

        List<String> titles = new ArrayList<>();
        List<String> contents = new ArrayList<>();

        titles.add("First new title.");
        contents.add("First content sample");
        titles.add("Second new title.");
        contents.add("Second content sample Second content sample Second content sample");
        titles.add("Third new title.");
        contents.add("Third content sample");

        adapter = new Adapter(titles,contents);
        notesList.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        notesList.setAdapter(adapter);

        return root;
    }
}