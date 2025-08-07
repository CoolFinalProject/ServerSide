#!/bin/bash
mkdir -p test_output
if [ $# -gt 0 ]
  then
    if [[ "$1" == "clean" ]]
        then
            rm -r test_output
            exit 0
    fi
    exit 1
fi


source ../venv/bin/activate

test_url='https://www.ynet.co.il/entertainment/article/sjkzgsxdxe'
python scrapeNews.py $test_url > test_output/ynet.txt

test_url='https://www.maariv.co.il/news/military/article-1221938'
python scrapeNews.py $test_url > test_output/maariv.txt

test_url='https://news.walla.co.il/item/3771473'
python scrapeNews.py $test_url > test_output/walla.txt

exit 0