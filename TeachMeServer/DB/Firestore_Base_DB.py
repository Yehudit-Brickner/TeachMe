import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
from google.cloud.firestore_v1 import *


class Firestore_Base_DB():
    cred = credentials.Certificate("data/teachme-key.json")
    firebase_admin.initialize_app(cred)
    db: Client = firestore.client()


