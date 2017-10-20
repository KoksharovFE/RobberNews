package ru.astralight.koksharov.robbernews.views;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import ru.astralight.koksharov.robbernews.DB.RobberNewsContentProvider;
import ru.astralight.koksharov.robbernews.R;
import ru.astralight.koksharov.robbernews.containers.HTML5WebView;
import ru.astralight.koksharov.robbernews.containers.ViewsListItem;
import ru.astralight.koksharov.robbernews.containers.ViewsListItemTagsAdapter;

public class ArticleExpanded extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_expanded);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
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

        Cursor cursor = getContentResolver().query(RobberNewsContentProvider.PROVIDER_ARTICLE,
                RobberNewsContentProvider.PROJECTION_ARTICLE,
                RobberNewsContentProvider.COLUMN_ID + "= ? ", new String[]{String.valueOf(fName)}, RobberNewsContentProvider.COLUMN_LIKES_NUMBER);
        if (cursor.moveToFirst()) {//todo theme filter, likes priority, data priority
            do {
                Integer _id = cursor.getInt(cursor.getColumnIndex(RobberNewsContentProvider.COLUMN_ID));
                String image = cursor.getString(cursor.getColumnIndex(RobberNewsContentProvider.COLUMN_IMAGE));
                String title = cursor.getString(cursor.getColumnIndex(RobberNewsContentProvider.COLUMN_TITLE));
                String tagsCloud = cursor.getString(cursor.getColumnIndex(RobberNewsContentProvider.COLUMN_TAGS_CLOUD));
                String preview = cursor.getString(cursor.getColumnIndex(RobberNewsContentProvider.COLUMN_PREVIEW));
                String text = cursor.getString(cursor.getColumnIndex(RobberNewsContentProvider.COLUMN_TEXT));
                String theme = cursor.getString(cursor.getColumnIndex(RobberNewsContentProvider.COLUMN_THEME));
                Integer likes = cursor.getInt(cursor.getColumnIndex(RobberNewsContentProvider.COLUMN_LIKES_NUMBER));
                Integer forumArticle = cursor.getInt(cursor.getColumnIndex(RobberNewsContentProvider.COLUMN_IS_FORUM_ARTICLE));
                Integer authorId = cursor.getInt(cursor.getColumnIndex(RobberNewsContentProvider.COLUMN_AUTHOR_ID));
                String dateTime = cursor.getString(cursor.getColumnIndex(RobberNewsContentProvider.COLUMN_DATE_TIME));
//                ViewsListItem item = new ViewsListItem(_id, title, preview, image, tagsCloud, likes);
            } while (cursor.moveToNext());
        }

        final RecyclerView tagsCloudView = (RecyclerView) findViewById(R.id.articleExpandTagsCloud);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        tagsCloudView.setItemAnimator(itemAnimator);

        ArrayList<String> tags = new ArrayList<String>();
        if (articlesList.get(position).getTags() != null) {
            Collections.addAll(tags, articlesList.get(position).getTags());
        }
        tagsCloudView.setAdapter(
                new ViewsListItemTagsAdapter(tags));
        tagsCloudView.setLayoutManager(new GridLayoutManager(this, 7));
        tagsCloudView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = tagsCloudView.indexOfChild(v);

                Log.i("Tags item position is ",String.valueOf(itemPosition));

            }
        });

        //data == html data which you want to load
        TextView webview = (TextView)this.findViewById(R.id.articleExpandedWebView);//todo return webview

        webview.setText(getString(R.string.html_example));


//        webview.setWebChromeClient(new WebChromeClient());
//        webview.setWebViewClient(new WebViewClient());
//        webview.getSettings().setJavaScriptEnabled(true);
//        webview.getSettings().setPluginState(WebSettings.PluginState.ON);
//        webview.getSettings().setJavaScriptEnabled(true);
//        webview.loadDataWithBaseURL("", getString(R.string.large_text), "text/html", "UTF-8", "");

//        if (savedInstanceState != null) {
//            webview.restoreState(savedInstanceState);
//        } else {
////            webview.loadUrl("https://stackoverflow.com/questions/3815090/webview-and-html5-video");
////            webview.loadUrl("https://www.youtube.com/watch?v=b499NNNdRqw");
//
////            webview.loadData(Html.fromHtml(getString(R.string.html_example)), "text/html; charset=utf-8", "UTF-8");
////            webview.loadDataWithBaseURL("about:blank", String.valueOf(Html.fromHtml(getString(R.string.html_example))),"text/html", "utf-8", null);
//
//            Spannable sp = new SpannableString("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + Html.fromHtml(getString(R.string.html_example)));
//            Linkify.addLinks(sp, Linkify.ALL);
//            final String html = "<body>" + Html.toHtml(sp) + "</body>";
//            webview.loadData(html, "text/html", "utf-8");
//        }

    }

}
