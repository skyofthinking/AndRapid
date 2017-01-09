package com.joyue.tech.gankio.ui.activity;

import android.os.Bundle;

import com.joyue.tech.core.ui.activity.RapidSearchActivity;
import com.joyue.tech.gankio.db.UserFav;

/**
 * @author JiangYH
 */
public class ExRapidSearchActivity extends RapidSearchActivity {

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        testdb();
    }

    public void testdb() {
        UserFav venza = new UserFav();
        venza.vin = "499499449";
        venza.make = "Toyota";
        venza.model = "Venza";
        venza.year = 2013;
        venza.save();



    }

}
