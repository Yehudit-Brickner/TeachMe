from datetime import datetime

import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
from firebase_admin.firestore import *

from DB.Firestore_Base_DB import Firestore_Base_DB
from DB.DBErrorException import DBErrorException
from DB.db_utils import db_utils as utils


class Lesson_DB(Firestore_Base_DB):
    # def __init__(self):
    #     super().__init__()

    def set_lesson_data(self, lesson: dict):
        if "tutorId" not in lesson or type(lesson["tutorId"]) is not str:
            raise DBErrorException("invalid Tutor Id")
        if "lessonId" not in lesson or type(lesson["lessonId"]) is not str:
            raise DBErrorException("invalid Lesson Id")
        tutor_id = lesson["tutorId"]
        lesson_id = lesson["lessonId"]
        self.db.collection(f'{utils.DOCK_USER}/{tutor_id}/{utils.DOCK_LESSON}').document(lesson_id).set(lesson)
        return True

    def get_lesson_from_DB(self, uid: str, lesson_id: str):
        doc_ref = self.db.collection(f'{utils.DOCK_USER}/{uid}/{utils.DOCK_LESSON}').document(lesson_id)
        doc = doc_ref.get()

        if not doc.exists:
            raise DBErrorException("lesson or uid doesn't exists")
        return doc.to_dict()

    def get_lessons_by_tutor_id(self, tutor_id):
        docs = self.db.collection(f'{utils.DOCK_USER}/{tutor_id}/{utils.DOCK_LESSON}').stream()
        #docs_names = set()
        lessons = []
        for doc in docs:
            lessons.append(doc.to_dict())

        #docs_names = list(docs_names)
        return lessons


    def get_lessons_by_name(self, name: str, start_dt: datetime = None, end_dt: datetime = None):
        if name is None or name == "":
            raise DBErrorException("name of lesson must not be empty")
        if start_dt is None and end_dt is not None:
            raise DBErrorException("by filtering, start time must be included if end time is provided")

        start_dt = datetime.now() if start_dt is None else start_dt

        meetings = self.db.collection_group(utils.DOCK_MEETINGS).where("lessonId", "==", name)\
                                                                .where("startDateTime", ">=", start_dt)
        if end_dt is not None:
            meetings = meetings.where("endDateTime", "<=", end_dt)
        meetings = meetings.stream()

        tutors_lessons = set()

        for meet in meetings:
            if end_dt is not None:
                end_date_time = utils.str_to_datetime(meet.get("endDateTime"))
                if end_date_time is None or end_dt < end_date_time:
                    continue
            tutors_lessons.add(meet.get("tutorId"))

        lessons = []
        for tutor_id in tutors_lessons:
            try:
                lessons += [self.get_lesson_from_DB(tutor_id, name)]
            except DBErrorException:
                pass

        return lessons


