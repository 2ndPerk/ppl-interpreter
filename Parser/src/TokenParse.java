import java.util.List;

/**
 * Created by Perk on 2018-03-19.
 */
public class TokenParse {

    List<Token> tokens;
    int curr = 0;
    public TokenParse(List<Token> tokens){
        this.tokens = tokens;
    }


    public String parseProgram(int ind){
        String retString = "Program \n";
        while(curr++ < tokens.size() - 1) {
            retString += parseTopDef(ind + 1);
        }

        return retString;
    }

    private String parseTopDef(int ind){
        String ret = tokens.get(curr).toString() + "\n";
        for(int i = 0; i < ind; i++){
            ret = "   " + ret;
        }

        return ret;
    }
    private String parseFunDef(int ind){

        return "";
    }
    private String parseCofunDef(int ind){

        return "";
    }
    private String parseTypeDef(int ind){

        return "";
    }
    private String parseFields(int ind){

        return "";
    }
    private String parseBody(int ind){

        return "";
    }
    private String parseStatement(int ind){

        return "";
    }
    private String parseVarDef(int ind){

        return "";
    }
    private String parseSimpleStatement(int ind){

        return "";
    }
    private String parseLambda(int ind){

        return "";
    }
    private String parseFunLambda(int ind){

        return "";
    }
    private String parseCofunLambda(int ind){

        return "";
    }
}
