import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Riley Newson on 18-03-06.
 */
public class Scanner {
    public static List<Token> getTokens(String[] args) {
        try {
            List<Token> tokenized = new ArrayList<Token>(); // List for finalized tokens
            String fileName = "toy.txt"; // Default file for testing
            int lineCount = 0;

            if (args.length > 0 && args[0] != null){
                fileName = args[0]; // Grab filename from command line
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));

            try {
                String line;

                // Getting each line from text file and tokenizing each word
                while ((line = br.readLine()) != null) {
                    lineCount++;
                    List<String> words = new ArrayList<String>(Arrays.asList(line.split(" ")));
                    words.removeAll(Arrays.asList("", null)); // clear out tabs/empty space
                    tokenized = tokenize(words, tokenized, lineCount);
                }
            } finally {
                br.close();
            }

            //String output = "";
            List <Token> tokenList = new ArrayList<Token>();

            // Taken fully tokenized string and outputing.
            for (int i = tokenized.size() - 1; i >= 0; i--){
                if(tokenized.get(i) != null) {
                    //output += tokenized.get(i).toString() + " ";
                    tokenList.add(tokenized.get(i));
                }
            }

            //System.out.println(output);
            return tokenList;

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static List<Token> tokenize(List<String> words, List<Token> tokenized, int lineCount) throws TokenizedException{
        String word;
        int wordCount = 0;
        for (int i = 0; i < words.size(); i++){
            word = words.get(i);
            wordCount++;
            if(word.length() > 2 &
                    word.charAt(0) == '"' &
                    word.charAt(word.length() - 1) != '"'){ //required for possible "test \" test" strings
                char endChar = word.charAt(word.length() - 1);
                char secEndChar;
                while(endChar != '"') {
                    i++;
                    if (i >= words.size()) { //String has not completed before line finish, throw error.
                        throw new TokenizedException("Token does not exist at value: "
                                + word + ", at Line: " + lineCount
                                + " Word: " + wordCount
                        );
                    }
                    word += " " + words.get(i);
                    endChar = word.charAt(word.length() - 1);
                    secEndChar = word.charAt(word.length() - 2);

                    if(endChar == '"' & secEndChar == '\\'){
                        endChar = 'a'; // 'a' is used as a place holder to have loop continue.
                    }
                }

                tokenized.add(0, new Token(Type.STRING, word));
            } else if(word.contentEquals("//"))
                i = words.size();
            else {
                Token token = getToken(word);
                if(token == null){
                    throw new TokenizedException("Token does not exist at value: "
                            + word + ", at Line: " + lineCount
                            + " Word: " + wordCount
                    );
                }
                tokenized.add(0, token);
            }

        }
        return tokenized;
    }

    public static Token getToken(String word){
        int type = -1;
        switch (word) {
            case "fun":
                type = Type.FUN;
                break;
            case ".":
                type = Type.END;
                break;
            case "[":
                type = Type.LAMBDABEGIN;
                break;
            case "]":
                type = Type.LAMBDAEND;
                break;
            case "[|":
                type = Type.COFUNLAMBDASTART;
                break;
            case "|]":
                type = Type.COFUNLAMBDAEND;
                break;
            case "cofun":
                type = Type.COFUN;
                break;
            case "type":
                type = Type.TYPE;
                break;
            case "var":
                type = Type.VAR;
                break;
            default: // Regex Cases:
                if (word.matches("[+-]?[0-9]+|0x[0-9a-fA-F]+|0b[01]+")) { //int
                    type = Type.INT;
                    break;
                } else if(word.matches("[+-]?([0-9]*\\.)?[0-9]+([Ee][+-]?[0-9]+)?")){ //float
                    type = Type.FLOAT;
                    break;
                }else if(word.matches("'[^\\']'|'\\.'")){ //char
                    type = Type.CHAR;
                    break;
                }else if(word.matches("\"([^\\\"]|\\.)*\"")){ //String
                    type = Type.STRING;
                    break;
                }else if(word.matches("[-+%]|\\.[^0-9].*|[-+][^.0-9].*|[^-+.:%'\"0-9].*")){ //id
                    type = Type.ID;
                    break;
                }else if(word.matches(":.+")){ //symbol
                    type = Type.SYMBOL;
                    break;
                }else if(word.matches("%[-+%]|%\\.[^0-9].*|%[-+][^.0-9].*|%[^-+.:%'\"0-9].*")){ //reference
                    type = Type.REFERENCE;
                    break;
                }
        }

        if(type != -1)
            return new Token(type, word);

        return null; // Token not found, will be caught by error handler.
    }

}
