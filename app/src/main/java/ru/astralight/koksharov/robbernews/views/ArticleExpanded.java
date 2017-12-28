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
import android.widget.ImageView;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;

import ru.astralight.koksharov.robbernews.DB.RobberNewsContentProvider;
import ru.astralight.koksharov.robbernews.R;
import ru.astralight.koksharov.robbernews.containers.HTML5WebView;
import ru.astralight.koksharov.robbernews.containers.ViewsListItem;
import ru.astralight.koksharov.robbernews.containers.ViewsListItemTagsAdapter;
import ru.astralight.koksharov.robbernews.tasks.DownloadImageTask;

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

        Integer authorId = -1;

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
                authorId = cursor.getInt(cursor.getColumnIndex(RobberNewsContentProvider.COLUMN_AUTHOR_ID));
                String dateTime = cursor.getString(cursor.getColumnIndex(RobberNewsContentProvider.COLUMN_DATE_TIME));
//                ViewsListItem item = new ViewsListItem(_id, title, preview, image, tagsCloud, likes);

                //region Text Fields
                TextView titleView = (TextView) findViewById(R.id.articleExpandTitle);
                titleView.setText(title);

                ImageView imageView = (ImageView) findViewById(R.id.articleExpandImage);
                new DownloadImageTask(imageView).execute(image);

                TextView preView = (TextView) findViewById(R.id.articleExpandPreview);
                preView.setText(preview);

                TextView themeView = (TextView) findViewById(R.id.articleExpandTheme);
                themeView.setText(theme);

                TextView likesView = (TextView) findViewById(R.id.articleExpandedLikesNumber);
                likesView.setText(String.valueOf(likes));

                TextView dateTimeView = (TextView) findViewById(R.id.articleExpandedDateTime);
                dateTimeView.setText(dateTime);

                //endregion

                //region Tags Cloud
                final RecyclerView tagsCloudView = (RecyclerView) findViewById(R.id.articleExpandTagsCloud);
                RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
                tagsCloudView.setItemAnimator(itemAnimator);

                ArrayList<String> tags = new ArrayList<String>();
                if (tagsCloud != null) {
                    Collections.addAll(tags, ViewsListItem.tagsCloudToTags( tagsCloud ));
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
                //endregion

                //region Text
                HTML5WebView webview = (HTML5WebView) this.findViewById(R.id.articleExpandedWebView);//todo return webview

                WebSettings webSettings = webview.getSettings();
                webSettings.setDefaultFontSize(48);
                webSettings.setTextZoom(90);

                webview.getSettings().setUseWideViewPort(true);
                webview.getSettings().setLoadWithOverviewMode(true);

                //region Ex
                //EXAMPLE
                String str = "      <html>\n" +
                        "      <head>\n" +
                        "         <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\n" +
                        "      <title>\n" +
                        "      A Simple HTML Document\n" +
                        "      </title>\n" +
                        "      </head>\n" +
                        "      <body>\n" +
                        "      <p>This is a very simple HTML document</p><tr/>\n" +
                        "      <p>Первый пример</p><br/>\n" +
                        "          <img src=\"https://pp.userapi.com/c837239/v837239772/32bd9/IxjdAyHzJBg.jpg\"></img>\n" +
                        "          <img src=\"D:\\proj\\RobberNews\\app\\src\\main\\res\\drawable\\image_stylished_chose2.jpg\"></img>\n" +
                        "          <video>\n" +
                        "            <source src=\"https://cloud.mail.ru/public/LdKx/GDZjx4rYt\" type='video/mp4; codecs=\"avc1.42E01E, mp4a.40.2\"' />\n" +
                        "          </video>\n" +
                        "          <video tabindex=\"-1\" class=\"video-stream html5-main-video\" controlslist=\"nodownload\" style=\"width: 640px; height: 360px; left: 0px; top: 0px; opacity: 1;\" src=\"blob:https://www.youtube.com/53f0f0b3-eee6-458b-9ede-9b8021671df1\"></video>\n" +
                        "          <iframe width=\"420\" height=\"315\"\n" +
                        "              src=\"https://www.youtube.com/watch?v=78r-DFnZo0M\">\n" +
                        "          </iframe>\n" +
                        "      <p>It only has two paragraphs</p>\n" +
                        "      </body>\n" +
                        "      </html>";

                String script = "<script type=\"text/javascript\" src=\"Mobilize.js\"></script>";
                //endregion

                str = str + script;

                webview.getSettings().setJavaScriptEnabled(true);
                webview.getSettings().setPluginState(WebSettings.PluginState.ON);
                webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                webview.getSettings().setSupportMultipleWindows(true);
                webview.getSettings().setSupportZoom(true);
                webview.getSettings().setBuiltInZoomControls(true);
                webview.getSettings().setAllowFileAccess(true);
                webview.setWebChromeClient(new WebChromeClient());
                webview.setWebViewClient(new WebViewClient());
                webview.loadDataWithBaseURL(null, (text + script), "text/html; charset=UTF-8", "UTF-8", null);//str text
                //endregion


            } while (cursor.moveToNext());
        }
        cursor.close();

        TextView authorView = (TextView) findViewById(R.id.articleExpandedAuthor);
        authorView.setText("Author not exists#" + authorId);
        try {
            Cursor cursor2 = getContentResolver().query(RobberNewsContentProvider.PROVIDER_USER,
                    RobberNewsContentProvider.PROJECTION_USER,
                    RobberNewsContentProvider.COLUMN_ID + "= ? ",
                    new String[]{String.valueOf(authorId)},
                    RobberNewsContentProvider.COLUMN_ID);
            if (cursor.getCount() >= 1) {
                cursor2.moveToFirst();
                Integer _uid = cursor.getInt(cursor.getColumnIndex(RobberNewsContentProvider.COLUMN_ID));
                String username = cursor.getString(cursor.getColumnIndex(RobberNewsContentProvider.COLUMN_NAME));
                String usersurname = cursor.getString(cursor.getColumnIndex(RobberNewsContentProvider.COLUMN_SURNAME));
                authorView.setText(username + " " + usersurname + "#" + _uid);
            }
//                    if () {
//                        do {
//
//                        } while (cursor2.moveToNext());
//                    }
            cursor2.close();
        } catch (Exception ex){
            Log.d("Author not exists","Author not exists#" + authorId);
            ex.printStackTrace();
        }

        //data == html data which you want to load



//
//        webview.setText(getString(R.string.html_example));



//        webview.getSettings().setJavaScriptEnabled(true);

//        webview.loadDataWithBaseURL(null, getString(R.string.html_example), "text/html; charset=UTF-8", "UTF-8", null);
//        webview.loadData(getString(R.string.html_example), "text/html; charset=UTF-8", "UTF-8");

    }

}
