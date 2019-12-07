#!/bin/bash
cp -r server/ ./icu_1.0/usr/lib/python2.7/dist-packages/icu/server
cp -r server/ ./icu_1.0/usr/lib/python2.7/dist-packages/icu/ML

dpkg -b icu_1.0

rm -r ./icu_1.0/usr/lib/python2.7/dist-packages/icu/server
rm -r ./icu_1.0/usr/lib/python2.7/dist-packages/icu/ML
