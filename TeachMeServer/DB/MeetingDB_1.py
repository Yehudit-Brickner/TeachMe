import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
from google.cloud.firestore_v1 import *

from DB.DBErrorException import DBErrorException
from DB.db_utils import db_utils as util
from DB.Meeting import Meeting
from DB.Firestore_Base_DB import Firestore_Base_DB


class MeetingDB(Firestore_Base_DB):
    # Get meetings by tutorid and lessonid
    def get_meeting_tutorid_lessonid(self, tutor_id: str, lesson_id: str) -> list:
        if tutor_id is None or len(tutor_id) == 0:
            print("no tutor")
            #raise DBErrorException
            return {"error": "didn't  enter tutor id"}, 404
        meetings = self.db.collection_group(util.DOCK_MEETINGS).where("tutorId", "==", tutor_id)\
                                                               .where("lessonId", "==", lesson_id).stream()
        return self.get_meeting_list(meetings), 200

    #Get meeting by meeting object
    def get_meeting(self, meeting: Meeting) -> dict:
        # Meetings are unique therefore will result only in one meeting.
        meeting_data = self.db.collection(util.DOCK_USER) \
            .where("meetingId", "==", meeting.get_meeting_id()) \
            .where("studentId", "==", meeting.get_student_id())\
            .where("lessonId", "==", meeting.get_tutor_id()) \
            .where("tutorId", "==", meeting.get_tutor_id())            \
            .stream()
        for meet in meeting_data:
            return self.json_to_meeting(meet.to_dict())
        return {"error": "meeting doesn't exist"}, 404

    #Get meetings by student id
    def get_student_meetings(self, student_id: str) -> list:
        if student_id is None or len(student_id) == 0:
            print("no student")
            return {"error": "didn't enter student id"}, 404
        meetings = self.db.collection_group(util.DOCK_MEETINGS).where("studentId", "==", student_id).stream()
        return self.get_meeting_list(meetings)

    #Gat meetings by tutor id
    def get_tutor_meetings(self, tutor_id) -> list:
        if tutor_id is None or len(tutor_id) == 0:
            print("no tutor")
            return {"error": "didn't  enter tutor id"}, 404
        meetings = self.db.collection_group(util.DOCK_MEETINGS).where("tutorId", "==", tutor_id).stream()
        return self.get_meeting_list(meetings)

    #Created this function because I had repitive code.
    def get_meeting_list(self, meetings):
        error = 0
        meetings_list = []
        for meet in meetings:
            error = error + 1
            meetings_list.append(self.json_to_meeting(meet.to_dict()))
        if error == 0:
            return {"error": "meeting doesn't exist"}, 404
        return meetings_list

    def set_meeting(self, meeting: Meeting):

        # root directory of the DB
        ref = None
        meeting_id = 0
        if meeting.get_meeting_id() is None or not len(meeting.get_meeting_id()):
            print("entered")
            ref = self.db.collection(f"{util.DOCK_USER}/{meeting.get_tutor_id()}/{util.DOCK_LESSON}/{meeting.get_lesson_id()}/{util.DOCK_MEETINGS}")\
                .document()
            # Writing .document is basically adding the auto generated id to the path.
            meeting_id = ref.id
            meeting.set_meeting_id(meeting_id)
        else:
            ref = self.db.collection(f"{util.DOCK_USER}/{meeting.get_tutor_id()}/{util.DOCK_LESSON}/{meeting.get_lesson_id()}/{util.DOCK_MEETINGS}")\
                .document(f"{meeting.get_meeting_id()}")
            meeting_id = meeting.get_meeting_id()

        meeting_data = {#"dateEnd": "", "dateStart": "",
                       "endDateTime": meeting.get_end_date_time(),
                       "inPerson": meeting.get_in_person(), "lessonId": meeting.get_lesson_id(),
                       "meetingId": meeting.get_meeting_id(),
                       "startDateTime": meeting.get_start_date_time(),
                       "studentId": meeting.get_student_id(),
                       #"timeEnd": "", "timeStart": "",
                       "tutorId": meeting.get_tutor_id(), "zoom": meeting.get_zoom()}
        ref.set(meeting_data)


    def update_meeting(self, meeting: Meeting):
        # So we go through only collections , until we need the document, so its: collection().document()
        print(f"{util.DOCK_USER}/{meeting.get_tutor_id()}/{util.DOCK_LESSON}/{meeting.get_lesson_id()}/{util.DOCK_MEETINGS}")
        ref_update = self.db.collection(f"{util.DOCK_USER}/{meeting.get_tutor_id()}/{util.DOCK_LESSON}/{meeting.get_lesson_id()}/{util.DOCK_MEETINGS}").document(f"{meeting.get_meeting_id()}")
        update_data = {"dateEnd": "", "dateStart": "",
                       "endDateTime": meeting.get_end_date_time(),
                       "inPerson": meeting.get_in_person(), "lessonId": meeting.get_lesson_id(),
                       "meetingId": meeting.get_meeting_id(),
                       "startDateTime": meeting.get_start_date_time(),
                       "studentId": meeting.get_student_id(),
                       "timeEnd": "", "timeStart": "",
                       "tutorId": meeting.get_tutor_id(), "zoom": meeting.get_zoom()}
        ref_update.update(update_data)

    def check_overlap(self, start_data, end_data, student, tutor, UID):
        # This function checks if the student or tutor tries to sign up into class
        # But overlaps with a different one.

        # Converting to datetime
        start_time = util.str_to_datetime(start_data)
        end_time = util.str_to_datetime(end_data)

        # If the class overlaps at the 10 min range allow only the student to assign to the class?

        # Collection group is like an admin , it crawls all the collection without us knowing the path
        # And returns all of the data which fits our condition
        overlap_list = []
        notifiy_template = ""
        if student != "no" and tutor == "no":
            date_data = self.get_parsed_timestamp_list(student, UID)
            return self.check_list_overlap(date_data,start_time,end_time)

        elif tutor != "no" and student == "no":
            date_data = self.get_parsed_timestamp_list(tutor, UID)
            return self.check_list_overlap(date_data,start_time,end_time)

        elif tutor != "no" and student != "no":
            student_data = self.get_parsed_timestamp_list(student, UID)
            tutor_data = self.get_parsed_timestamp_list(tutor, UID)
            student_overlap = self.check_list_overlap(student_data,start_time,end_time)
            tutor_overlap = self.check_list_overlap(tutor_data,start_time,end_time)
            if student_overlap or tutor_overlap:
                return True
            else:
                return False
        return False

    def check_list_overlap(self, date_data, start_time, end_time):
        try:
            for date in date_data:
                print("start: " ,date[0]," end: ",date[1]," start_time: ",start_time," end_time: ",end_time)
                if date[0] < start_time < date[1] or date[0] < end_time < date[1] or (start_time<=date[0] and end_time>=date[1]):
                    return True
        except:
            print("list is empty")
        return False

    def get_parsed_timestamp_list(self,role,UID):
        meetings = self.db.collection_group('meetings').where(role, "==",UID).stream()
        parsed_list = []
        for meet in meetings:
            meeting_date_start = str(meet.to_dict()["startDateTime"])
            meeting_date_end = str(meet.to_dict()["endDateTime"])
            # The minus 3 is to delete the seconds , and the "+" is to delete the time zone.
            meeting_date_start = util.str_to_datetime(meeting_date_start[0:meeting_date_start.find("+") - 3])
            meeting_date_end = util.str_to_datetime(meeting_date_end[0:meeting_date_end.find("+") - 3])
            parsed_list.append((meeting_date_start, meeting_date_end))
        return parsed_list

    def json_to_meeting(self,meeting_data)->Meeting:
        print(meeting_data)
        meeting = Meeting(meeting_data["lessonId"],meeting_data["startDateTime"],
                          meeting_data["endDateTime"].replace(tzinfo=None).strftime("%Y-%m-%d %H:%M"),meeting_data["tutorId"],
                          meeting_data["zoom"],meeting_data["inPerson"])
        meeting.set_meeting_id(meeting_data["meetingId"])
        meeting.set_student_id(meeting_data["studentId"])
        return meeting








