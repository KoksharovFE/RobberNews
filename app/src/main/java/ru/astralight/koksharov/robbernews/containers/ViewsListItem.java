package ru.astralight.koksharov.robbernews.containers;

/**
 * Created by Koksharov on 09.10.2017.
 */

public class ViewsListItem {
    Integer _id;
    String title;
    String preview;
    String imgSrc;
    String[] tags;



    public ViewsListItem(Integer pId, String pTitle, String pPreview, String pImgSrc, String tagsCloud){
        _id = pId;
        title = pTitle;
        preview = pPreview;
        imgSrc = pImgSrc;
        if (tagsCloud != null ){
            tags = tagsCloud.replace(";","").split(" ");
            for (int i = 0; i < tags.length; i++) {
                tags[i] = "#" + tags[i];
            }
        }
    }

}
