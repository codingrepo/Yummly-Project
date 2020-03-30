package com.example.yummlyteam.yummly_project;


import androidx.lifecycle.Observer;
import com.example.yummlyteam.app.model.RecipeSearchList;
import com.example.yummlyteam.app.search.RecipeViewModel;

import org.mockito.Mock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class RecipeViewModelTest {
    private RecipeViewModel recipeViewModel;
    @Mock
    Observer<String> queryObserver;
    @Mock
    Observer<RecipeSearchList> recipeSearchListObserver;
    @Mock
    Observer<Integer> pageObserver;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeViewModel = new RecipeViewModel();
        recipeViewModel.getSearchQuery().observeForever(queryObserver);
        recipeViewModel.getSearchList().observeForever(recipeSearchListObserver);
        recipeViewModel.getCurrentSearchPage().observeForever(pageObserver);
    }

    @Test
    public void testNull() {
        assertNotNull(recipeViewModel.getSearchQuery());
        assertNotNull(recipeViewModel.getSearchList());
        assertNotNull(recipeViewModel.getCurrentSearchPage());
    }

    @Test
    public void setSearchQueryTest() {
        assertThat(recipeViewModel.getSearchQuery().getValue()).isNull();
        recipeViewModel.setSearchQuery("TestQuery");
        String val = recipeViewModel.getSearchQuery().getValue();
        assertThat(val).isEqualTo("TestQuery");
    }
}