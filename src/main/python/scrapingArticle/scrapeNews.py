from bs4 import BeautifulSoup
import requests
from data.articleObject import Article
from urllib.parse import urlparse
from data.config import SITE_CONFIG
import sys
import time
argv = sys.argv
if len(argv) != 2:
    print("One argument needed")
    sys.exit()
test_url = argv[1]
headers = {
    "user-agent": "Mozilla/5.0"  # Making out script look like browser
}

response = requests.get(test_url, headers=headers)
start = time.perf_counter()
soup = BeautifulSoup(response.text, features="html.parser")
domain = urlparse(test_url).netloc

found = False

config = next((val for key, val in SITE_CONFIG.items() if key in domain), None)

if config:
    container_config= config["container"]
    container = soup.find(container_config[0],class_=container_config[1]["class"])
    if container:
        par_config = config["paragraphs"]
        if "class" in par_config[1]:
            my_class_ = class_=par_config[1]["class"]
            my_attrs= {}
        else:
            my_class_ = None
            my_attrs = par_config[1]
        texts = [p.get_text(strip=True) for p in container.find_all(par_config[0],class_=my_class_,attrs=my_attrs)]
        found=True
        


if found == False:
    article_obj = Article()
else:
    text = "\n".join(texts)
    article_obj = Article(title="Test",body=text)

end =time.perf_counter()
print(f"Elapsed time for BSoup only! {end - start :.6f} sec")
print(f"Proccessing speed: {1/(end-start):.6f} articles per second")
print(article_obj)

#print(soup.prettify())