package ru.astralight.koksharov.robbernews.containers;

/**
 * Created by Koksharov on 09.10.2017.
 */

public class ViewsListItem {
    private Integer _id;
    private String title;
    private String preview;
    private String imgSrc;
    private Integer likesNumber;
    private String[] tags;



    public ViewsListItem(Integer pId, String pTitle, String pPreview, String pImgSrc, String tagsCloud, Integer likesNumber){
        set_id(pId);
        setTitle(pTitle);
        setPreview(pPreview);
        setImgSrc(pImgSrc);
        setLikesNumber(likesNumber);
        if (tagsCloud != null ){
            setTags(tagsCloud.replace(";","").split(" "));
            for (int i = 0; i < getTags().length; i++) {
                getTags()[i] = "#" + getTags()[i];
            }
        }
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public Integer getLikesNumber() {
        return likesNumber;
    }

    public void setLikesNumber(Integer likes_number) {
        this.likesNumber = likes_number;
    }
}
