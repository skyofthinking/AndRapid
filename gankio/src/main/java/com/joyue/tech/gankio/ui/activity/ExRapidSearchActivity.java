package com.joyue.tech.gankio.ui.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;

import com.joyue.tech.core.ui.activity.RapidSearchActivity;
import com.joyue.tech.core.utils.TLog;
import com.joyue.tech.gankio.db.DbOpenHelper;
import com.joyue.tech.gankio.model.UserFav;
import com.joyue.tech.gankio.model.UserFavModel;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;
import com.squareup.sqlbrite.SqlBrite.Query;

import java.util.concurrent.atomic.AtomicInteger;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author JiangYH
 */
public class ExRapidSearchActivity extends RapidSearchActivity {

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        DbOpenHelper dbOpenHelper = new DbOpenHelper(mContext);
        SqlBrite sqlBrite = new SqlBrite.Builder().build();
        BriteDatabase db = sqlBrite.wrapDatabaseHelper(dbOpenHelper, Schedulers.io());

        db.execute(UserFavModel.DELETE_ALL);

        Observable<Query> users = db.createQuery("UserFav", UserFavModel.GET_ALL);
        users.subscribe(new Action1<Query>() {
            @Override
            public void call(Query query) {
                Cursor cursor = query.run();
            }
        });

        test1(users, db);
        test2(users, db);
        test3(users, db);
    }

    public void test1(Observable<Query> users, BriteDatabase db ) {
        final AtomicInteger queries = new AtomicInteger();
        Subscription s = users.subscribe(new Action1<Query>() {
            @Override public void call(Query query) {
                queries.getAndIncrement();
                TLog.d(TAG, query.toString());
            }
        });
        System.out.println("Queries: " + queries.get()); // Prints 1

        db.insert("UserFav", createUser(1, "Jake Wharton"));
        db.insert("UserFav", createUser(2, "Matt Precious"));
        s.unsubscribe();

        db.insert("UserFav", createUser(3, "Alec Strong"));

        System.out.println("Queries: " + queries.get()); // Prints 3
    }


    public void test2(Observable<Query> users, BriteDatabase db ) {
        final AtomicInteger queries = new AtomicInteger();
        Subscription s = users.subscribe(new Action1<Query>() {
            @Override public void call(Query query) {
                queries.getAndIncrement();
                TLog.d(TAG, query.toString());
            }
        });
        System.out.println("Queries: " + queries.get()); // Prints 1

        db.insert("UserFav", createUser(11, "Jake Wharton"));
        db.insert("UserFav", createUser(12, "Matt Precious"));
        s.unsubscribe();

        db.insert("UserFav", createUser(13, "Alec Strong"));

        System.out.println("Queries: " + queries.get()); // Prints 3
    }

    public void test3(Observable<Query> users, BriteDatabase db ) {
        final AtomicInteger queries = new AtomicInteger();
        users.subscribe(new Action1<Query>() {
            @Override public void call(Query query) {
                queries.getAndIncrement();
                TLog.d(TAG, query.toString());
            }
        });
        System.out.println("Queries: " + queries.get()); // Prints 1

        BriteDatabase.Transaction transaction = db.newTransaction();
        try {
            db.insert("UserFav", createUser(21, "Jake Wharton"));
            db.insert("UserFav", createUser(22, "Matt Precious"));
            db.insert("UserFav", createUser(23, "Alec Strong"));
            transaction.markSuccessful();
        } finally {
            transaction.end();
        }

        System.out.println("Queries: " + queries.get()); // Prints 2
    }

    public ContentValues createUser(long id, String name) {
        return UserFav.FACTORY.marshal().id(id).name(name).asContentValues();
    }


}
