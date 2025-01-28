---
layout: default
title: Introduction
---

# Introduction

ButtonBoxLib is structured to be as modular as possible. Most classes implement generic interfaces, which makes it easy to create custom classes to extend the library.

## Structure

Programs using ButtonBoxLib are split into two main sections - the server and the bridge. The server program consists of a class which runs as a part of the robot code and handles all the logic for the controls, while the bridge program is run standalone on the client device and handles interfacing with the actual hardware.

There's a few key classes and interfaces which are used throughout the library.

### Controls

[Controls]({{ site.javadoc_url }}/io/github/roboblazers7617/buttonbox/Control.html) handle all the logic and communication for controls on the buttonbox. The Control class is designed such that the same class can be used on the client and the server, making it easier to create Controls by allowing the client and server to share most of the same code. Control classes are generally extended by one or more PhysicalControl classes, which take Addresses and communicate with the hardware.

### Addresses

[Addresses]({{ site.javadoc_url }}/io/github/roboblazers7617/buttonbox/Address.html) handle the communication with actual hardware. The Address interface represents a single double in the range \[0-1\] which can be sent and received to and from the hardware.
