#!/bin/sh
./gradlew build
tmux \
    new-session  './gradlew :example:buttonbox-robot:simulateJava' \; \
    split-window './gradlew :example:buttonbox-bridge:run'
