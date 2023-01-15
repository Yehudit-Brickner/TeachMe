from datetime import datetime
from dateutil import parser
from flask import jsonify
from DB.DBErrorException import DBErrorException


class db_utils():
    DOCK_USER = "users"
    DOCK_LESSON = "lessons"
    DOCK_MEETINGS = "meetings"
    DATE_FORMAT = "%Y-%m-%d"
    TIME_FORMAT = "%H:%M"  # HH:mm:ss
    DATE_TIME_FORMAT = DATE_FORMAT + " " + TIME_FORMAT

    @staticmethod
    def str_to_datetime(s):
        for form in [db_utils.DATE_TIME_FORMAT, db_utils.DATE_FORMAT, "%d/%m/%Y"]:      # , db_utils.TIME_FORMAT]:
            try:
                return datetime.strptime(s, form)
            except:
                pass

        return None

    @staticmethod
    def dict_timestamp_to_datetime(time_dict: dict):
        print(time_dict)
        seconds = time_dict['seconds'] + time_dict['nanoseconds'] / 1e9
        dt = datetime.fromtimestamp(seconds)
        return dt

    @staticmethod
    def get_time_from_timestamp(t: str):
        tmp = db_utils.str_to_datetime(t)
        if tmp is not None:
            return tmp
        t = db_utils.str_to_datetime(t[0:t.find("+") - 3])
        if t is None:
            return datetime.fromtimestamp(0)
        return t

    @staticmethod
    def translate_time(time):
        try:
            return db_utils.dict_timestamp_to_datetime(time)
        except:
            return db_utils.get_time_from_timestamp(str(time))

    @staticmethod
    def run_func(f, *args, **kwargs):
        try:
            return jsonify({"result": f(*args, **kwargs)}), 200
        except DBErrorException as ex:
            data, code = ex.get_as_response()
            return jsonify(data), code


# from datetime import datetime

# date_time_str = '2023-09-18 23:55'
#
# print(db_utils.str_to_datetime(date_time_str))
# print(db_utils.str_to_datetime("13/1/2023"))