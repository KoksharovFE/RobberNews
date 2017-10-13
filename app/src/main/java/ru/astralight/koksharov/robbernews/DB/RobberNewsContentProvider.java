package ru.astralight.koksharov.robbernews.DB;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;

import static android.database.sqlite.SQLiteDatabase.openDatabase;

/**
 * Created by Koksharov on 30.08.2017.
 */

public class RobberNewsContentProvider extends ContentProvider {

    public static final String DATABASE_NAME = "RobberNews";

    public static final String AUTHORITY = "ru.astralight.koksharov.robbernews.DB.RobberNewsContentProvider";

    //region Access Mode
    public static String ACCESS_MODE_ADMIN = "admin";
    public static String ACCESS_MODE_MODERATOR = "moderator";
    public static String[] ACCESS_MODES_USER = {"common user", "graduated user", "master user"};

    //endregion

    //region Themes
    public static final String THEME_DOTA = "dota2";
    public static final String THEME_HEARTHSTONE = "hearthstone";
    public static final String THEME_GWENT = "gwent";
    public static final String THEME_ARTIFACT = "artifact";
    public static String[] THEMES = {THEME_ARTIFACT, THEME_HEARTHSTONE, THEME_GWENT, THEME_DOTA};
    //endregion

    //region Tables
    public final static String[] SPINNER_DATA = {RobberNewsContentProvider.TABLE_USER,
            RobberNewsContentProvider.TABLE_ARTICLE,
            RobberNewsContentProvider.TABLE_ARTICLE_COMMENT,
//            RobberNewsContentProvider.TABLE_FORUM_ARTICLE,
//            RobberNewsContentProvider.TABLE_FORUM_ARTICLE_COMMENT
    };
    //endregion

    //region Table
    public static final String TABLE_USER = "user";
    public static final String TABLE_ARTICLE = "article";
    public static final String TABLE_ARTICLE_COMMENT = "articlecomment";
//    public static final String TABLE_FORUM_ARTICLE = "forumarticle";
//    public static final String TABLE_FORUM_ARTICLE_COMMENT = "forumarticlecomment";
    public static final String TABLE_IMG = "img";
    //endregion

    //region Provider tags
    public final static Uri PROVIDER_USER = Uri.parse("content://" + AUTHORITY + "/" + TABLE_USER);
    public final static Uri PROVIDER_ARTICLE = Uri.parse("content://" + AUTHORITY + "/" + TABLE_ARTICLE);
    public final static Uri PROVIDER_ARTICLE_COMMENT = Uri.parse("content://" + AUTHORITY + "/" + TABLE_ARTICLE_COMMENT);
//    public final static Uri PROVIDER_FORUM_ARTICLE = Uri.parse("content://" + AUTHORITY + "/" + TABLE_FORUM_ARTICLE);
//    public final static Uri PROVIDER_FORUM_ARTICLE_COMMENT = Uri.parse("content://" + AUTHORITY + "/" + TABLE_FORUM_ARTICLE_COMMENT);
    public final static Uri PROVIDER_IMG = Uri.parse("content://" + AUTHORITY + "/" + TABLE_IMG);
    //endregion

    //region Projection && Columns
    public static final String COLUMN_ID = "_id";

    public final static String COLUMN_NAME = "name";
    public final static String COLUMN_SURNAME = "surname";
    public final static String COLUMN_PASSWORD = "password";
    public final static String COLUMN_ACCESS_MODE = "accees_mode";
    public final static String COLUMN_REGISTER_DATE = "date";
    public final static String COLUMN_AVATAR = "avatar";
    public final static String COLUMN_ABOUT = "about";

    public final static String[] PROJECTION_USER = new String[]{
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_SURNAME,
            COLUMN_PASSWORD,
            COLUMN_ACCESS_MODE,
            COLUMN_REGISTER_DATE,
            COLUMN_AVATAR,
            COLUMN_ABOUT
    };

    public final static String COLUMN_IMAGE = "img";

    public final static String COLUMN_TITLE = "title";
    public final static String COLUMN_PREVIEW = "preview";
    public final static String COLUMN_TEXT = "text";
    public final static String COLUMN_TAGS_CLOUD = "tags";
    public final static String COLUMN_LIKES_NUMBER = "likes_number";
    public final static String COLUMN_THEME = "theme";
    public final static String COLUMN_IS_FORUM_ARTICLE = "is_forum_article";
    public final static String COLUMN_AUTHOR_ID = "author_id";
    public final static String COLUMN_DATE_TIME = "date_time";

    public final static String[] PROJECTION_ARTICLE = new String[]{
            COLUMN_ID,
            COLUMN_IMAGE,
            COLUMN_TITLE,
            COLUMN_TAGS_CLOUD,
            COLUMN_PREVIEW,
            COLUMN_TEXT,
            COLUMN_THEME,
            COLUMN_LIKES_NUMBER,
            COLUMN_IS_FORUM_ARTICLE,
            COLUMN_AUTHOR_ID,
            COLUMN_DATE_TIME
    };

    public final static String COLUMN_ARTICLE_ID = "article_id";

    public final static String[] PROJECTION_ARTICLE_COMMENT = new String[]{
            COLUMN_ID,
            COLUMN_TEXT,
            COLUMN_AUTHOR_ID,
            COLUMN_ARTICLE_ID,
            COLUMN_LIKES_NUMBER,
            COLUMN_DATE_TIME
    };

//    public final static String[] PROJECTION_FORUM_ARTICLE = new String[]{
//            COLUMN_ID,
//            COLUMN_IMAGE,
//            COLUMN_TITLE,
//            COLUMN_TAGS_CLOUD,
//            COLUMN_PREVIEW,
//            COLUMN_TEXT,
//            COLUMN_THEME,
//            COLUMN_LIKES_NUMBER,
//            COLUMN_AUTHOR_ID,
//            COLUMN_DATE_TIME
//    };
//
//    public final static String COLUMN_FORUM_ARTICLE_ID = "forum_article_id";
//
//    public final static String[] PROJECTION_FORUM_ARTICLE_COMMENT = new String[]{
//            COLUMN_ID,
//            COLUMN_TEXT,
//            COLUMN_AUTHOR_ID,
//            COLUMN_FORUM_ARTICLE_ID,
//            COLUMN_DATE_TIME
//    };

    public final static String BLOB_IMAGE = "img";

    public final static String[] PROJECTION_IMG = new String[]{
            COLUMN_ID,
            BLOB_IMAGE,
    };
    //endregion

    //region URI Matcher
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int URI_TABLE_USER = 1;
    private static final int URI_TABLE_ARTICLE = 2;
    private static final int URI_TABLE_ARTICLE_COMMENT = 3;
//    private static final int URI_TABLE_FORUM_ARTICLE = 4;
//    private static final int URI_TABLE_FORUM_ARICLE_COMMENT = 5;
    private static final int URI_TABLE_IMG = 6;
    //endregion

    static {
        sURIMatcher.addURI(AUTHORITY, TABLE_USER, URI_TABLE_USER);
        sURIMatcher.addURI(AUTHORITY, TABLE_ARTICLE, URI_TABLE_ARTICLE);
        sURIMatcher.addURI(AUTHORITY, TABLE_ARTICLE_COMMENT, URI_TABLE_ARTICLE_COMMENT);
//        sURIMatcher.addURI(AUTHORITY, TABLE_FORUM_ARTICLE, URI_TABLE_FORUM_ARTICLE);
//        sURIMatcher.addURI(AUTHORITY, TABLE_FORUM_ARTICLE_COMMENT, URI_TABLE_FORUM_ARICLE_COMMENT);
        sURIMatcher.addURI(AUTHORITY, TABLE_IMG, URI_TABLE_IMG);
    }

    private SQLiteDatabase mDatabase;
    public RobberNewsContentProvider() {
    }

    //Запись в файл лога базы
    static void writeInFile(String sText, boolean bNew) {
        try {
            File file = new File(Environment.getExternalStorageDirectory().getPath() + "RobberNews.log");
            OutputStreamWriter writeFile = new FileWriter(file, bNew);
            writeFile.write(sText + "\n");
            writeFile.close();

        } catch (Exception ex) {
            System.out.println("Failed write in file. Reason: " + ex.getStackTrace());
            ex.printStackTrace();
        }
    }
    //endregion

    private String getTable(Uri uri) {
        String table = null;
        switch (sURIMatcher.match(uri)) {
            case URI_TABLE_USER:
                Log.d(((Integer) URI_TABLE_USER).toString(), TABLE_USER);
                table = TABLE_USER;
                break;
            case URI_TABLE_ARTICLE:
                Log.d(((Integer) URI_TABLE_ARTICLE).toString(), TABLE_ARTICLE);
                table = TABLE_ARTICLE;
                break;
            case URI_TABLE_ARTICLE_COMMENT:
                Log.d(((Integer) URI_TABLE_ARTICLE_COMMENT).toString(), TABLE_ARTICLE_COMMENT);
                table = TABLE_ARTICLE_COMMENT;
                break;
//            case URI_TABLE_FORUM_ARTICLE:
//                Log.d(((Integer) URI_TABLE_FORUM_ARTICLE).toString(), TABLE_FORUM_ARTICLE);
//                table = TABLE_FORUM_ARTICLE;
//                break;
//            case URI_TABLE_FORUM_ARICLE_COMMENT:
//                Log.d(((Integer) URI_TABLE_FORUM_ARICLE_COMMENT).toString(), TABLE_FORUM_ARTICLE_COMMENT);
//                table = TABLE_FORUM_ARTICLE_COMMENT;
//                break;
            case URI_TABLE_IMG:
                Log.d(((Integer) URI_TABLE_IMG).toString(), TABLE_IMG);
                table = TABLE_IMG;
                break;
            default:
                Log.d(((Integer) sURIMatcher.match(uri)).toString(), "not found");
                break;
        }
        return table;
    }

    @Override
    public boolean onCreate() {
        DatabaseHelper dbHelper = new DatabaseHelper(this.getContext());
        mDatabase = dbHelper.getWritableDatabase();
        return true;
    }

    /**
     * selectionArgs replace any question marks in the selection string.
     *
     * for example:
     *
     * String[] args = { "first string", "second@string.com" };
     * Cursor cursor = db.query("TABLE_NAME", null, "name=? AND email=?", args, null);
     * as for your question - you can use null
     *
     * */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d("reading from database: ", getTable(uri) + "");
        return mDatabase.query(getTable(uri), projection, selection, selectionArgs, null, null, sortOrder);

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return uri.toString();
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d("insert in database: ", getTable(uri) + "");
        Long id = mDatabase.insert(getTable(uri), null, values);
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d("delete in database: ", getTable(uri) + "");
        return mDatabase.delete(getTable(uri), selection, selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String whereClause, String[] whereArgs) {
        Log.d("update in database: ", getTable(uri) + "");
        return mDatabase.update(getTable(uri), values, whereClause, whereArgs);
    }

    //region SQLite DB
    private static class DatabaseHelper extends SQLiteOpenHelper {

        private static final int VERSION = 12;
        public static SQLiteDatabase mDb;

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            if (db == null){
//                openDatabase(DATABASE_NAME + ".db", null, Context.MODE_PRIVATE);
                db = this.getWritableDatabase();
            }

            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + TABLE_USER + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY autoincrement, "
                    + COLUMN_NAME + " VARCHAR, "
                    + COLUMN_SURNAME + " VARCHAR, "
                    + COLUMN_PASSWORD + " VARCHAR, "
                    + COLUMN_ACCESS_MODE + " VARCHAR, "
                    + COLUMN_AVATAR + " VARCHAR, "
                    + COLUMN_REGISTER_DATE + " VARCHAR, "
                    + COLUMN_ABOUT + " VARCHAR"
                    + ");");

            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + TABLE_ARTICLE + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY autoincrement, "
                    + COLUMN_IMAGE + " VARCHAR, "
                    + COLUMN_PREVIEW + " VARCHAR, "
                    + COLUMN_TITLE + " VARCHAR, "
                    + COLUMN_TEXT + " VARCHAR, "
                    + COLUMN_THEME + " VARCHAR, "
                    + COLUMN_IS_FORUM_ARTICLE + " INTEGER, "
                    + COLUMN_TAGS_CLOUD + " VARCHAR, "
                    + COLUMN_LIKES_NUMBER + " INTEGER, "
                    + COLUMN_AUTHOR_ID + " INTEGER, "
                    + COLUMN_DATE_TIME + " VARCHAR"
                    + ");");

            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + TABLE_ARTICLE_COMMENT + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY autoincrement, "
                    + COLUMN_TEXT + " VARCHAR,"
                    + COLUMN_LIKES_NUMBER + " INTEGER, "
                    + COLUMN_AUTHOR_ID + " INTEGER, "
                    + COLUMN_ARTICLE_ID + " INTEGER,"
                    + COLUMN_DATE_TIME + " VARCHAR "
                    + ");");
//
//            db.execSQL("CREATE TABLE IF NOT EXISTS "
//                    + TABLE_FORUM_ARTICLE + " ("
//                    + COLUMN_ID + " INTEGER PRIMARY KEY autoincrement, "
//                    + COLUMN_IMAGE + " VARCHAR, "
//                    + COLUMN_PREVIEW + " VARCHAR, "
//                    + COLUMN_TITLE + " VARCHAR, "
//                    + COLUMN_TEXT + " VARCHAR, "
//                    + COLUMN_THEME + " VARCHAR, "
//                    + COLUMN_TAGS_CLOUD + " VARCHAR, "
//                    + COLUMN_LIKES_NUMBER + " VARCHAR, "
//                    + COLUMN_AUTHOR_ID + " INTEGER, "
//                    + COLUMN_DATE_TIME + " VARCHAR"
//                    + ");");

//            db.execSQL("CREATE TABLE IF NOT EXISTS "
//                    + TABLE_FORUM_ARTICLE_COMMENT + " ("
//                    + COLUMN_ID + " INTEGER PRIMARY KEY autoincrement, "
//                    + COLUMN_TEXT + " VARCHAR,"
//                    + COLUMN_AUTHOR_ID + " INTEGER, "
//                    + COLUMN_FORUM_ARTICLE_ID + " VARCHAR,"
//                    + COLUMN_DATE_TIME + " VARCHAR"
//                    + ");");

            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + TABLE_IMG + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY autoincrement, "
                    + BLOB_IMAGE + " BLOB"
                    + ");");

            mDb = db;
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO: backup and recover
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLE_COMMENT);
//            db.execSQL("DROP TABLE IF EXISTS " + TABLE_FORUM_ARTICLE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMG);
            onCreate(db);
        }

        public void onDrop(SQLiteDatabase db) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLE_COMMENT);
//            db.execSQL("DROP TABLE IF EXISTS " + TABLE_FORUM_ARTICLE);
//            db.execSQL("DROP TABLE IF EXISTS " + TABLE_FORUM_ARTICLE_COMMENT);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMG);
            onCreate(db);
        }
    }

    //endregion
}
