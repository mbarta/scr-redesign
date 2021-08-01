# Smart Contact Reminder redesign

Redesign of the *contact screen* and *contact detail screen* for **Smart Contact Reminder app** inspired by [design by Yaroslav Zubko](https://www.uplabs.com/posts/cinema-club-fest-interaction).

The original app is available from [Google Play](https://play.google.com/store/apps/details?id=me.barta.stayintouch) and [project website](https://smartcontactreminder.barta.me/).

## App Architecture

### Data

The network API is simulated by the `FakeApi` class with returns static data with a random delay.
The repositories (`ContactCategoryRepository` and `ContactPersonRepository`) communicate directly with the API, obtain the data requested as DTOs and map them to Entities used within the domain layer.

### Domain

In our very simple sample app, there's almost no business logic. The mappers map DTOs to Entities 1:1. However, a more complex application would require more advanced mapping of DTOs. It would also use UseCases for encapsulating and reusing of business logic.

### Presentation

The sample app uses Activites and Fragments as basic UI screen elements. JetPack ViewModels are used for keeping state between configuration changes and directly communicate with Repositories. View state is represented by LiveData which is observed by the View component.

### Other
* Kotlin programming language
* dependency injection implemented using Hilt
* ASYNC operations implemented using Kotlin Coroutines

## Design Features
* custom view for drawing *AppBar* background
* customised *TabLayout* appearance, animations
* shared elements activity transitions
* custom *CoordinatorLayout* behaviours for background view and *FAB*
* material design components, view animations

## Sample APK
Available in *sample_apk* directory in this repository or directly from [this link](https://github.com/mbarta/scr-redesign/raw/develop/sample_apk/scr-redesign.apk) (minSDK 21).

## Screenshots
![Contact list](https://github.com/mbarta/scr-redesign/raw/develop/screenshots/list.png "Contact list")
![Contact detail](https://github.com/mbarta/scr-redesign/raw/develop/screenshots/detail.png "Contact detail")

## App Demo
![App demo](https://github.com/mbarta/scr-redesign/raw/develop/screenshots/demo.gif "App demo")