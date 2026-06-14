package dto;

public record UserEvent(
        Operation operation,
        String email
) {
}
