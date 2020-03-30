package com.example.yummlyteam.app.search;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yummlyteam.app.adapter.RecipeListAdapter;
import com.example.yummlyteam.app.model.Match;
import com.example.yummlyteam.app.model.RecipeSearchList;
import com.example.yummlyteam.yummly_project.R;

import java.util.ArrayList;
import java.util.List;


public class RecipeFragment extends Fragment {
    private static final String TAG = RecipeFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private RecipeViewModel mViewModel;
    private  RecipeListAdapter recipeListAdapter;

    public static RecipeFragment newInstance() {
        return new RecipeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recipe_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(RecipeViewModel.class);

        recyclerView = getView().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recipeListAdapter = new RecipeListAdapter(new ArrayList<Match>());
        recyclerView.setAdapter(recipeListAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    mViewModel.nextSearchPage();
                }
            }
        });

        searchListObserver();
        nextSearchPageObserve();
    }

    //Observe change in SearchList
    private void searchListObserver(){
        mViewModel.getSearchList().observe(getViewLifecycleOwner(), new Observer<RecipeSearchList>() {
            @Override
            public void onChanged(RecipeSearchList recipeSearchList) {
                if (recipeSearchList == null || recipeSearchList.getMatches() == null) { // clear the list
                    ((RecipeListAdapter) recyclerView.getAdapter()).clearList();
                }else {
                    Log.d(TAG, "RecipeSearchList data chnaged" + recipeSearchList.toString());
                    List<Match> matches = recipeSearchList.getMatches();
                    recipeListAdapter.addItems(matches);
                    recipeListAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void nextSearchPageObserve(){

        final Observer<Integer> currentSearchPageObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer currentSearchPage) {
                mViewModel.fetchRecipeSearchList();
            }
        };
        mViewModel.getCurrentSearchPage().observe(getViewLifecycleOwner(), currentSearchPageObserver);
    }
}
