import java.util.List;
import java.util.Stack;

/**
 * Created by Perk on 2018-03-19.
 */
public class Parser {
    public static void main(String[] args) {
        List<Token> tokens = Scanner.getTokens(args);

        for( int i = 0; i < tokens.size(); i++){
            System.out.println(tokens.get(i).toString());
        }

        TokenParse parser = new TokenParse(tokens);
        System.out.println(parser.parseProgram(0));
    }
}
