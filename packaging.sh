#!/bin/bash
cp -r server/ ./icu_1.0/usr/lib/python2.7/dist-packages/icu_1.0/server
cp -r server/ ./icu_1.0/usr/lib/python2.7/dist-packages/icu_1.0/ML

dpkg -b icu_1.0

rm -r ./icu_1.0/usr/lib/python2.7/dist-packages/icu_1.0/server
rm -r ./icu_1.0/usr/lib/python2.7/dist-packages/icu_1.0/ML
