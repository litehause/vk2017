#!/bin/sh
rm -rf ../../../public/js/**/*.js
coffee -c -b -o ../../../public/js *.coffee