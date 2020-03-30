package com.example.yummlyteam.yummly_project;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.test.core.app.ActivityScenario;

import com.example.yummlyteam.app.MainActivity;
import com.example.yummlyteam.app.search.RecipeViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class MainActivityTest {
    private  ActivityScenario scenario;
    @Mock RecipeViewModel recipeViewModel;
    @Mock Observer<String> queryObserver;
    @Before
    public void setUp() throws Exception {
         scenario = ActivityScenario.launch(MainActivity.class);
         scenario.moveToState(Lifecycle.State.CREATED);

    }

    @Test
    public void searchQueryTest() {
    }

    public void clearQuery(){

    }
}