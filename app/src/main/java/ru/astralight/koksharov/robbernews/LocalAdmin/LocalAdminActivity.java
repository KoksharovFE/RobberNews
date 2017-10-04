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

    final String[] spinnerData = {RobberNewsContentProvider.TABLE_USER, RobberNewsContentProvider.TABLE_ARTICLE,
            RobberNewsContentProvider.TABLE_ARTICLE_COMMENT, RobberNewsContentProvider.TABLE_FORUM_ARTICLE,
            RobberNewsContentProvider.TABLE_FORUM_ARTICLE_COMMENT};

    int currentFragmentNumber = -1;

    HashMap<String, String> userData = new HashMap<>();

    View createUserFragment, createArticleFragment, createArticleCommentFragment,
            createForumArticleFragment, createForumArticleCommentFragment;

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
        createForumArticleFragment = findViewById(R.id.forum_article_fragment);
        createForumArticleCommentFragment = findViewById(R.id.forum_article_comment_fragment);

        createUserFragment.setVisibility(View.GONE);
        createArticleFragment.setVisibility(View.GONE);
        createArticleCommentFragment.setVisibility(View.GONE);
        createForumArticleFragment.setVisibility(View.GONE);
        createForumArticleCommentFragment.setVisibility(View.GONE);


        final ArrayAdapter<String> chooseAdapter = new ArrayAdapter<String>(this,  android.R.layout.simple_spinner_item, spinnerData);//,R.layout.spinner_layout R.id.choose_spinner

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
                choseFragment(spinnerData[i]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                choseFragment(spinnerData[0]);
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
        createForumArticleFragment.setVisibility(View.GONE);
        createForumArticleCommentFragment.setVisibility(View.GONE);

        int chosenFragment = 0;
        //todo switch / finals
        for (int i = 0; i < spinnerData.length; i++) {
            if (spinnerData[i].equals(name)){
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
            case 3:
                createForumArticleFragment.setVisibility(View.VISIBLE);
                break;
            case 4:
                createForumArticleCommentFragment.setVisibility(View.VISIBLE);
                break;
        }
        TextView textView = (TextView) findViewById(R.id.chose_common_text_view);
        textView.setText( getString(R.string.chose_fragment) );
    }

    void addInDb(){
        TextView textView = (TextView) findViewById(R.id.chose_common_text_view);
        textView.setText("AddInDb");
        try {
            if (userData.get("WRITE").equals(RobberNewsContentProvider.TABLE_USER)){
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
            }
//            Map<String, String> data = new HashMap<>();
//            data.put("WRITE", spinnerData[currentFragmentNumber]);
//            onFragmentInteraction(data);
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
            if (data.get("WRITE").equals(spinnerData[0])) {//USER{
                userData.putAll(data);
            }
        } catch (Exception ex){
            textView.setText(ex.getMessage());
            ex.printStackTrace();
        }
        textView.setText(userData.toString());
        Log.i("URI ", userData.toString());
    }


//    public void onActivityCreated(Bundle savedInstanceState) {//For Fragment
//        this.onActivityCreated(savedInstanceState);
//    }
}
