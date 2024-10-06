./gradlew build
cp buttonbox-lib/build/libs/buttonbox-lib.jar ../TShirtLauncher/libs
tmux \
    new-session  'cd ../TShirtLauncher && ./gradlew simulateJava' \; \
    split-window './gradlew :buttonbox-bridge:run'
