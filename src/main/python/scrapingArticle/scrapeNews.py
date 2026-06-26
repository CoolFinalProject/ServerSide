from bs4 import BeautifulSoup
import requests
from data.articleObject import Article
from urllib.parse import urlparse
from data.config import SITE_CONFIG
import sys
import time

sys.stdout.reconfigure(encoding="utf-8")

argv = sys.argv

if len(argv) != 2:
    print("One argument needed")
    sys.exit()

test_url = argv[1]

headers = {
    "user-agent": "Mozilla/5.0"
}

response = requests.get(test_url, headers=headers)
start = time.perf_counter()

soup = BeautifulSoup(response.text, features="html.parser")
domain = urlparse(test_url).netloc

found = False
texts = []

config = next((val for key, val in SITE_CONFIG.items() if key in domain), None)

#print("domain:", domain, file=sys.stderr)
#print("config:", config, file=sys.stderr)

if config:
    container_config = config["container"]
    container = soup.find(container_config[0], attrs=container_config[1])

    #print("container found:", container is not None, file=sys.stderr)

    if container:
        par_config = config["paragraphs"]

        texts = [
            p.get_text(strip=True)
            for p in container.find_all(par_config[0], attrs=par_config[1])
        ]

        #print("paragraphs found:", len(texts), file=sys.stderr)
        #print("first paragraph:", texts[0] if texts else "NONE", file=sys.stderr)

        found = len(texts) > 0

if not found:
    article_obj = Article()
else:
    text = "\n".join(texts)
    article_obj = Article(title="Test", body=text)

end = time.perf_counter()

print(article_obj.body)