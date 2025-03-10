package internal.andreiva;

public class Main
{
    public static void main(String[] args)
    {
        var randomUser = new User("John", "Doe", "johndoe", "johndoe@gmail.com", "password");
        var randomUserFail = new User("John", "Doe", "johndoe", "@a.com", "password");
        System.out.println(randomUser);
        System.out.println(randomUserFail);
    }
}