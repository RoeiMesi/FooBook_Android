# TCP Server Integration - Android Client Updates

This branch of the Android application introduces significant enhancements to how the app handles URLs within posts, specifically regarding the detection and blocking of malicious URLs through integration with the TCP server.

## Overview

In this update, we've enhanced the Android app's ability to detect malicious URLs during post creation and editing. The system now actively denies any attempts to create or edit posts containing URLs that are identified as malicious through API calls to the TCP server.

## Key Features

- **Enhanced Security Alerts**: Alerts have been updated to provide clearer information regarding the denial of post creation or editing due to the presence of malicious URLs.
- **Malicious URL Blocking**: Posts containing URLs that are returned as malicious by the TCP server are automatically denied to ensure the integrity and security of the content shared within the platform.

## How It Works

When a user attempts to create or edit a post that includes URLs, the Android app makes an API call to the TCP server to check these URLs against a Bloom filter. If any URL is flagged as malicious, the action is immediately denied, and an alert is shown to the user explaining why the post could not be processed.

## Additional Notes

This branch is focused on security enhancements related to URL processing. For comprehensive documentation on other aspects of the Android application, including more detailed features and functionalities, please refer to the main documentation available in the `server-dev` branch or our [Wiki](https://github.com/TomerBeren/FooBook_Server/tree/tcp-server-main) in the tcp-server-main branch of the FooBook_Server repository.

## Reference

For a detailed README of the Android application itself and more in-depth technical details, please refer to:
- [Main Android Application Documentation](https://github.com/RoeiMesi/FooBook_Android/tree/server-dev)
