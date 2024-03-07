package com.example.foobook_android.DB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.foobook_android.Dao.PostDao;
import com.example.foobook_android.Dao.UserDao;
import com.example.foobook_android.post.Post;

@Database(entities = {Post.class}, version = 1)

public abstract class AppDB extends RoomDatabase {
    public abstract PostDao postDao();
    public abstract UserDao userDao();
}
