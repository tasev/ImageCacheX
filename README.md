# Image loading library

## App features:
1. Image loading library
   Implement a library that will download and cache an image. It should receive
   the following input parameters:

● Url
● Placeholder
● Some view, that the image loading library will assign the image to (it
should support both UIKit and SwiftUI )
Cached image should be valid for 4h. Also the library should support a manual
cache invalidation.

3. Example app
   ● Fetch a json with the images url list
   ● Build a screen with the list of loaded images, each view should display the
   loaded image (or a placeholder while loading) and it’s id (both id and url can
   can be found within the JSON loaded earlier)
   ● Add a button which will invalidate the cache by tap.
   ● The app can be built either with Java or Kotlin, the library should support
   both.

## Architecture
- Written in Kotlin.
- Clean architecture, MVVM

# Library references
- Kotlin
- Coroutines - A coroutine is a concurrency design pattern that you can use on Android to simplify code that executes asynchronously
- Flow - Flow is used to pass (send) a stream of data that can be computed asynchronously
- Dagger-Hilt - for dependency injection.
- ViewModel - Stores UI-related data that isn't destroyed on UI changes.
- Room - Used to create room db and store the data.
- Retrofit - Used for REST api communication.