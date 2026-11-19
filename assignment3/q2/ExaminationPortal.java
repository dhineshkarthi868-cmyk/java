class ExamException extends Exception {
    ExamException(String message) { super(message); }
    ExamException(String message, Throwable cause) { super(message, cause); }
}
class WindowClosedException extends ExamException {
    WindowClosedException(String message) { super(message); }
}
class CourseRegistrationException extends ExamException {
    CourseRegistrationException(String message) { super(message); }
}
class SubmissionStorageException extends ExamException {
    SubmissionStorageException(String message, Throwable cause) { super(message, cause); }
}

class SubmissionStorage {
    void save(String fileName, long sizeKb) throws SubmissionStorageException {
        try {
            if (sizeKb > 500) throw new java.io.IOException("Disk quota exceeded");
            System.out.println("Saved " + fileName);
        } catch (java.io.IOException e) {
            throw new SubmissionStorageException("Could not store " + fileName, e);
        }
    }
}
class ExamService {
    private final SubmissionStorage storage = new SubmissionStorage();

    void submit(String file, long sizeKb, boolean registered, boolean windowOpen)
            throws ExamException {
        if (!windowOpen) throw new WindowClosedException("Exam submission window has closed");
        if (!registered) throw new CourseRegistrationException("Student is not registered for this course");
        storage.save(file, sizeKb);
    }
}
public class ExaminationPortal {
    public static void main(String[] args) {
        ExamService service = new ExamService();
        Object[][] tests = {
            {"answer01.pdf", 120L, true, true},
            {"answer02.pdf", 800L, true, true},
            {"answer03.pdf", 100L, false, true},
            {"answer04.pdf", 100L, true, false}
        };
        for (Object[] t : tests) {
            try {
                service.submit((String)t[0], (Long)t[1], (Boolean)t[2], (Boolean)t[3]);
            } catch (SubmissionStorageException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Cause: " + e.getCause().getMessage());
            } catch (WindowClosedException | CourseRegistrationException e) {
                System.out.println("Submission rejected: " + e.getMessage());
            } catch (ExamException e) {
                System.out.println("Exam error: " + e.getMessage());
            }
        }
    }
}
