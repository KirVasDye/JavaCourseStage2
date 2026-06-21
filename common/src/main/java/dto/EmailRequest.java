package dto;

public record EmailRequest(
        String email,
        String subject,
        String text
) {
}
