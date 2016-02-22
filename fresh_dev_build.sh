#!/usr/bin/env bash
rm -vrf ./build
rm -vrf ./sub/Growthcraft/build
gradle setupDevWorkspace build
