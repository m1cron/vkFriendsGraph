package ru.micron;

import org.junit.Assert;
import org.junit.Test;

public class ConfigTest {

    @Test
    public void get() {
        Assert.assertNotNull(Config.get());
    }

    @Test
    public void getDb_url() {
        Assert.assertNotNull(Config.get().getDb_url());
    }

    @Test
    public void getDb_user() {
        Assert.assertNotNull(Config.get().getDb_user());
    }

    @Test
    public void getDb_password() {
        Assert.assertNotNull(Config.get().getDb_password());
    }
}