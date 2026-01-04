class Article:

    def __init__(self, title="", body=""):
        self.title = title
        self.body = body
    
    def __str__(self):
        return f"Article : [title:\n{self.title}\n,\nbody:\n{self.body}]"