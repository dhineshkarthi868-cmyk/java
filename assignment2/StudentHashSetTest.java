import java.util.*;

public class StudentHashSetTest {
    static final class Student {
        private final String regNo;
        private final String name;
        Student(String regNo, String name) { this.regNo=regNo; this.name=name; }
        @Override public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Student)) return false;
            Student other=(Student)obj;
            return Objects.equals(regNo, other.regNo);
        }
        @Override public int hashCode() { return Objects.hash(regNo); }
        @Override public String toString() { return name + "(" + regNo + ")"; }
    }
    // Separate mutable example demonstrates why key fields must remain stable.
    static final class MutableStudent {
        String regNo;
        MutableStudent(String regNo) { this.regNo=regNo; }
        @Override public boolean equals(Object obj) {
            return obj instanceof MutableStudent && Objects.equals(regNo, ((MutableStudent)obj).regNo);
        }
        @Override public int hashCode() { return Objects.hash(regNo); }
    }
    public static void main(String[] args) {
        Set<Student> same=new HashSet<>();
        Student a=new Student("CSE101","Asha"); same.add(a); same.add(a);
        System.out.println("Test 1 - same reference set size: " + same.size());

        Set<Student> duplicates=new HashSet<>();
        duplicates.add(new Student("CSE102","Bala"));
        duplicates.add(new Student("CSE102","Bala"));
        System.out.println("Test 2 - distinct equal objects set size: " + duplicates.size());

        Set<MutableStudent> mutated=new HashSet<>();
        MutableStudent m=new MutableStudent("CSE103"); mutated.add(m);
        m.regNo="CSE999";
        System.out.println("Test 3 - contains after key mutation: " + mutated.contains(m));
        System.out.println("Test 3 - size after mutation: " + mutated.size());
        System.out.println("Recommended: immutable identity field (final regNo) while stored in HashSet.");
    }
}
