package gr.aueb.cf.schoolapp.model;

public class Meeting {
    private Integer teacherId;
    private Integer studentId;
    private String meetingRoom;
    private String meetingDate;

    public Meeting() {
    }

    public Meeting(Integer teacherId, Integer studentId, String meetingRoom, String meetingDate) {
        this.teacherId = teacherId;
        this.studentId = studentId;
        this.meetingRoom = meetingRoom;
        this.meetingDate = meetingDate;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getMeetingRoom() {
        return meetingRoom;
    }

    public void setMeetingRoom(String meetingRoom) {
        this.meetingRoom = meetingRoom;
    }

    public String getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "teacherId=" + teacherId +
                ", studentId=" + studentId +
                ", meetingRoom='" + meetingRoom + '\'' +
                ", meetingDate='" + meetingDate + '\'' +
                '}';
    }
}
