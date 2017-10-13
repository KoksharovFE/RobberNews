package ru.astralight.koksharov.robbernews.LocalAdmin;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import ru.astralight.koksharov.robbernews.DB.RobberNewsContentProvider;
import ru.astralight.koksharov.robbernews.R;

public class LocalAdminActivity extends AppCompatActivity implements CreateUserFragment.OnFragmentInteractionListener,
        CreateArticleFragment.OnFragmentInteractionListener,
        CreateArticleCommentFragment.OnFragmentInteractionListener,
        CreateForumArticleFragment.OnFragmentInteractionListener,
        CreateForumArticleCommentFragment.OnFragmentInteractionListener{



    int currentFragmentNumber = -1;

    HashMap<String, String> userData = new HashMap<>();
    HashMap<String, String> articleData = new HashMap<>();
    HashMap<String, String> articleCommentData = new HashMap<>();

    View createUserFragment, createArticleFragment, createArticleCommentFragment;
//            createForumArticleFragment, createForumArticleCommentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_admin);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        createUserFragment = findViewById(R.id.la_user_fragment);
        createUserFragment = findViewById(R.id.user_fragment);
        createArticleFragment = findViewById(R.id.article_fragment);
        createArticleCommentFragment = findViewById(R.id.article_comment_fragment);
//        createForumArticleFragment = findViewById(R.id.forum_article_fragment);
//        createForumArticleCommentFragment = findViewById(R.id.forum_article_comment_fragment);

        createUserFragment.setVisibility(View.GONE);
        createArticleFragment.setVisibility(View.GONE);
        createArticleCommentFragment.setVisibility(View.GONE);
//        createForumArticleFragment.setVisibility(View.GONE);
//        createForumArticleCommentFragment.setVisibility(View.GONE);


        final ArrayAdapter<String> chooseAdapter = new ArrayAdapter<String>(this,  android.R.layout.simple_spinner_item, RobberNewsContentProvider.SPINNER_DATA);//,R.layout.spinner_layout R.id.choose_spinner

//        chooseAdapter.addAll(spinnerData);

        chooseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner chooseSpinner = (Spinner) findViewById(R.id.choose_spinner);
        chooseSpinner.setAdapter(chooseAdapter);
        // заголовок
        chooseSpinner.setPrompt(getString(R.string.Table));
        // выделяем элемент
        chooseSpinner.setSelection(0);
        chooseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                choseFragment(RobberNewsContentProvider.SPINNER_DATA[i]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                choseFragment(RobberNewsContentProvider.SPINNER_DATA[0]);
            }
        });

        FloatingActionButton addFab = (FloatingActionButton) findViewById(R.id.local_admin_add);
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addInDb();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    void choseFragment(String name){
        createUserFragment.setVisibility(View.GONE);
        createArticleFragment.setVisibility(View.GONE);
        createArticleCommentFragment.setVisibility(View.GONE);
//        createForumArticleFragment.setVisibility(View.GONE);
//        createForumArticleCommentFragment.setVisibility(View.GONE);

        int chosenFragment = 0;
        for (int i = 0; i < RobberNewsContentProvider.SPINNER_DATA.length; i++) {
            if (RobberNewsContentProvider.SPINNER_DATA[i].equals(name)){
                chosenFragment = i;
            }
        }
        currentFragmentNumber = chosenFragment;
        switch (chosenFragment){
            case 0:
                createUserFragment.setVisibility(View.VISIBLE);
                break;
            case 1:
                createArticleFragment.setVisibility(View.VISIBLE);
                break;
            case 2:
                createArticleCommentFragment.setVisibility(View.VISIBLE);
                break;
//            case 3:
//                createForumArticleFragment.setVisibility(View.VISIBLE);
//                break;
//            case 4:
//                createForumArticleCommentFragment.setVisibility(View.VISIBLE);
//                break;
        }
        TextView textView = (TextView) findViewById(R.id.chose_common_text_view);
        textView.setText( getString(R.string.chose_fragment) );
    }

    void addInDb(){
        TextView textView = (TextView) findViewById(R.id.chose_common_text_view);
        textView.setText("AddInDb");
        try {
            switch(RobberNewsContentProvider.SPINNER_DATA[currentFragmentNumber]){
                case RobberNewsContentProvider.TABLE_USER:
                    if ( userData.get("WRITE").equals(RobberNewsContentProvider.TABLE_USER) ){
                        ContentResolver resolver = getContentResolver();
                        ContentValues values = new ContentValues();

                        Cursor cursor = resolver.query(RobberNewsContentProvider.PROVIDER_USER, RobberNewsContentProvider.PROJECTION_USER, null, null, null);

                        if (cursor != null) {

                            values.put(RobberNewsContentProvider.COLUMN_NAME, userData.get(RobberNewsContentProvider.COLUMN_NAME));
                            values.put(RobberNewsContentProvider.COLUMN_SURNAME, userData.get(RobberNewsContentProvider.COLUMN_SURNAME));
                            values.put(RobberNewsContentProvider.COLUMN_PASSWORD, userData.get(RobberNewsContentProvider.COLUMN_PASSWORD));
                            values.put(RobberNewsContentProvider.COLUMN_ACCESS_MODE, userData.get(RobberNewsContentProvider.COLUMN_ACCESS_MODE));
                            values.put(RobberNewsContentProvider.COLUMN_REGISTER_DATE, userData.get(RobberNewsContentProvider.COLUMN_REGISTER_DATE) +
                                    " " + userData.get(RobberNewsContentProvider.COLUMN_DATE_TIME) );
                            values.put(RobberNewsContentProvider.COLUMN_AVATAR, userData.get(RobberNewsContentProvider.COLUMN_AVATAR));
                            values.put(RobberNewsContentProvider.COLUMN_ABOUT, userData.get(RobberNewsContentProvider.COLUMN_ABOUT));
                            resolver.insert(RobberNewsContentProvider.PROVIDER_USER, values);
                        }
                        cursor.close();
                        userData.clear();
                    }
                    break;
                case RobberNewsContentProvider.TABLE_ARTICLE:
                    if ( articleData.get("WRITE").equals(RobberNewsContentProvider.TABLE_ARTICLE) ){
                        ContentResolver resolver = getContentResolver();
                        ContentValues values = new ContentValues();

                        Cursor cursor = resolver.query(RobberNewsContentProvider.PROVIDER_ARTICLE, RobberNewsContentProvider.PROJECTION_ARTICLE, null, null, null);

                        if (cursor != null) {

                            values.put(RobberNewsContentProvider.COLUMN_IMAGE, articleData.get(RobberNewsContentProvider.COLUMN_IMAGE));
                            values.put(RobberNewsContentProvider.COLUMN_TITLE, articleData.get(RobberNewsContentProvider.COLUMN_TITLE));
                            values.put(RobberNewsContentProvider.COLUMN_TAGS_CLOUD, articleData.get(RobberNewsContentProvider.COLUMN_TAGS_CLOUD));
                            values.put(RobberNewsContentProvider.COLUMN_PREVIEW, articleData.get(RobberNewsContentProvider.COLUMN_PREVIEW));
                            values.put(RobberNewsContentProvider.COLUMN_TEXT, articleData.get(RobberNewsContentProvider.COLUMN_TEXT));
                            values.put(RobberNewsContentProvider.COLUMN_THEME, articleData.get(RobberNewsContentProvider.COLUMN_THEME));
                            values.put(RobberNewsContentProvider.COLUMN_LIKES_NUMBER, articleData.get(RobberNewsContentProvider.COLUMN_LIKES_NUMBER));
                            values.put(RobberNewsContentProvider.COLUMN_IS_FORUM_ARTICLE, articleData.get(RobberNewsContentProvider.COLUMN_IS_FORUM_ARTICLE));
                            values.put(RobberNewsContentProvider.COLUMN_AUTHOR_ID, articleData.get(RobberNewsContentProvider.COLUMN_AUTHOR_ID));
                            values.put(RobberNewsContentProvider.COLUMN_DATE_TIME, articleData.get(RobberNewsContentProvider.COLUMN_REGISTER_DATE) +
                                    " " + articleData.get(RobberNewsContentProvider.COLUMN_DATE_TIME) );
                            resolver.insert(RobberNewsContentProvider.PROVIDER_ARTICLE, values);
                        }
                        cursor.close();
                        articleData.clear();
                    }
                    break;
            }

        } catch (Exception ex){
            textView.setText("AddInDb fail uri");
        }
    }

    public void onFragmentInteraction(Map<String, String> data) {
        TextView textView = (TextView) findViewById(R.id.chose_common_text_view);
//        for( String str : data.keySet()){
//            userData.put(str, data.get(str));
//        }
        try {
            if (data.get("WRITE").equals(RobberNewsContentProvider.TABLE_USER)) {//USER{
                userData.putAll(data);
            }
            if (data.get("WRITE").equals(RobberNewsContentProvider.TABLE_ARTICLE)) {//ARTICLE{
                articleData.putAll(data);
            }
            if (data.get("WRITE").equals(RobberNewsContentProvider.TABLE_ARTICLE_COMMENT)) {//ARTICLE_COMMENT{
                articleCommentData.putAll(data);
            }
        } catch (Exception ex){
            textView.setText(ex.getMessage());
            ex.printStackTrace();
        }
        textView.setText(userData.toString() + "\r\n" + articleData.toString() + "\r\n" + articleCommentData.toString() );
        Log.i("URI ", userData.toString());
    }


//    public void onActivityCreated(Bundle savedInstanceState) {//For Fragment
//        this.onActivityCreated(savedInstanceState);
//    }
}
