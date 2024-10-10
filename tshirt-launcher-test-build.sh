./gradlew build
cp buttonbox-lib/build/libs/buttonbox-lib.jar ../TShirtLauncher/libs
cp buttonbox-bridge-midi/build/libs/buttonbox-bridge-midi.jar ../TShirtLauncher/libs
cd ../TShirtLauncher
./buttonbox-simulate.sh
