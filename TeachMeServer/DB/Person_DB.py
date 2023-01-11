from DB.Firestore_Base_DB import Firestore_Base_DB
from DB.db_utils import *

class Person_DB(Firestore_Base_DB):
    def get_tutor_from_db(self, u_id: str):
        doc_ref = self.db.collection(db_utils.DOCK_USER).document(u_id)
        doc = doc_ref.get()
        doc_dict = doc.to_dict()
        if doc_dict['is_tutor']:
            # print(doc.to_dict())
            return doc_dict
        else:
            print("not a tutor")
            return None

    def get_student_from_db(self, u_id: str):
        doc_ref = self.db.collection(db_utils.DOCK_USER).document(u_id)
        doc = doc_ref.get()
        doc_dict = doc.to_dict()
        if doc_dict['is_student']:
            # print(doc.to_dict())
            return doc_dict
        else:
            print("not a student")
            return None

    def set_person_data(self, person_dict: dict, u_id):
        user_ref = self.db.collection(db_utils.DOCK_USER)
        user_ref.document(u_id).set(person_dict)

    def set_person_data_2(self, person_dict: dict, u_id):
        user_ref = self.db.collection(db_utils.DOCK_USER)
        user_ref.document(u_id).set(person_dict)

    def update_person_data_db(self, person_dict: dict, u_id):
        user_ref = self.db.collection(db_utils.DOCK_USER)
        user_ref.document(u_id).set(person_dict)
