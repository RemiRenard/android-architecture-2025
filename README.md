# Android Architecture 2025

This project was based on the previous one (https://github.com/RemiRenard/android-architecture-2024) with a lot of improvements!

## Architecture

- MVI

## CI

- Github action
- Run tests
- Deploy to Firebase Distribution
- Send mail to testers with debug & release APK

## Libraries

| Name                                                                                | Description                                   |
|-------------------------------------------------------------------------------------|-----------------------------------------------|
| [Hilt](https://dagger.dev/hilt)                                                     | Provides dependency injection                 |
| [Compose Navigation](https://developer.android.com/develop/ui/compose/navigation)   | Navigate between composable                   |
| [Room](https://developer.android.com/training/data-storage/room)                    | Local database                                |
| [Firebase](https://firebase.google.com/docs/cloud-messaging)                        | Notifications                                 |
| [Jupiter](https://station.jup.ag/docs)                                              | Unit Tests (Junit5)                           |
| [Retrofit](https://github.com/square/retrofit)                                      | A type-safe HTTP client for Android and Java. |
| [OkHttp](https://square.github.io/okhttp)                                           | HTTP client                                   |
| [Datastore](https://developer.android.com/jetpack/androidx/releases/datastore)      | Store local data asynchronously               |

## Features

- Login
- Create an account
- Get my account
- Logout

## Building & Running

To build or run the project, you should have your own server and your own /app/google-services.json !
So the purpose of this repository is just to see and understand how the architecture works until you create your own backend.
Don't worry i'll upload the backend repository one day :p 

| Task                          | Description                                               |
|-------------------------------|-----------------------------------------------------------|
| `./gradlew test`              | Run the tests                                             |
| `./gradlew assembleDebug`     | Build in debug                                            |
| `./gradlew assembleRelease`   | Build in release (Be careful, you should signed your app) |                       |=

