package com.kj.cherryengineering20.bottomBarFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.kj.cherryengineering20.R;
import com.kj.cherryengineering20.employees.EmployeeDatabase;
import com.kj.cherryengineering20.homeFragments.CasesCompletedFragment;

import java.util.List;

public class HomeFragment extends Fragment {

    private SharedViewModel viewModel;
    private static final String ARG_NAME = "argName";

    public static final HomeFragment newInstance(String param1) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    // we are using onViewCreated in liu of deprecated onActivityCreated()

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        EmployeeDatabase db = new EmployeeDatabase(getContext());
        List<String> names = db.getEmployeeNames();

        ListView listView = view.findViewById(R.id.employees_listView);
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, names);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                viewModel.setText(names.get(i));
                FragmentTransaction fr = getParentFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new CasesCompletedFragment()).addToBackStack(null);
                fr.commit();
            }
        });
    }
}
