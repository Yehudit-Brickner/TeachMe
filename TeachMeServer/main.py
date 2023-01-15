import os
from flask import Flask, redirect, url_for, request, jsonify

from DB.Lesson_DB import Lesson_DB
from DB.MeetingDB import MeetingDB
from DB.db_utils import db_utils as utils

from db_try import Firestore_DB

app = Flask(__name__)

db = Firestore_DB()
lessons_db = Lesson_DB()
meeting_db = MeetingDB()


@app.route('/')
def index():
    return 'Flask is running!'


@app.route('/get_tutor_lesson', methods=['GET'])
def get_tutor_lesson():
    uid = request.args.get('UID', default='', type=str)
    lesson_id = request.args.get('LID', default='', type=str)
    mess, code = utils.run_func(lessons_db.get_lesson_from_db, uid, lesson_id)
    print(mess.get_json())
    return mess, code


@app.route('/get/lessons/names', methods=['GET'])
def get_lesson_names():
    return utils.run_func(lessons_db.get_lessons_names)

@app.route('/get/lessons/by_name', methods=['GET'])
def get_lessons():
    lesson_name = request.args.get('LID', default=None, type=str)
    start_time = request.args.get('start', default=None, type=str)
    end_time = request.args.get('end', default=None, type=str)
    print(start_time)

    start_time = utils.str_to_datetime(start_time)
    end_time = utils.str_to_datetime(end_time)
    print(start_time)
    # lessons_db.get_lessons_by_name(lesson_name, start_time, end_time)
    return utils.run_func(lessons_db.get_lessons_by_name, lesson_name, start_time, end_time)

# @app.route('/get/lesson/<uid>/<lesson_id>')
# def get_lesson_with_uid(uid: str, lesson_id: str):
#     return utils.run_func(lessons_db.get_lesson_from_DB, uid, lesson_id)

@app.route('/set/lesson', methods=['POST'])
def set_lesson():
    request_dict = request.get_json()
    return utils.run_func(lessons_db.set_lesson_data, request_dict)


@app.route('/get/meetings', methods=['GET'])
def meetings():
    lesson_id = request.args.get('LID', default='', type=str)
    tutor_id = request.args.get('TID', default='', type=str)
    print(lesson_id)
    mess, code = utils.run_func(meeting_db.get_meeting_tutorid_lessonid, tutor_id, lesson_id)
    print(mess.get_json())
    return mess, code
    # return jsonify(db.get_meetings_by_time()), 200


@app.route('/get/meeting', methods=['GET'])
def get_meeting():
    uid = request.args.get('UID', default='', type=str)
    lid = request.args.get('LID', default='', type=str)
    mid = request.args.get('MID', default='', type=str)

    mess, code = utils.run_func(meeting_db.get_meeting_parms, uid, lid, mid)
    print(mess.get_json())
    return mess, code


@app.route('/get/meetings/student/<student_id>', methods=['GET'])
def get_student_meetings(student_id: str):
    return utils.run_func(meeting_db.get_student_meetings, student_id)


@app.route('/get/meetings/tutor/<tutor_id>', methods=['GET'])
def get_tutor_meetings(tutor_id: str):
    return utils.run_func(meeting_db.get_tutor_meetings, tutor_id)


@app.route('/set/meeting', methods=['POST'])
def set_meeting():
    request_dict = request.get_json()
    mess, code = utils.run_func(meeting_db.set_meeting, request_dict)
    print(mess.get_json())
    return mess, code


@app.route('/update/meeting', methods=['POST'])
def update_meeting():
    request_dict = request.get_json()
    return utils.run_func(meeting_db.update_meeting, request_dict)


if __name__ == '__main__':
    port = int(os.environ.get('PORT', 9090))
    app.run(threaded=True, host='0.0.0.0', port=port)      # , ssl_context='adhoc'
