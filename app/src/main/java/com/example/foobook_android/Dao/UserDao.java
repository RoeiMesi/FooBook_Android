package com.example.foobook_android.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.foobook_android.User;
import com.example.foobook_android.post.Post;

import java.util.List;
@Dao
public interface UserDao {
    @Query("SELECT * FROM post")
    List<User> index();

    @Query("SELECT * FROM post WHERE id = :id")
    User get(int id);

    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User... users);
}
