import java.util.Scanner;

public class LabActivity1EmployeeInformationSystem {
    public static void main(String[] args ){
        
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter your age: ");
        int age = scanner.nextInt();

        System.out.print("Enter hours worked: ");
        float hoursWorked = scanner.nextFloat();

        System.out.print("Enter your hourly wage: ");
        float hourlyWage = scanner.nextFloat();

        float DailySalary = hoursWorked * hourlyWage;
        
        System.out.println();
        System.out.println("Employee Information");
        System.out.println("--------------------");
        System.out.println("Full name    : " + firstName + " " + lastName);
        System.out.println("Age          : " + age);
        System.out.println("Daily Salary : PHP " + DailySalary);

        scanner.close();

    }
}
