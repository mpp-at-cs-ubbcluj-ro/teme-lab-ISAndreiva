import internal.andreiva.User;
import internal.andreiva.UserValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class TestValidation
{
    @Test
    @DisplayName("Test validation success")
    public void testValidationSuccess()
    {
        var user = new User("John", "Doe", "johndoe", "john_doe@gmail.com", "password");
        user.setId(UUID.randomUUID());

        try
        {
            UserValidator.getInstance().validate(user);
            assert true;
        }
        catch (Exception e)
        {
            assert false;
        }

    }

    @Test
    @DisplayName("Test validation fail")
    public void testValidationFail()
    {
        var user = new User("John", "Doe", "johndoe", "@a.com", "password");
        user.setId(UUID.randomUUID());

        try
        {
            UserValidator.getInstance().validate(user);
            assert false;
        }
        catch (Exception e)
        {
            assert true;
        }
    }
}
