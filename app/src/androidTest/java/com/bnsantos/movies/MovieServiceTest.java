package com.bnsantos.movies;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

/**
 * Created by bruno on 19/11/14.
 */
@Config(emulateSdk = 18)
@RunWith(MoviesTestRunner.class)
public class MovieServiceTest {

    @Test
    public void test1() {
        Assert.assertEquals(true, true);
    }

}
