#!/usr/bin/env bash
rm -rf ./build
rm -rf ./sub/Growthcraft/build
gradle setupDevWorkspace build
