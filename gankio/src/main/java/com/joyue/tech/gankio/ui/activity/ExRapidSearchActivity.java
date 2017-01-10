package com.joyue.tech.gankio.ui.activity;

import android.os.Bundle;

import com.joyue.tech.core.utils.TLog;
import com.joyue.tech.gankio.db.AppDatabase;
import com.joyue.tech.gankio.db.UserFav;
import com.joyue.tech.gankio.db.UserFav_Table;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.list.FlowQueryList;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;

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

        venza = new UserFav();
        venza.vin = "499499449";
        venza.make = "Toyota";
        venza.model = "Camry";
        venza.year = 2001;
        venza.save();

        FlowQueryList<UserFav> list = SQLite.select().from(UserFav.class).where(UserFav_Table.model.eq("Camry")).flowQueryList();
        for (int i = 0; i < list.size(); i++) {
            UserFav userFav = list.get(i);
            TLog.d(TAG, "UserFav " + userFav.vin + " " + userFav.make + " " + userFav.model + " " + userFav.year);
        }

        // run a transaction synchronous easily.
        DatabaseDefinition database = FlowManager.getDatabase(AppDatabase.class);
        database.executeTransaction(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                UserFav venza = new UserFav();
                venza.vin = "499499449";
                venza.make = "Toyota";
                venza.model = "Venza";
                venza.year = 2013;
                venza.save();

                venza = new UserFav();
                venza.vin = "499499449111";
                venza.make = "Toyota111";
                venza.model = "Venza111";
                venza.year = 2011;
                venza.save();
            }
        });
    }

}
