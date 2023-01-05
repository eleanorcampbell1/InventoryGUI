package ui;


import java.io.FileNotFoundException;

public class Main {
    private static final String JSON_STORE = "./data/inventory.json";

    // Lines 15-16 attributed to Main class in CPSC210/JsonSerializationDemo

    public static void main(String[] args) {
        try {
            DepartmentStoreApp app = new DepartmentStoreApp(JSON_STORE);
            app.runDepartmentStoreApp();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot launch Department Store App: file not found");
        }
    }

}
