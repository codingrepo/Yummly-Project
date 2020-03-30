package com.example.yummlyteam.yummly_project;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import com.example.yummlyteam.app.Util;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricTestRunner.class)
public class UtilTest {

    private Context context;
    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
    }

    @Test
    public void isNetworkConnectionAvailableTest() {
        boolean isNetworkConnectionAvailableFlag = false;
        isNetworkConnectionAvailableFlag = Util.isNetworkConnectionAvailable(context);
        assertThat(isNetworkConnectionAvailableFlag).isEqualTo(true);
    }

    @Test
    public void timeFormatter() {
        Integer timeInSeconds = 1235;
        assertThat(Util.timeFormatter(timeInSeconds)).isEqualTo("20m");
        timeInSeconds = 12035;
        assertThat(Util.timeFormatter(timeInSeconds)).isEqualTo("3h");
        assertThat(Util.timeFormatter(timeInSeconds)).isNotEqualTo("2h");
    }
}