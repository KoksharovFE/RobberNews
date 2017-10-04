package ru.astralight.koksharov.robbernews.DB;

import java.util.HashMap;

/**
 * Created by Koksharov on 12.09.2017.
 */

public interface ActualData {
    void init();
    HashMap<String, String> getUser();
    HashMap<String, String> getArticle();
    HashMap<String, String> getArticleComment();
    boolean setArticleComment(HashMap<String, String> comment);
    HashMap<String, String> getForumArticle();
    boolean setForumArticleComment(HashMap<String, String> comment);
}
