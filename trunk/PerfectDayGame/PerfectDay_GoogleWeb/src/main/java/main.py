import os

import wsgiref.handlers
from google.appengine.ext.webapp import template
from google.appengine.ext import webapp
from google.appengine.ext.webapp.util import run_wsgi_app
from google.appengine.api import users
from google.appengine.ext import db

from models import Foro,Post
from foros import Foros,Posts
from login import Login,Logout

class MainPage(webapp.RequestHandler):
  def get(self):
    user = users.get_current_user()
    path = os.path.join(os.path.dirname(__file__), 'templates/main.html')
    if user:        
        self.response.out.write(template.render(path, {'conectado':True, 'usuario':user.nickname()}))
    else:
        self.response.out.write(template.render(path, {'conectado':False}))
        

    
def main():
    application = webapp.WSGIApplication(
                         [('/', MainPage),
                            ('/foros',Foros),
                            ('/foros/(.*?)/(.*?)',Posts),
                            ('/login',Login),('/logout',Logout)],
                         debug=True)
    wsgiref.handlers.CGIHandler().run(application)


if __name__ == "__main__":
  main()
