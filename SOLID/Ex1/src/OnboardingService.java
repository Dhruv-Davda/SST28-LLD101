import java.util.*;

public class OnboardingService {
    private final StudentStore db;
    private final InputParser parser;
    private final StudentValidator validator;
    private final OnboardingPrinter printer;

    public OnboardingService(StudentStore db, InputParser parser, StudentValidator validator, OnboardingPrinter printer) { 
        this.db = db; this.parser = parser;
        this.validator = validator; this.printer = printer;
    }

    // Intentionally violates SRP: parses + validates + creates ID + saves + prints.
    public void registerFromRawInput(String raw) {
        
        printer.printInput(raw);

        RawStudentData data = parser.parse(raw);

        // validation inline, printing inline
        List<String> errors = validator.validate(data);

        if (!errors.isEmpty()) {
            printer.printErrors(errors);
            return;
        }

        String id = IdUtil.nextStudentId(db.count());
        StudentRecord rec = new StudentRecord(id, data.name, data.email, data.phone, data.program);

        db.save(rec);

        printer.printSuccess(id, db.count(), rec);
    }
}
