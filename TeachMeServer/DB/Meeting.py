from datetime import datetime
from typing import TypeVar

from DB.db_utils import db_utils as util

MeetingT = TypeVar("MeetingT", bound="Meeting")


class Meeting:
    def __init__(self, lessonId: str = "", startDateTime=None, endDateTime=None, tutorId: str = "",
                 zoom: bool = False, inPerson: bool = False):
        self.meeting_id = ""
        self.lesson_id = lessonId
        self.tutor_id = tutorId
        self.student_id = ""
        self.zoom = zoom
        self.start_date_time = util.str_to_datetime(startDateTime)
        self.end_date_time = util.str_to_datetime(endDateTime)  # converting to datetime object
        # self.start_date_time=start_date_time
        # self.end_date_time=end_date_time
        self.in_person = inPerson

    def get_tutor_id(self):
        return self.tutor_id

    def get_in_person(self):
        return self.in_person

    def get_lesson_id(self):
        return self.lesson_id

    def get_meeting_id(self) -> str:
        return self.meeting_id

    def get_zoom(self):
        return self.zoom

    def get_student_id(self):
        return self.student_id

    def get_start_date_time(self) -> datetime:
        return self.start_date_time

    def get_end_date_time(self) -> datetime:
        return self.end_date_time

    def set_meeting_id(self, meeting_id):
        self.meeting_id = meeting_id

    def set_student_id(self, student_id):
        self.student_id = student_id

    def set_tutor_id(self, tutor_id):
        self.tutor_id = tutor_id