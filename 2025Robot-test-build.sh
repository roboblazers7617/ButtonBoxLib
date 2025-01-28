./gradlew build
cp buttonbox-lib/build/libs/buttonbox-lib.jar ../2025Robot/libs
cp buttonbox-lib-midi/build/libs/buttonbox-lib-midi.jar ../2025Robot/libs
cd ../2025Robot
./buttonbox-simulate.sh
