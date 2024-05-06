package com.example.foobook_android.activities;

import static com.example.foobook_android.activities.CreatePostActivity.QUALITY;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.foobook_android.ViewModels.PostViewModel;
import com.example.foobook_android.utility.ImageUtility;
import com.example.foobook_android.utility.PhotoSelectorHelper;
import com.example.foobook_android.post.Post;
import com.example.foobook_android.R;
import com.example.foobook_android.utility.TokenManager;

/**
 * Activity to edit an existing post. It allows users to update the text content of the post
 * and change the associated image either by taking a new photo with the camera or selecting one from the gallery.
 */
public class EditPostActivity extends AppCompatActivity {
    // Request codes for camera and gallery intents
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int GALLERY_REQUEST_CODE = 101;

    // ViewModel for observing and updating post data
    private PostViewModel postViewModel;
    // UI components
    private EditText postEditText;
    private ImageView selectedImage;
    private Button postButton, removePhoto;
    private ImageButton btnGallery, btnCamera;
    // URI of the selected image for the post
    private String postImageBase64;
    // Flag to track if a photo has been selected
    private boolean isPhotoSelected = false;
    // The post being edited
    private Post currentPost;
    // Helper class for selecting photos from gallery or camera
    private PhotoSelectorHelper photoSelectorHelper;
    private TokenManager tokenManager; // Field to hold the TokenManager instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        // Initialize local database instance
        // Local database instance
        Log.i("EditPostActivity", "onCreate");

        tokenManager = new TokenManager(this); // Initialize the TokenManager

        // Initialize UI components
        initializeViewComponents();

        // Initialize PostViewModel
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);

        // Set up UI listeners
        setupListeners();

        // Initialize helper classes
        initializeHelpers();

        // Handle any incoming data from intents (e.g., selected post ID)
        handleIncomingIntent();
    }

    /**
     * Initializes UI components by binding them to their respective views in the layout.
     */
    private void initializeViewComponents() {
        postEditText = findViewById(R.id.postEditText);
        selectedImage = findViewById(R.id.selectedImage);
        postButton = findViewById(R.id.postButton);
        btnGallery = findViewById(R.id.btnGallery);
        btnCamera = findViewById(R.id.btnCamera);
        removePhoto = findViewById(R.id.removePhoto);
    }

    /**
     * Sets up click listeners for the UI components that require interaction.
     */
    private void setupListeners() {
        btnGallery.setOnClickListener(v -> photoSelectorHelper.openGallery());
        btnCamera.setOnClickListener(v -> photoSelectorHelper.checkCameraPermissionAndOpen());
        removePhoto.setOnClickListener(v -> clearSelectedPhoto());
        postButton.setOnClickListener(v -> savePost());
    }

    /**
     * Initializes helper classes used within the activity.
     */
    private void initializeHelpers() {
        photoSelectorHelper = new PhotoSelectorHelper(this, CAMERA_REQUEST_CODE,
                GALLERY_REQUEST_CODE, this::setImage);
    }

    /**
     * Sets the selected image on the ImageView and updates the visibility of the image and remove photo button.
     * @param bitmap The bitmap of the selected image.
     */
    private void setImage(Bitmap bitmap) {
        postImageBase64 = ImageUtility.bitmapToBase64(ImageUtility.compressBitmap(bitmap, QUALITY));
        selectedImage.setImageBitmap(bitmap);
        selectedImage.setVisibility(View.VISIBLE);
        removePhoto.setVisibility(View.VISIBLE);
        isPhotoSelected = true;
    }


    /**
     * Handles the incoming intent by checking for a passed post ID and retrieving the corresponding post.
     */
    private void handleIncomingIntent() {
        String postId = getIntent().getStringExtra("postId");
        if (postId != null) {
            postViewModel.getPostById(postId).observe(this, post -> {
                currentPost = post;
                if (post != null) {
                    populateUIWithPostDetails();
                }
            });
        }
    }

    /**
     * Populates the UI with the details of the current post.
     * It sets the text content and loads the image if available.
     */
    private void populateUIWithPostDetails() {
        // Check if currentPost is not null to populate UI with post details
        if (currentPost != null) {
            postEditText.setText(currentPost.getText()); // Set the post content

            // Check if there's an image URI available and load it
            if (currentPost.getImageUrl() != null && !currentPost.getImageUrl().isEmpty()) {
                isPhotoSelected = true;
                String postImage = currentPost.getImageUrl();
                if (ImageUtility.isImageUrl(postImage)) {
                    ImageUtility.loadImage(selectedImage, postImage, this);
                } else if (ImageUtility.isBase64(postImage)) {
                    Bitmap postImageBitmap = ImageUtility.base64ToBitmap(postImage);
                    selectedImage.setImageBitmap(postImageBitmap);
                }
                selectedImage.setVisibility(View.VISIBLE); // Make the ImageView visible
                removePhoto.setVisibility(View.VISIBLE); // Show the remove photo button
            }
        } else {
            // Hide the ImageView and remove photo button if no photo is set
            selectedImage.setVisibility(View.GONE); // If no photo was picked, ensure the ImageView is not visible
            removePhoto.setVisibility(View.GONE); // Hide the remove photo button if no photo is set
        }
    }

    /**
     * Clears the selected photo from the ImageView and updates UI visibility.
     */
    private void clearSelectedPhoto() {
        selectedImage.invalidate();
        selectedImage.setVisibility(View.GONE);
        isPhotoSelected = false;
        postImageBase64 = "";
    }

    /**
     * Saves the changes made to the post back to the database.
     * It updates the post's text content and image URL, and notifies the user of success.
     */
    private void savePost() {
        String postText = postEditText.getText().toString();
       // boolean isPhotoChanged = isPhotoSelected && postImageBase64 != null;



        // Proceed with update if there's new content or a new photo
        if (!postText.isEmpty() || isPhotoSelected) {
            currentPost.setContent(postText);
            currentPost.setImageUrl(postImageBase64);

            // Update the post using ViewModel
            postViewModel.updatePost(getCurrentUserId(), currentPost.getPostId(), currentPost, this);

            finish(); // Close the activity
        } else {
            // Prompt the user if the post content is empty
            Toast.makeText(EditPostActivity.this, "Post cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }

    private String getCurrentUserId() {
        return tokenManager.getUserId();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Delegate the result handling to the photoSelectorHelper
        photoSelectorHelper.handleActivityResult(requestCode, resultCode, data);
    }
}