package ru.astralight.koksharov.robbernews.containers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import ru.astralight.koksharov.robbernews.DB.RobberNewsContentProvider;
import ru.astralight.koksharov.robbernews.R;
import ru.astralight.koksharov.robbernews.tasks.DownloadImageTask;
import ru.astralight.koksharov.robbernews.views.ArticleExpanded;
import ru.astralight.koksharov.robbernews.views.Views;

/**
 * Created by Koksharov on 05.10.2017. RobberNews
 */
public class ViewsArrayAdapter extends ArrayAdapter<ViewsListItem> {
    private final Activity context;
    public ArrayList<ViewsListItem> articlesList = new ArrayList<>();

    public ViewsArrayAdapter(Activity context, ArrayList<ViewsListItem> articles) {
        super(context, R.layout.views_list_item_layout, articles );
        this.context = context;
        this.articlesList = articles;
    }

    //todo update articles method

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.views_list_item_layout, parent, false);

        TextView titleView = (TextView) rowView.findViewById(R.id.vListItemTitle);
        TextView previewView = (TextView) rowView.findViewById(R.id.vListItemPreview);
        TextView likesView = (TextView) rowView.findViewById(R.id.vListItemLikesNumber);
//        ListView tagsCloudView = rowView.findViewById(R.id.vListItemTagsCloudListView);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.vListItemTitleImageView);
        Button detail = (Button) rowView.findViewById(R.id.vListItemDetailButton);

        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(rowView.getContext(), ArticleExpanded.class);
                intent.putExtra(RobberNewsContentProvider.COLUMN_ID, articlesList.get(position).get_id());
                ((Activity) rowView.getContext()).startActivityForResult(intent, 1);
            }
        });

        titleView.setText(articlesList.get(position).getTitle());
        previewView.setText(articlesList.get(position).getPreview());
        likesView.setText(articlesList.get(position).getLikesNumber()+"");

        CardView cardView = rowView.findViewById(R.id.vListItemCardView);

        cardView.setCardBackgroundColor(Views.argbColor);
        new DownloadImageTask(imageView).execute(articlesList.get(position).getImgSrc());



        final RecyclerView tagsCloudView = rowView.findViewById(R.id.vListItemTagsCloudRecyclerView);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        tagsCloudView.setItemAnimator(itemAnimator);

        ArrayList<String> tags = new ArrayList<String>();
        if (articlesList.get(position).getTags() != null) {
            Collections.addAll(tags, articlesList.get(position).getTags());
        }
        tagsCloudView.setAdapter(
                new ViewsListItemTagsAdapter(tags));
        tagsCloudView.setLayoutManager(new GridLayoutManager(rowView.getContext(), 7));
        tagsCloudView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = tagsCloudView.indexOfChild(v);

                Log.i("Tags item position is ",String.valueOf(itemPosition));

            }
        });
//        cardView.setBackground(parent.getBackground());

//        titleView.setBackground(parent.getBackground());
//        previewView.setBackground(parent.getBackground());
//        tagsCloudView.setBackground(parent.getBackground());
//        imageView.setBackground(parent.getBackground());


        // show The Image in a ImageView
        //https://goo.gl/ppQbgZ
        //http://java.sogeti.nl/JavaBlog/wp-content/uploads/2009/04/android_icon_256.png

        try {
            ArrayAdapter<String> adapterTags = new ArrayAdapter<String>(
                    getContext(),
                    android.R.layout.simple_list_item_1,
                    R.id.vListItemTagsCloudRecyclerView,
                    articlesList.get(position).getTags()
            );

        } catch (Exception ex){
            ex.printStackTrace();
        }

        return rowView;
    }

    @Override
    public int getCount() {
        return articlesList.size();
    }

    @Override
    public ViewsListItem getItem(int position) {
        return articlesList.get(position);
    }

}