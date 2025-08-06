from bs4 import BeautifulSoup
import requests
from articleObject import Article

test_url = 'https://www.ynet.co.il/vacation/flights/article/ry9jjog00lx#autoplay'
test_url = 'https://www.ynet.co.il/entertainment/article/sjkzgsxdxe'

headers = {
    "user-agent": "Mozilla/5.0"  # Making out script look like browser
}

response = requests.get(test_url, headers=headers)
soup = BeautifulSoup(response.text, features="html.parser")


article_div = soup.find("div", class_="article-body")  # change selector based on site

paragraphs = []
texts = [span.get_text(strip=True) for span in article_div.find_all("span", attrs={"data-text": "true"})]

text = "\n".join(texts)
article_obj = Article(title="Test",body=text)
print(article_obj)

#print(soup.prettify())