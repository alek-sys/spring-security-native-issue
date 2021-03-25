# Spring Native issue

This is a sample app to reproduce Spring Native issue. The issue seems to be around `AutowireCapableBeanFactory`.

# Sample app

Components of the sample app:
1. `SomeBean` and `AnotherBean` are two mock beans
2. `TestController` just to test injection worked. `index` method is to run `AutowireCapableBeanFactory` autowiring manually.

# Steps to reproduce

1. Build Docker image for the sample app `./gradlew bootBuildImage` and run it `docker run -it --rm -p 8080:8080 demo:0.0.1-SNAPSHOT`
2. Check output of `http://localhost:8080/` - should be `Autowired`
3. Uncomment `bootBuildImage` configuration in `build.gradle` to enable native build
4. Build Docker image for the sample app `./gradlew bootBuildImage`, now native
5. Check output of `http://localhost:8080/` - now it's `Not autowired` means object wasn't post-processed correctly