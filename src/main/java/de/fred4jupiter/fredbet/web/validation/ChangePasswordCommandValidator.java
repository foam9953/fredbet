package de.fred4jupiter.fredbet.web.validation;

import de.fred4jupiter.fredbet.web.profile.ChangePasswordCommand;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validates the new password against the password repeat.
 *
 * @author michael
 */
public class ChangePasswordCommandValidator implements ConstraintValidator<PasswordChangeConstraint, ChangePasswordCommand> {

    private static final Logger LOG = LoggerFactory.getLogger(ChangePasswordCommandValidator.class);

    @Override
    public boolean isValid(ChangePasswordCommand value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (!value.getNewPassword().equals(value.getNewPasswordRepeat())) {
            context.buildConstraintViolationWithTemplate("{msg.passwordChange.passwordMismatch}")
                    .addPropertyNode("newPassword").addConstraintViolation().disableDefaultConstraintViolation();
            LOG.error("newPassword and newPasswordRepeat are different");
            return false;
        }

        if (value.getOldPassword().equals(value.getNewPassword())) {
            context.buildConstraintViolationWithTemplate("{msg.passwordChange.oldAndNewPasswordAreSame}")
                    .addPropertyNode("newPassword").addConstraintViolation().disableDefaultConstraintViolation();
            LOG.error("newPassword and newPasswordRepeat are different");
            return false;
        }

        return true;
    }

}
