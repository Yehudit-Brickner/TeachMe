from datetime import datetime

import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
# from pip._vendor.msgpack import Timestamp

from DB.Firestore_Base_DB import *

class Firestore_DB(Firestore_Base_DB):
    def get_users(self):
        users_ref = self.db.collection('users')
        docs = users_ref.stream()
        data = {}
        for doc in docs:
            print(f'{doc.id} => {doc.to_dict()}')
            data[doc.id] = doc.to_dict()
        return data

    def get_meetings(self, lesson_id: str):
        meetings = self.db.collection_group('lessons').where('lessonId', '==', lesson_id)
        i = 0
        data = {}
        docs = meetings.stream()
        for doc in docs:
            print(f'{"*"*10}{doc.id} => {doc.to_dict()}')

            data[i] = doc.to_dict()
            i += 1
        return data

    def get_meetings_by_time(self, start_dt: datetime = datetime(2022, 12, 1)):
        # start_dt = Timestamp.from_datetime(datetime(2020, 1, 1))
        meetings = self.db.collection_group('meetings').where('startDateTime', ">=", start_dt)
        i = 0
        data = {}
        docs = meetings.stream()
        for doc in docs:
            print(f'{doc.id} => {doc.to_dict()}')
            data[i] = doc.to_dict()
            i += 1
        return data


# db = Firestore_DB()
# db.get_users()

from datetime import datetime

