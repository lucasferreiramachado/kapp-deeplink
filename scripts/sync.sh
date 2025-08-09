killall java
cd ..

./gradlew syncDebugLibJars --refresh-dependencies
./gradlew resolveIdeDependencies
./gradlew prepareKotlinBuildScriptModel
./gradlew prepareKotlinIdeaImport