package com.example.yummlyteam.app.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yummlyteam.app.api.ApiClient;
import com.example.yummlyteam.app.api.ApiInterface;
import com.example.yummlyteam.yummly_project.R;
import com.example.yummlyteam.app.Util;
import com.example.yummlyteam.app.model.Match;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {

  private List<Match> recipeList;

  public RecipeListAdapter(List<Match> recipeList) {
    this.recipeList = recipeList;
  }

  @NonNull
  @Override
  public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
    View view = LayoutInflater.from(parent.getContext()).
        inflate(R.layout.recipe_row, parent, false);
    return new RecipeViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int i) {

    Match recipe = recipeList.get(i);
    if(recipe.getRecipeName() != null){
      recipeViewHolder.recipeName.setText(recipe.getRecipeName());

      if(recipe.getTotalTimeInSeconds() != null){
        recipeViewHolder.totalTime.setText(Util.timeFormatter(recipe.getTotalTimeInSeconds()));
      }
      recipeViewHolder.totalCalories.setText("--");
      if(recipe.getIngredients() != null){
        String sizeIngredients = ""+recipe.getIngredients().size();
        recipeViewHolder.ingredients.setText(sizeIngredients);
      }

      if(recipe.getSmallImageUrls() != null) {
        Picasso.with(recipeViewHolder.itemView.getContext())
                .load(recipe.getSmallImageUrls().get(0))
                .networkPolicy(
                        Util.isNetworkConnectionAvailable(recipeViewHolder.itemView.getContext()) ?
                                NetworkPolicy.NO_CACHE : NetworkPolicy.OFFLINE)
                .into(recipeViewHolder.recipeImageView);
      }

      if(recipe.getFlavors()!=null) {
        if (recipe.getFlavors().getBitter()!=null && recipe.getFlavors().getBitter().compareTo(1d)==0) {
          recipeViewHolder.recipeBitternessIndicator.setVisibility(View.VISIBLE);
        }
      }
    }

    ApiInterface apiService =
            ApiClient.getClient().create(ApiInterface.class);
    Call<Match> getRecipeCall = apiService.getRecipe(recipe.getId());

    getRecipeCall.enqueue(new Callback<Match>() {
      @Override
      public void onResponse(Call<Match> call, Response<Match> response) {

      }

      @Override
      public void onFailure(Call<Match> call, Throwable t) {

      }
    });
  }

  @Override
  public int getItemCount() {
    return recipeList.size();
  }

  public boolean addItems(List<Match> newItems) {
    if (recipeList !=null) {
      int preSize = recipeList.size();
      recipeList.addAll(preSize, newItems);
      notifyDataSetChanged();
      return true;
    }
    return false;
  }

  public void clearList() {
    if (recipeList != null) {
      recipeList.clear();
    }
    notifyDataSetChanged();
  }


  public static class RecipeViewHolder extends RecyclerView.ViewHolder {

    TextView ingredients, recipeName, totalCalories, totalTime, recipeBitternessIndicator;
    ImageView recipeImageView;

    public RecipeViewHolder(@NonNull View itemView) {
      super(itemView);
      recipeName = itemView.findViewById(R.id.recipeName);
      ingredients = itemView.findViewById(R.id.ingredients);
      totalCalories = itemView.findViewById(R.id.totalCalories);
      totalTime = itemView.findViewById(R.id.totalTime);
      recipeImageView = itemView.findViewById(R.id.recipeImageView);
      recipeBitternessIndicator = itemView.findViewById(R.id.bitter_label);
    }
  }

}
