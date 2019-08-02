package busy.gui.flashmessagemanager;

public class FlashMessageManagerMessageContents {
    private FlashMessageManagerMessageContents() throws Exception{
        throw new Exception(String.format("Sorry, there is no %s instance for you!", this.getClass()));
    }

    public static final String ALREADY_LOGGED = "Jesteś już zalogowany!";
    public static final String INVALID_LOGIN_OR_PASSWORD = "Wpisano błędny login lub hasło";
    public static final String LOGGED = "Zalogowano jako: ";
    public static final String EMAIL_TAKEN = "Na ten email zostało już założone konto";
    public static final String LOGIN_TAKEN = "Ten login jest zajęty";
    public static final String DIFFERENT_PASSWORDS = "Hasła się różnią";
}
