package ru.astralight.koksharov.robbernews.views;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ru.astralight.koksharov.robbernews.DB.RobberNewsContentProvider;
import ru.astralight.koksharov.robbernews.FullscreenActivit;
import ru.astralight.koksharov.robbernews.R;
import ru.astralight.koksharov.robbernews.containers.ViewsArrayAdapter;
import ru.astralight.koksharov.robbernews.containers.ViewsListItem;

public class Views extends AppCompatActivity {

    Views views;
    RelativeLayout layout;



    ListView listView;
    ImageView imageView;
    public static int argbColor = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_views);

        views = this;

        listView = (ListView) findViewById(R.id.viewsListView);//

        receiveDataList(null, null);


//        LayoutInflater inflater = (LayoutInflater) this
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View rowView = inflater.inflate(R.layout.activity_views, null, false);
//        ListView listView = (ListView) rowView.findViewById(R.id.viewsListView);//
//        ListView listView2 = (ListView) rowView.findViewById(R.id.viewsListView2);//




//        ViewsArrayAdapter adapter = new ViewsArrayAdapter(this, viewsListItems );
//        listView.setAdapter(adapter);

        try {
//            Button artifact = (Button) rowView.findViewById(R.id.viewsArtifact);
//            Button hearthstone = (Button) rowView.findViewById(R.id.viewsHearthstone);
//            Button gwent = (Button) rowView.findViewById(R.id.viewsGwent);
//            Button dota2 = (Button) rowView.findViewById(R.id.viewsDota2);
//            imageView = (ImageView) rowView.findViewById(R.id.viewsCurrentColor);

            Button artifact = (Button) findViewById(R.id.viewsArtifact);
            Button hearthstone = (Button) findViewById(R.id.viewsHearthstone);
            Button gwent = (Button) findViewById(R.id.viewsGwent);
            Button dota2 = (Button) findViewById(R.id.viewsDota2);
            imageView = (ImageView) findViewById(R.id.viewsCurrentColor);

            artifact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeTheme(RobberNewsContentProvider.THEME_ARTIFACT);
                    receiveDataList(RobberNewsContentProvider.THEME_ARTIFACT, null);
                }
            });
            hearthstone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeTheme(RobberNewsContentProvider.THEME_HEARTHSTONE);
                    receiveDataList(RobberNewsContentProvider.THEME_HEARTHSTONE, null);
                }
            });
            gwent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeTheme(RobberNewsContentProvider.THEME_GWENT);
                    receiveDataList(RobberNewsContentProvider.THEME_GWENT, null);
                }
            });
            dota2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeTheme(RobberNewsContentProvider.THEME_DOTA);
                    receiveDataList(RobberNewsContentProvider.THEME_DOTA, null);
                }
            });

        } catch (Exception ex){
            ex.printStackTrace();
            Log.i("ERR", ex.getLocalizedMessage());
            Log.d("ERR", ex.getLocalizedMessage());
        }

    }

    void receiveDataList(String theme, String tag){
        Cursor cursor = null;
        ArrayList<ViewsListItem> viewsListItems = new ArrayList<>();

        if (theme == null && tag == null) {
            cursor = getContentResolver().query(RobberNewsContentProvider.PROVIDER_ARTICLE,
                    RobberNewsContentProvider.PROJECTION_ARTICLE,
                    null, null, RobberNewsContentProvider.COLUMN_LIKES_NUMBER);//todo order - likes, filtering
        } else if (tag == null) {
            cursor = getContentResolver().query(RobberNewsContentProvider.PROVIDER_ARTICLE,
                    RobberNewsContentProvider.PROJECTION_ARTICLE,
                    RobberNewsContentProvider.COLUMN_THEME + "= ? ", new String[]{theme}, RobberNewsContentProvider.COLUMN_LIKES_NUMBER);//todo order - likes, filtering
        } else if (theme == null){
            cursor = getContentResolver().query(RobberNewsContentProvider.PROVIDER_ARTICLE,
                    RobberNewsContentProvider.PROJECTION_ARTICLE,
                    RobberNewsContentProvider.COLUMN_TAGS_CLOUD + "= ? ", new String[]{tag}, RobberNewsContentProvider.COLUMN_LIKES_NUMBER);//todo order - likes, filtering
        }

        viewsListItems.clear();
        if (cursor.moveToFirst()) {//todo theme filter, likes priority, data priority
            do {

                Integer _id = cursor.getInt(cursor.getColumnIndex(RobberNewsContentProvider.COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndex(RobberNewsContentProvider.COLUMN_TITLE));
                String preview = cursor.getString(cursor.getColumnIndex(RobberNewsContentProvider.COLUMN_PREVIEW));
                String image = cursor.getString(cursor.getColumnIndex(RobberNewsContentProvider.COLUMN_IMAGE));
                Integer likes = cursor.getInt(cursor.getColumnIndex(RobberNewsContentProvider.COLUMN_LIKES_NUMBER));
                String tagsCloud = cursor.getString(cursor.getColumnIndex(RobberNewsContentProvider.COLUMN_TAGS_CLOUD));
                ViewsListItem item = new ViewsListItem(_id, title, preview, image, tagsCloud, likes);
                viewsListItems.add(item);
            } while (cursor.moveToNext());
        }

        final ViewsArrayAdapter adapter = new ViewsArrayAdapter(this, viewsListItems );
        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Intent intent = new Intent(views, ArticleExpanded.class);
//                intent.putExtra(RobberNewsContentProvider.COLUMN_ID,
//                        ((ViewsArrayAdapter) listView.getAdapter()).articlesList.get(position).get_id());
//                startActivityForResult(intent, 1);
//
//            }
//        });
    }

    void changeTheme(String theme){

        TextView text = new TextView(this);
        text.setText(theme);
        Bitmap bitmap = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        switch(theme){
            case RobberNewsContentProvider.THEME_ARTIFACT:
                argbColor = Color.argb(55, 255, 255, 0);
                paint.setColor(argbColor);
//                listView.setBackgroundColor();
                break;
            case RobberNewsContentProvider.THEME_DOTA:
                argbColor = Color.argb(55, 255, 0, 0);
                paint.setColor(argbColor);
//                listView.setBackgroundColor();
                break;
            case RobberNewsContentProvider.THEME_GWENT:
                argbColor = Color.argb(55, 0, 255, 0);
                paint.setColor(argbColor);
//                listView.setBackgroundColor();
                break;
            case RobberNewsContentProvider.THEME_HEARTHSTONE:
                argbColor = Color.argb(55, 0, 0, 255);
                paint.setColor(argbColor);
//                listView.setBackgroundColor();
                break;
        }
        canvas.drawCircle(0, 0, 100, paint);
        imageView.setImageBitmap(bitmap);
    }
}