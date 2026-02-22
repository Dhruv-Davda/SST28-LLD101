import java.util.Optional;

public class CreditsRule implements EligibilityRule {
    public Optional<String> check(StudentProfile s) {
        if (s.earnedCredits < 20) return Optional.of("credits below 20");
        return Optional.empty();
    }
}
