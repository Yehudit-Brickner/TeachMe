# from DB import *
# # from db_try import Firestore_DB
# from DB.Lesson_DB import Lesson_DB
# from DB.MeetingDB import MeetingDB
# from db_try import Firestore_DB

# d = Firestore_DB()
# d.get_meetings("infi")

# d = Lesson_DB()
# data_dict, code_id = d.get_lesson_from_DB('vElfoL0jnONTmjhLpaHpHMGiMJU2', 'Intro to Java')
# print(data_dict, '\n')
#
# print(code_id, '\n')
#
#
# d = Lesson_DB()
# data_dict, code_id = d.get_lesson_from_DB('vElfoL0jnONTmjhLpaHpHMGiMJU2', 'Intro to java')
#
# print(data_dict, '\n')
#
# print(code_id)
#
# print(d.get_lessons_by_tutor_id("v"))
#
# # lesson = {""}
#
# print(MeetingDB().get_student_meetings("david_ehevich"))

from flask import jsonify, json

from DB.MeetingDB import MeetingDB

# data = {"key": "value"}
# response = jsonify(data)
#
# # parse the json object from the response
# json_data = json.loads(response.get_data())
#
# # print the json object
# print(json.dumps(json_data, indent=4))


db = MeetingDB()

print(db.overleap_check('aqYp0NlSRkezyOxYihCN5eMtNHt2', 'C++', '2023-1-14 10:00', '2023-1-17 16:30'))