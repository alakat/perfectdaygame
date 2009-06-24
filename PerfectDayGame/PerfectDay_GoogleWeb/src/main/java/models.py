from google.appengine.ext import db

#Comentario 
class Foro(db.Model):
    name = db.StringProperty(required=True)
    creador = db.UserProperty(required=True)
    creada = db.DateTimeProperty(auto_now_add=True)

class Post(db.Model):
    title = db.StringProperty(required=True)
    text = db.StringProperty(required=True,multiline=True)
    creador = db.UserProperty(required=True)
    foro = db.ReferenceProperty(Foro,collection_name='posts')
    visitas = db.IntegerProperty()
    creada = db.DateTimeProperty(auto_now_add=True)
  
class Comentario(db.Model):
    text = db.StringProperty(required=True,multiline=True)
    creador = db.UserProperty(required=True)
    post = db.ReferenceProperty(Post,collection_name='coments') 
    creada = db.DateTimeProperty(auto_now_add=True) 

