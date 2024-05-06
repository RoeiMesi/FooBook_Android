package com.example.foobook_android.ViewModels;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.foobook_android.Api.PostsResponse;
import com.example.foobook_android.Api.ToggleLikeResponse;
import com.example.foobook_android.Repositories.UserRepository;
import com.example.foobook_android.post.Post;
import com.example.foobook_android.Repositories.PostRepository;
import com.example.foobook_android.utility.TokenManager;
import com.example.foobook_android.utility.UserDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// View model for managing post-related data.
public class PostViewModel extends AndroidViewModel {
    // Repository for handling post-related operations
    private final PostRepository repository;

    // Mutable live data objects for observing data changes
    private final LiveData<List<Post>> latestPosts;
    private final MutableLiveData<String> displayNameLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> profilePicLiveData = new MutableLiveData<>();
    private final MutableLiveData<PostsResponse> postsLiveData = new MutableLiveData<>();
    private final MutableLiveData<ToggleLikeResponse> toggleLikeResponse = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>(); // For error message
    private TokenManager tokenManager; // Field to hold the TokenManager instance



    public PostViewModel(@NonNull Application application) {
        super(application);
        repository = new PostRepository(application);
        tokenManager = new TokenManager(application); // Initialize the TokenManager
        latestPosts = repository.getLatestPosts();
    }
    // Getter for observing latest posts
    public LiveData<List<Post>> getLatestPosts() {
        return latestPosts;
    }

    // Getter for observing errors
    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    // Getter for observing toggle like response
    public MutableLiveData<ToggleLikeResponse> getToggleLikeResponse() {
        return toggleLikeResponse;
    }

    // Getter for observing posts response
    public LiveData<PostsResponse> getPostsLiveData() {
        return postsLiveData;
    }

    // Fetches posts from the server
    public void fetchPostsFromServer(Context context) {
        repository.fetchAndProcessPosts(context);
    }

    // Toggles like for a post
    public void toggleLike(String userId, String postId) {
        repository.toggleLike(userId, postId, new Callback<ToggleLikeResponse>() {
            @Override
            public void onResponse(@NonNull Call<ToggleLikeResponse> call, @NonNull Response<ToggleLikeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Update LiveData upon successful toggle
                    toggleLikeResponse.setValue(response.body());
                } else {
                    // Handle error scenario, update error LiveData
                    errorLiveData.setValue("Failed to toggle like. Please try again.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ToggleLikeResponse> call, @NonNull Throwable t) {
                // Handle failure of the call, such as network errors, update error LiveData
                errorLiveData.setValue("Network error occurred. Please check your connection and try again.");
            }
        });
    }

    // Inserts a new post
    public void insert(Post post) {
        repository.insert(post);
    }

    // Deletes a post
    public void delete(Post post) {
        repository.delete(post);
    }

    // Deletes a post by its ID
    public void deleteByPostId(String postId) {
        repository.deleteByPostId(postId);
    }

    // Fetches posts by user ID
    public void fetchPostsByUserId(String userId, String authToken) {
        repository.getPostsByUserId(userId, authToken, new Callback<PostsResponse>() {
            @Override
            public void onResponse(@NonNull Call<PostsResponse> call, @NonNull Response<PostsResponse> response) {
                if (response.isSuccessful()) {
                    postsLiveData.postValue(response.body());
                } else {
                    // Handle API error response
                    postsLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<PostsResponse> call, @NonNull Throwable t) {
                // Handle call failure
                postsLiveData.postValue(null);
            }
        });
    }

    // Getter for observing display name
    public LiveData<String> getDisplayNameLiveData() {
        return displayNameLiveData;
    }

    // Getter for observing profile picture
    public LiveData<String> getProfilePicLiveData() {
        return profilePicLiveData;
    }

    // Fetches display name and profile picture for a user
    public void fetchDisplayName(Context context, String sentUserId) {
        UserRepository userRepository = new UserRepository(context);
        userRepository.fetchUserDetails(sentUserId, new UserRepository.UserDetailsCallback() {
            @Override
            public void onSuccess(UserDetails userDetails) {
                displayNameLiveData.postValue(userDetails.getDisplayName());
                tokenManager.setDisplayName(userDetails.getDisplayName());
                profilePicLiveData.postValue(userDetails.getProfilePic());
                tokenManager.setProfilePic(userDetails.getProfilePic());
            }

            @Override
            public void onError(Throwable throwable) {
                displayNameLiveData.postValue(null);
                profilePicLiveData.postValue(null);
            }
        });
    }

    // Creates a post for a user
    public void createPostForUser(String userId, Post post, Context context) {
        repository.createPostForUser(userId, post, context, new Callback<Post>() {
            @Override
            public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                if (response.isSuccessful()) {
                    Log.d("PostViewModel", "Post created successfully");
                    Post newPost = response.body();

                    if (newPost != null) {
                        if (newPost.getImageUrl() != null) {
                            newPost.setIsPhotoPicked(Post.PHOTO_PICKED);
                        }
                        repository.insert(newPost);

                        // Show success message with Toast
                        Toast.makeText(context, "Post created successfully!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    // Handle non-200 status codes
                    String errorMessage = "Post update failed";

                    if (response.errorBody() != null) {
                        try {
                            // Parse the JSON error body
                            String errorJson = response.errorBody().string();
                            JSONObject errorObj = new JSONObject(errorJson);

                            // Extract only the "message" field
                            errorMessage = errorObj.optString("message", "An error occurred");
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    // Display the error message via Toast without "{}" or "message:"
                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
                    Log.e("PostViewModel", "Post creation failed: " + errorMessage);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {
                // Handle failure to make the request (e.g., no internet connection)
                Log.e("PostViewModel", "Error updating post", t);
            }
        });
    }

    // Updates a post
    public void updatePost(String userId, String postId, Post updatedPost, Context context) {
        repository.updatePostForUser(userId, postId, updatedPost, context, new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    Log.d("PostViewModel", "Post updated successfully");
                    Post updatedPost = response.body();

                    if (updatedPost != null) {
                        repository.update(updatedPost);
                    }
                    // Show success message with Toast
                    Toast.makeText(context, "Post updated successfully!", Toast.LENGTH_LONG).show();
                } else {
                    // Handle non-200 status codes
                    String errorMessage = "Post update failed";

                    if (response.errorBody() != null) {
                        try {
                            // Parse the JSON error body
                            String errorJson = response.errorBody().string();
                            JSONObject errorObj = new JSONObject(errorJson);

                            // Extract only the "message" field
                            errorMessage = errorObj.optString("message", "An error occurred");
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    // Display the error message via Toast without "{}" or "message:"
                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
                    Log.e("PostViewModel", "Post update failed: " + errorMessage);
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                // Handle request failure (e.g., network error)
                String errorMessage = "Failed to connect to server: " + t.getMessage();
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
                Log.e("PostViewModel", errorMessage, t);
            }
        });

    }

    public void deletePostByUser(String userId, String postId, Context context) {
        repository.deletePostByUser(userId, postId, context);
    }

    public LiveData<Post> getPostById(String postId) {
        return repository.getPostById(postId);
    }
}