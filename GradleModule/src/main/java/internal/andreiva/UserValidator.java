package internal.andreiva;

import internal.andreiva.User;
import org.apache.commons.validator.routines.EmailValidator;

/**
 * User validator
 */
public class UserValidator
{
    public void validate(User entity) throws Exception
    {
        if (entity.getId() == null)
        {
            throw new Exception("ID must not be null.");
        }
        if (entity.getFirstName().isEmpty() || entity.getFirstName().matches(".*[0-9].*"))
        {
            throw new Exception("First name must not be empty and must not contain digits.");
        }
        if (entity.getLastName().isEmpty() || entity.getLastName().matches(".*[0-9].*"))
        {
            throw new Exception("Last name must not be empty and must not contain digits.");
        }
        if (entity.getUsername().isEmpty())
        {
            throw new Exception("Username must not be empty.");
        }
        if (!EmailValidator.getInstance().isValid(entity.getEmail()))
        {
            throw new Exception("Email is not valid.");
        }
        if (entity.getPasswordHash().isEmpty())
        {
            throw new Exception("Password must not be empty.");
        }

    }
    private UserValidator() {}
    private static final UserValidator instance = new UserValidator();
    public static UserValidator getInstance()
    {
        return instance;
    }

}
