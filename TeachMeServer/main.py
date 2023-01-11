# from flask import Flask
#
# app = Flask(__name__)
#
#
# @app.route('/')
# def hello():
#     return 'Hello, World!'
#
#
# @app.route('/hello/<name>')
# def hello_name(name):
#     return 'Hello %s!' % name
#
#
# # def gfg():
# #    return 'geeksforgeeks'
# # app.add_url_rule('/', 'g2g', gfg)
#
# if __name__ == '__main__':
#     app.run()

import os
from flask import Flask, redirect, url_for, request, jsonify

from DB.Lesson_DB import Lesson_DB
from DB.db_utils import db_utils as utils

from db_try import Firestore_DB

app = Flask(__name__)

db = Firestore_DB()
lessons_db = Lesson_DB()


@app.route('/')
def index():
    return 'Flask is running!'


# @app.route('/success/<name>')
# def success(name):
#     return 'welcome %s' % name, 200\

def success(name):
    return 'welcome %s' % name


@app.route('/login', methods=['POST', 'GET'])
def login():
    if request.method == 'POST':
        request_dict = request.get_json()
        user = request_dict['nm']
        return utils.run_func(success, user)    # redirect(url_for('success', name=user))
    else:
        user = request.args.get('nm')
        return jsonify(db.get_users()), 200


@app.route('/get/lessons', methods=['GET'])
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


@app.route('/get/lesson/<uid>/<lesson_id>')
def success(uid: str, lesson_id: str):
    return utils.run_func(lessons_db.get_lesson_from_DB, uid, lesson_id)

@app.route('/set/lesson', methods=['POST'])
def set_lesson():
    request_dict = request.get_json()
    return utils.run_func(lessons_db.set_lesson_data, request_dict)


@app.route('/get/meetings', methods=['GET'])
def meetings():
    lesson_id = request.args.get('LID', default='', type=str)
    print(lesson_id)
    return utils.run_func(db.get_meetings, lesson_id)
    # return jsonify(db.get_meetings_by_time()), 200


if __name__ == '__main__':
    port = int(os.environ.get('PORT', 9090))
    app.run(threaded=True, host='0.0.0.0')      # , ssl_context='adhoc'
