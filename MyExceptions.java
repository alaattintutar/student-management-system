// Custom exception class to handle specific errors in the Student Management System
public class MyExceptions extends Exception {

    // Exception for handling invalid data or types
    public static class InvalidError extends Exception {

        // Constructor that takes an error message as a parameter
        public InvalidError(String message) {
            super(message);
        }
    }

    // Exception for handling cases where an expected entity or person does not exist
    public static class NonExistedError extends Exception {

        // Constructor that takes an error message as a parameter
        public NonExistedError(String message) {
            super(message);
        }
    }
}

