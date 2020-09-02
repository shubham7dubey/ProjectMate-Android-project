public class UserProfile {


    public  String full_name,course,department,mobile,roll;

    public UserProfile(){

    }
    public UserProfile(String full_name,String course,String department,String mobile,String roll){
        this.full_name=full_name;
        this.course  =course ;
        this.department  =department ;
        this.mobile  =mobile ;
        this.roll=roll;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }
}
