package HospitalManagementSystem;

import java.util.*;
import java.text.*;

public class ManagementSystem {

    static class Patient {
        String id;
        String name;
        int age;
        String gender;
        String ailment;
        String medicalHistory;

        public Patient(String id, String name, int age, String gender, String ailment, String medicalHistory) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.gender = gender;
            this.ailment = ailment;
            this.medicalHistory = medicalHistory;
        }

        @Override
        public String toString() {
            return "Patient [ID=" + id + ", Name=" + name + ", Age=" + age + ", Gender=" + gender + ", Ailment=" + ailment + ", Medical History=" + medicalHistory + "]";
        }
    }

    static class Staff {
        String id;
        String name;
        String role;

        public Staff(String id, String name, String role) {
            this.id = id;
            this.name = name;
            this.role = role;
        }

        @Override
        public String toString() {
            return "Staff [ID=" + id + ", Name=" + name + ", Role=" + role + "]";
        }
    }

    static class Bill {
        String billId;
        String patientId;
        double amount;

        public Bill(String billId, String patientId, double amount) {
            this.billId = billId;
            this.patientId = patientId;
            this.amount = amount;
        }

        @Override
        public String toString() {
            return "Bill [BillID=" + billId + ", PatientID=" + patientId + ", Amount=" + amount + "]";
        }
    }

    static class InventoryItem {
        String id;
        String name;
        int quantity;
        double price;

        public InventoryItem(String id, String name, int quantity, double price) {
            this.id = id;
            this.name = name;
            this.quantity = quantity;
            this.price = price;
        }

        @Override
        public String toString() {
            return "InventoryItem [ID=" + id + ", Name=" + name + ", Quantity=" + quantity + ", Price=" + price + "]";
        }
    }

    static List<Patient> patients = new ArrayList<>();
    static List<Staff> staffMembers = new ArrayList<>();
    static List<Bill> bills = new ArrayList<>();
    static List<InventoryItem> inventory = new ArrayList<>();

    static Map<String, List<String>> doctorSpecializations = new HashMap<>();

    public static void initializeDoctorSpecializations() {
        doctorSpecializations.put("fever", Arrays.asList("Dr. Smith", "Dr. Taylor"));
        doctorSpecializations.put("fracture", Arrays.asList("Dr. Brown", "Dr. Lee"));
        doctorSpecializations.put("diabetes", Arrays.asList("Dr. Wilson", "Dr. Davis"));
        doctorSpecializations.put("heart disease", Arrays.asList("Dr. Garcia", "Dr. Martinez"));
    }

    public static boolean validateDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            Date inputDate = sdf.parse(date);
            Date today = sdf.parse(sdf.format(new Date()));
            return !inputDate.before(today);
        } catch (ParseException e) {
            return false;
        }
    }

    public static void registerPatient(Scanner scanner) {
        int patientId = 0;
        while (true) {
            System.out.print("Enter Patient ID : ");
            if (scanner.hasNextInt()) {
                patientId = scanner.nextInt();
                if (patientId > 0) {
                    scanner.nextLine();
                    break;
                } else {
                    System.out.println("Patient ID must be a positive number.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next();
            }
        }

        String name;
        while (true) {
            System.out.print("Enter Name : ");
            name = scanner.nextLine();
            if (name.length() >= 4 && name.matches("[a-zA-Z]+")) {
                break;
            } else {
                System.out.println("Name must be at least 4 characters and contain only letters.");
            }
        }

        int age;
        while (true) {
            System.out.print("Enter Age: ");
            if (scanner.hasNextInt()) {
                age = scanner.nextInt();
                if (age > 0) {
                    scanner.nextLine();
                    break;
                } else {
                    System.out.println("Age must be a positive number.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next();
            }
        }

        String gender;
        while (true) {
            System.out.print("Enter Gender (Male/Female): ");
            gender = scanner.nextLine().trim();
            if (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female")) {
                break;
            } else {
                System.out.println("Please enter a valid gender (Male/Female).");
            }
        }

        System.out.print("Enter Ailment: ");
        String ailment = scanner.nextLine().toLowerCase();
        System.out.print("Enter Medical History: ");
        String medicalHistory = scanner.nextLine();

        patients.add(new Patient(String.valueOf(patientId), name, age, gender, ailment, medicalHistory));

        System.out.println("Patient registered successfully. Details: ");
        System.out.println(patients.get(patients.size() - 1));
    }

    public static void scheduleAppointment(Scanner scanner) {
        System.out.print("Enter Patient ID for appointment: ");
        String patientId = scanner.nextLine();
        boolean patientExists = false;

        for (Patient p : patients) {
            if (p.id.equals(patientId)) {
                patientExists = true;
                break;
            }
        }

        if (patientExists) {
            System.out.println("Select a doctor for appointment:");
            System.out.println("1) Dr. Taylor");
            System.out.println("2) Dr. John");
            System.out.println("0) Default Doctor");

            int doctorChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            String doctor = "Dr. Default";
            if (doctorChoice == 1) {
                doctor = "Dr. Taylor";
            } else if (doctorChoice == 2) {
                doctor = "Dr. John";
            }

            // Validate date
            System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
            String date = scanner.nextLine();

            if (validateDate(date)) {
                // Set the default time for the appointment (e.g., 10:00 AM)
                String defaultTime = "10:00 AM";

                // Schedule the appointment
                System.out.println("\nAppointment successfully scheduled!");
                System.out.println("Patient ID: " + patientId);
                System.out.println("Doctor: " + doctor);
                System.out.println("Appointment Date: " + date);
                System.out.println("Appointment Time: " + defaultTime);
            } else {
                System.out.println("Invalid date. Only today or future dates are accepted.");
            }
        } else {
            System.out.println("Patient not found with ID " + patientId);
        }
    }

    public static void generateBill(Scanner scanner) {
        System.out.print("Enter Patient ID for bill: ");
        String patientId = scanner.nextLine();
        boolean patientExists = false;

        for (Patient p : patients) {
            if (p.id.equals(patientId)) {
                patientExists = true;
                break;
            }
        }

        if (patientExists) {
            System.out.print("Enter Amount for Bill: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            String billId = "B" + UUID.randomUUID().toString();
            bills.add(new Bill(billId, patientId, amount));
            System.out.println("Bill generated for patient ID " + patientId + " with Bill ID " + billId);
        } else {
            System.out.println("Patient not found with ID " + patientId);
        }
    }

    public static void addInventoryItem(Scanner scanner) {
        System.out.print("Enter Inventory Item ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Item Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Item Quantity: ");
        int quantity = scanner.nextInt();
        System.out.print("Enter Item Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        inventory.add(new InventoryItem(id, name, quantity, price));
        System.out.println("Inventory Item added successfully.");
    }

    public static void addStaffMember(Scanner scanner) {
        System.out.print("Enter Staff ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Staff Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Staff Role: ");
        String role = scanner.nextLine();
        staffMembers.add(new Staff(id, name, role));
        System.out.println("Staff Member added successfully.");
    }

    public static void displayPatients() {
        System.out.println("\n--- Patients List ---");
        for (Patient p : patients) {
            System.out.println(p);
        }
    }

    public static void displayBillings() {
        System.out.println("\n--- Billings List ---");
        for (Bill b : bills) {
            System.out.println(b);
        }
    }

    public static void displayInventory() {
        System.out.println("\n--- Inventory List ---");
        for (InventoryItem item : inventory) {
            System.out.println(item);
        }
    }

    public static void displayStaff() {
        System.out.println("\n--- Staff List ---");
        for (Staff s : staffMembers) {
            System.out.println(s);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        initializeDoctorSpecializations();

        while (true) {
            System.out.println("\n--- Hospital Management System ---");
            System.out.println("1. Register Patient");
            System.out.println("2. Schedule Appointment");
            System.out.println("3. Generate Bill");
            System.out.println("4. Add Inventory Item");
            System.out.println("5. Add Staff Member");
            System.out.println("6. Display Patients");
            System.out.println("7. Display Billings");
            System.out.println("8. Display Inventory");
            System.out.println("9. Display Staff");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerPatient(scanner);
                    break;
                case 2:
                    scheduleAppointment(scanner);
                    break;
                case 3:
                    generateBill(scanner);
                    break;
                case 4:
                    addInventoryItem(scanner);
                    break;
                case 5:
                    addStaffMember(scanner);
                    break;
                case 6:
                    displayPatients();
                    break;
                case 7:
                    displayBillings();
                    break;
                case 8:
                    displayInventory();
                    break;
                case 9:
                    displayStaff();
                    break;
                case 10:
                    System.out.println("Exiting the system.");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}



