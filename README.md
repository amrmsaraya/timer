# Timer
[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)
[![Jitpack](https://img.shields.io/jitpack/v/github/amrmsaraya/timer)](https://jitpack.io/#amrmsaraya/timer)

Timer is a general purpose kotlin library that use kotlin coroutines, flows and channels to provide timer features with the most easy and efficient way

## FEATURES
- Stopwatch
- Countdown timer
- Observe timer using coroutine flow that emits `Time()`  class which provide time in an easy and formatted way
- Stopwatch laps
- Start, pause, resume and reset timer
- Configure countdown timer timer and delay anytime
- Get the current status of the stopwatch
- Attach `CoroutineScope` like `viewModelScope` or `lifecycleScope` to the class constructor to cancel active coroutines or use the default scope provided by the class and call `clear()` function to prevent any memory leak 


## USAGE
#### Add the JitPack repository to your root build.gradle file 

```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

#### Add the dependency using Groovy
```groovy
implementation 'com.github.amrmsaraya:timer:1.0.0'
```

#### Add the dependency using Kotlin DSL
```kotlin
implementation("com.github.amrmsaraya:timer:1.0.0")
```

## LICENSE
Timer library is licensed under [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
