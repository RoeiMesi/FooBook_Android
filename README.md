<div align="center">
  <h1>FooBook_Android</h1>
</div>
<p align="center">
  <img src="https://raw.githubusercontent.com/RoeiMesi/FooBook_Android/main/app/src/main/res/drawable/foobook_logo.png" alt="foobook_logo">
</p>

## Table of Contents
- [Getting Started](#getting-started)
- [New Features and Improvements](#new-features-and-improvements)
- [How to use FooBook_Android Features](#how-to-use-foobook_android-features)
- [Development and Testing](#development-and-testing)
- [Photos](#photos)

Welcome to Part 3 of the FooBook_Android project. This phase brings critical updates to the app, integrating MongoDB for backend storage and Room for local database management. We've also incorporated JWT for secure authentication, enhanced UI components, and introduced a complete friend system, alongside other robust features.

## Getting Started
To get started with these new features, please follow the instructions below.

### Prerequisites
Ensure that you have the latest version of Android Studio.

To fully utilize the functionalities of FooBook_Android, it's essential to have the [`Foobook_Server`](https://github.com/TomerBeren/FooBook_Server)
operational, since this frontend application depends on its API endpoints for data access.

### Installation
1. **Clone the Repository:**
   **git clone https://github.com/RoeiMesi/FooBook_Android**
   
2. **Open Project: Launch Android Studio and open the cloned project.**

3. **Sync Gradle: Use the 'Sync Now' feature in Android Studio to sync your project with the updated dependencies.**


## New Features and Improvements

### User Authentication and Security
- Secure login and registration through JWT-based authentication.
- Backend endpoints protection requiring valid JWTs.

### Friend System
- A comprehensive system for managing friend requests, viewing, and interacting with friends within user profiles.

### UI and UX Enhancements
- Improved UI components for easier navigation and a visually appealing experience, including a dark mode.
- Enhanced personalization through photo selection in posts and profile settings.
- Smooth UI updates with consistent theming and data binding.

### Database Management
- MongoDB integration for efficient data handling and user information storage.
- Room database for local data persistence, ensuring offline functionality.
- Synchronization between local and remote databases to maintain data consistency.

### Error Handling and Data Compliance
- Advanced error handling mechanisms for a streamlined user experience.
- Compliance with privacy standards in data management.


## How to use FooBook_Android Features

This guide is designed to help you navigate and make full use of the FooBook_Android app's features. From creating and managing posts to handling friend requests, here's everything you need to know to enhance your user experience.

### Creating and Managing Posts

#### Creating a New Post
1. **Initiate Post Creation:** Tap the "+" icon at the top of the main screen to open the post creation interface.
2. **Adding Photos:**
   - **Camera:** Tap the small camera icon to open your phone's camera. Capture a photo to add it to your post.
   - **Gallery:** Tap the photo icon next to the camera icon to select a photo from your gallery.

#### Editing and Deleting Posts
- **Access Edit Options:** Click the pencil icon next to your post to see options for "Edit Post" and "Delete Post."
   - **Edit Post:** Select this option to modify the content of your post. You can change the text or the photos attached.
   - **Delete Post:** Choose this to remove your post permanently.

#### Interacting with Posts
- **Liking Posts:** You can like any post by tapping the like button, regardless of whether the poster is a friend.
- **Commenting:** Feel free to comment on any post to engage with other users.

### Profile Management

#### Accessing and Editing Your Profile
- **View Your Profile:** Select "My Profile" to see your posts and profile information.
- **Edit Profile Options:** Tap the popup button at the bottom right and choose "Edit Profile." Here, you can:
   - **Change Profile Picture:** Choose "Take Picture" to snap a new photo or "Choose File" to select one from your gallery. If updating your photo, re-enter your name if you do not wish to change it.
   - **Update Name:** You have the option to update your name directly in this section.

#### Logout and Account Deletion
- **Logout:** Select "Logout" from the popup menu to sign out of your account.
- **Delete User:** Choose "Delete User" to permanently remove your account and all related data.

### Friend System

#### Managing Friends and Friend Requests
- **Viewing Friend List:** Tap "Friend List" to see your friends. Here, you can delete friends or view their friends.
- **Handling Friend Requests:** Use the "Friend Requests" button to accept or decline incoming requests.

#### Interacting with Other Users' Profiles
- **Viewing Profiles:** Click on a user's profile picture from a post in your feed to view their profile.
   - **If Not Friends:** You'll see an "Add Friend" option and won't be able to view their posts until the friend request is accepted.
   - **If Friends:** You can view their posts displayed beneath their name.

This comprehensive guide should make navigating and utilizing the FooBook_Android app's features straightforward. If there's anything else you'd like to know or need further clarification on, don't hesitate to ask!


## Development and Testing
### Development Approach
- Planned user journey and data flow for a seamless app experience.
- Modular development strategy for easy feature testing and integration.
- Adoption of MVVM architecture for code that's both maintainable and testable.

### Testing
- Comprehensive unit and integration testing of features.
- End-to-end testing for ensuring feature compatibility and data integrity.
- Application testing across different devices and Android versions for consistent user experience.

### Wrap-Up
The latest advancements in FooBook_Android mark a significant enhancement in functionality and user interface. We encourage you to explore these updates, share your feedback, and contribute to ongoing development.
For in-depth details on each feature, refer to the [`linked Jira`](https://chengoizman.atlassian.net/jira/software/projects/AP/boards/4/backlog) tasks within this document.

## Photos
<div align="center">
  <img src="https://github.com/RoeiMesi/FakeFooBook_Android/assets/23407020/7976fecf-f863-4611-ab4f-31cdafe62632" alt="Image 1">
  <img src="https://github.com/RoeiMesi/FakeFooBook_Android/assets/23407020/8e4ac62c-daa7-4b1e-b255-cb64270deeec" alt="Image 2">
  <img src="https://github.com/RoeiMesi/FakeFooBook_Android/assets/23407020/b3186cb0-e58d-4b1c-b4e8-09854ddfc95f" alt="Image 3">
  <img src="https://github.com/RoeiMesi/FakeFooBook_Android/assets/23407020/1b7ad99d-61c1-4976-82b3-c6e948824e79" alt="Image 4">
  <img src="https://github.com/RoeiMesi/FakeFooBook_Android/assets/23407020/4ddac3ce-d4ac-4a2f-8800-3602efb4b39f" alt="Image 5">
  <img src="https://github.com/RoeiMesi/FakeFooBook_Android/assets/23407020/53cbd007-57c0-4762-8871-4a5c5fc14ba9" alt="Image 6">
  <img src="https://github.com/RoeiMesi/FakeFooBook_Android/assets/23407020/b0c3f749-61af-40e6-911e-fe14caebdd8c" alt="Image 7">
  <img src="https://github.com/RoeiMesi/FakeFooBook_Android/assets/23407020/c62bc21d-ba18-4171-93ab-1579f0d6315f" alt="Image 8">
  <img src="https://github.com/RoeiMesi/FakeFooBook_Android/assets/23407020/6b59bb92-6a10-498e-a79d-4839fe87652d" alt="Image 9">
  <img src="https://github.com/RoeiMesi/FakeFooBook_Android/assets/23407020/0e448103-6319-413d-9611-48abdfc2e987" alt="Image 10">
  <img src="https://github.com/RoeiMesi/FakeFooBook_Android/assets/23407020/4e2f818b-6658-4dd7-981e-209ce5534638" alt="Image 11">
</div>
