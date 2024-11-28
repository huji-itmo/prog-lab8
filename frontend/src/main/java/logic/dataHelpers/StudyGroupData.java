package logic.dataHelpers;

import dataStructs.Country;
import dataStructs.FormOfEducation;
import dataStructs.Semester;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudyGroupData {
    long id;
    String name;
    double coordinatesX;
    double coordinatesY;
    LocalDate creationDate;
    Integer studentsCount;
    long averageMark;
    FormOfEducation formOfEducation;
    Semester semester;

    String groupAdminName;
    LocalDate groupAdminBirthday;
    double groupAdminWeight;
    String groupAdminPassportID;
    Country groupAdminNationality;

    String owner;
}
