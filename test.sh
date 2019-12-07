#!/bin/bash
sudo dpkg -r icu
./packaging.sh
sudo dpkg -i icu_1.0.deb
cp ~/ICU/ML/api_key.json ~/.icu_1.0/
