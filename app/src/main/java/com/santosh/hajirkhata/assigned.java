package com.santosh.hajirkhata;

public class assigned {
    String code,faculty,program,semester;
    public assigned(){}

    public assigned(String code, String faculty, String program, String semester) {
        this.code = code;
        this.faculty = faculty;
        this.program = program;
        this.semester = semester;
    }

    public String getCode() {
        return code;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getProgram() {
        return program;
    }

    public String getSemester() {
        return semester;
    }
}
