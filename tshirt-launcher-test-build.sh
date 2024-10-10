./gradlew build
cp buttonbox-lib/build/libs/buttonbox-lib.jar ../TShirtLauncher/libs
cd ../TShirtLauncher
./buttonbox-simulate.sh
