package ru.astralight.koksharov.robbernews.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import ru.astralight.koksharov.robbernews.DB.RobberNewsContentProvider;
import ru.astralight.koksharov.robbernews.R;

public class ArticleExpanded extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_expanded);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent intent = getIntent();

        Integer fName = intent.getIntExtra(RobberNewsContentProvider.COLUMN_ID, -1);


//        Log.i("AE bundle _id: ", savedInstanceState.getInt(RobberNewsContentProvider.COLUMN_ID) + "");

        Log.w("AE intent _id: ", fName + "");
    }

}
