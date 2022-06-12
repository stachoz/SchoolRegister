package schoolRegister;

import validators.Validator;

import java.util.List;
import java.util.Scanner;



public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        boolean runApp = true;

        SchoolRegister schoolRegister = new SchoolRegister();
        Validator validator = new Validator();

        do{
            System.out.println("SCHOOL REGISTER");
            System.out.println("---------------");
            System.out.println("1. Add teachers");
            System.out.println("2. Display all teachers ");
            System.out.println("3. Create Class");
            System.out.println("4. Display Classes");
            System.out.println("5. Add Students");
            System.out.println("6. Display students");
            System.out.println("7. Give grades");
            System.out.println("8. Exit");
            int choice = scan.nextInt();
            switch (choice) {
                case 1:
                    boolean addAnotherTeacher;
                    do {
                        System.out.println("Add a new teacher");
                        System.out.println("Enter a Teacher's name");
                        scan.nextLine();
                        String name = scan.next();
                        System.out.println("Enter a Teacher's surname");
                        String surname = scan.next();
                        Teacher newTeacher = new Teacher(name, surname);
                        schoolRegister.saveTeacher(newTeacher);
                        addAnotherTeacher = doSomethingNext("Add next teacher?");
                    } while (addAnotherTeacher);
                    break;
                case 2:
                    if (!schoolRegister.getTeachers().isEmpty()) {
                        System.out.println("All teachers at school");
                        schoolRegister.displayTeachers();
                    } else {
                        System.out.println("There are no teachers at school"); 
                    }
                    break;
                case 3:
                    boolean addNextClass;
                    do {
                        List<Teacher> availableTeachers = schoolRegister.avilableTeachers();
                        if (availableTeachers.isEmpty()) {
                            System.out.println("The are no available teachers.If you want create a new class you have hire new people");
                            break;
                        } else {
                            System.out.println("Enter a degree");
                            int degree = scan.nextInt();
                            System.out.println("Enter a letter");
                            char letter = scan.next().charAt(0);

                            System.out.println("Set a class's tutor");
                            int i = 1;
                            for (Teacher availableTeacher : availableTeachers) {
                                System.out.println(i + ": " + availableTeacher.getName() + " " + availableTeacher.getSurname());
                                i++;
                            }
                            Teacher selectedTeacher = availableTeachers.get(scan.nextInt() - 1);
                            SchoolClass schoolClass = new SchoolClass(degree, letter, selectedTeacher);
                            schoolRegister.saveClass(schoolClass);
                            addNextClass = doSomethingNext("Add next class");
                        }
                    } while (addNextClass);
                    break;
                case 4:
                    if (!schoolRegister.getSchoolClasses().isEmpty()) {
                        schoolRegister.displayClasses();
                    } else {
                        System.out.println("there are no classes");
                    }
                    break;
                case 5:
                    boolean addStudentToDifClass;
                    if (!schoolRegister.getSchoolClasses().isEmpty()) {
                        do {
                            boolean nextStudent = true;
                            System.out.println("Select in which class you want add a new students");
                            schoolRegister.displayClasses();
                            int selectClass = scan.nextInt();
                            SchoolClass selectedClass = schoolRegister.selectClass(selectClass);
                            scan.nextLine();
                            do {
                                System.out.println("Enter Student's name");
                                String name = scan.nextLine();
                                System.out.println("Enter Student's surname");
                                String surname = scan.nextLine();
                                selectedClass.addStudent(new Student(name, surname));
                                nextStudent = doSomethingNext("Add next student");
                            } while (nextStudent);
                            addStudentToDifClass = doSomethingNext("select different class");
                        } while (addStudentToDifClass);
                    } else {
                        System.out.println("there are no classes ate school");
                    }
                    break;
                case 6:
                    if (!schoolRegister.getSchoolClasses().isEmpty()) {
                        boolean displayDifClass;
                        do {
                            System.out.println("Select which class's students you want to display");
                            schoolRegister.displayClasses();
                            int selectClass = scan.nextInt();
                            scan.nextLine();
                            SchoolClass selectedClass = schoolRegister.selectClass(selectClass);
                            selectedClass.displayStudents();
                            displayDifClass = doSomethingNext("Display different class");
                        } while (displayDifClass);
                    } else {
                        System.out.println("there are no classes at school");
                    }
                    break;
                case 7:
                    boolean studentsFromNextClass;
                    if (!schoolRegister.getSchoolClasses().isEmpty()) {
                        do {
                            System.out.println("Select a class");
                            schoolRegister.displayClasses();
                            int userInput = scan.nextInt();
                            SchoolClass selectedClass = schoolRegister.selectClass(userInput);
                            List<Student> students = selectedClass.getStudents();
                            if (!students.isEmpty()) {
                                for (Student student : students) {
                                    if (student.getGrade() == null) {
                                        System.out.println(student.getName() + " " + student.getSurname());
                                        boolean canNotSaveGrade = true;
                                        while (canNotSaveGrade) {
                                            System.out.println("enter a note (1-6)");
                                            int note = scan.nextInt(); 
                                            boolean isValid = validator.isInRangeValidator(1, 6, note);
                                            if (isValid) {
                                                student.setGrade(note);
                                                canNotSaveGrade = false;
                                            } else {
                                                System.out.println("Grade should be in the range of 1 to 6");
                                            }
                                        }
                                    }else {
                                        System.out.println("Students have already a grade");
                                    }
                                    break;
                                }
                            } else {
                                System.out.println("There are no students in this class");
                            }
                            studentsFromNextClass = doSomethingNext("Select different class");
                        }while (studentsFromNextClass) ;
                    } else{
                        System.out.println("There are no classes");
                    }
                    break;
                case 8:
                    runApp = false;
                    break;
            }
        } while (runApp);
    }

    public static boolean doSomethingNext(String s) {
        Scanner scan = new Scanner(System.in);
        System.out.println(s + " (Y/n)");
        String userInput = scan.next();
        if (userInput.equals("n")){
            return false;
        }
        return true;
    }
}
