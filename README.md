# Simple Weather App

It is a simple and intuitive Android application that provides you with up-to-date weather information right at your fingertips. Developed using Kotlin and Android's modern architecture components, this app delivers accurate hourly weather forecasts and a user-friendly interface.

## Built with

- **Kotlin**,
- **Coroutines** - _for asynchronous and more..,_
- **Android Architecture Components** - _Collection of libraries that help you design robust, testable, and maintainable apps,_
- **LiveData** - _to notify views when the underlying database changes,_
- **ViewModel** - _to store UI-related data that isn't destroyed on UI changes,_
- **Room** - _to store searched weather information_
- **Jetpack navigation** - _to navigate between fragments_
- **MVVM Architecture** - _to separate the business logic from the UI_
- **Gson library** - _for JSON serialization/deserialization_

- **Retrofit** - _to build communication between the app (client) and web servers_
- **OpenMeteo API** - _to retrieve weather information_
- **GeoCoding API** - _to retrieve country and city information (especially latitude and longitude info)_

## Package structure

```
|--- adapters // to store adapter classes
|
|--- api // to store api-related classes
|     |-- geocoding
|     |-- weather
|
|--- data // to store data and database related classes
|     |-- db
|         |- entities
|         |- repositories
|
|--- ui // to store ui-related classes
|
|--- utils // to store utility classes
```


## Video (click to navigate Youtube - 2 min video)
[![Video Thumbnail](https://user-images.githubusercontent.com/116954772/262738070-d5c51b6c-379f-4655-b60e-a0ed195d5715.png)](https://www.youtube.com/watch?v=zTKWSbGS0yk)
