#!/bin/sh

export TESSDATA_PREFIX=/usr/local/share/tessdata

echo $TESSDATA_PREFIX

sudo /usr/local/bin/tesseract -l rus+eng  $1  $2  pdf
 
