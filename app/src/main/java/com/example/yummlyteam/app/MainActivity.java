package com.example.yummlyteam.app;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;

import com.example.yummlyteam.app.search.RecipeFragment;
import com.example.yummlyteam.app.search.RecipeViewModel;
import com.example.yummlyteam.yummly_project.R;

public class MainActivity extends AppCompatActivity {
  private RecipeViewModel mViewModel;
  private SearchView searchView;


  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
    searchView = findViewById(R.id.recipeSearchBar);
    searchView.setQueryHint("Search Recipe");
    searchView.setIconified(false);

    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String s) {
        mViewModel.setSearchQuery(s);
        return false;
      }
      @Override
      public boolean onQueryTextChange(String s) {
        if (s.isEmpty()) {
          mViewModel.clearSearchList();
        }
        return false;
      }
    });

    searchQueryObserver();
    displayResponseFragment(savedInstanceState);
  }

  private void displayResponseFragment(Bundle savedInstanceState){
    if (findViewById(R.id.fragment_container) != null) {
      if (savedInstanceState != null) {
        return;
      }
      RecipeFragment firstFragment = new RecipeFragment();
      firstFragment.setArguments(getIntent().getExtras());
      getSupportFragmentManager().beginTransaction()
              .add(R.id.fragment_container, firstFragment).commit();
    }
  }

  @VisibleForTesting
  public void searchQueryObserver(){
    final Observer<String> queryObserver = new Observer<String>() {
      @Override
      public void onChanged(@Nullable String s) {
        mViewModel.fetchRecipeSearchList();
      }
    };
    mViewModel.getSearchQuery().observe(this, queryObserver);
  }
}
