from bs4 import BeautifulSoup
import requests
from data.articleObject import Article
from urllib.parse import urlparse
import sys

argv = sys.argv
if len(argv) != 2:
    print("One argument needed")
    sys.exit()
test_url = argv[1]
headers = {
    "user-agent": "Mozilla/5.0"  # Making out script look like browser
}

response = requests.get(test_url, headers=headers)
soup = BeautifulSoup(response.text, features="html.parser")
domain = urlparse(test_url).netloc

found = False
if 'ynet.co.il' in domain:
    article_div = soup.find("div", class_="article-body")  # change selector based on site
    texts = [span.get_text(strip=True) for span in article_div.find_all("span", attrs={"data-text": "true"})]
    found = True
elif "maariv.co.il" in domain:
    container = soup.find("section", class_="article-body article-body-min-width")
    if container:
        texts = [p.get_text(strip=True) for p in container.find_all("p")]
        found = True
elif "walla.co.il" in domain:
    container = soup.find("article", class_="common-item")
    if container:
        texts = [p.get_text(strip=True) for p in container.find_all("p", class_="article_speakable")]
        found = True


if found == False:
    article_obj = Article()
else:
    text = "\n".join(texts)
    article_obj = Article(title="Test",body=text)
print(article_obj)

#print(soup.prettify())