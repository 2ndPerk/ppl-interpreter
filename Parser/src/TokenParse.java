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

        Token c = tokens.get(curr);
        String ret = c.toString() + "\n";

        if(c.getIntType() == 5){
            this.parseFunDef(ind + 1);
        }else if(c.getIntType() == 6){
            this.parseCofunDef(ind + 1);
        }else if(c.getIntType() == 7){
            this.parseTypeDef(ind + 1);
        }else{
            System.out.println("ERROR");
        }

        for(int i = 0; i < ind; i++){
            ret = "   " + ret;
        }

        return ret;
    }
    private String parseFunDef(int ind){
        Token c = tokens.get(curr);
        String ret = c.toString() + "\n";
        for(int i = 0; i < ind; i++){
            ret = "   " + ret;
        }
        c = tokens.get(curr++);
        if(c.getIntType() != 9){
            System.out.println("ERROR");
        }else{
            String r = c.toString() + "\n";
            for(int i = 0; i < ind + 1; i++){
                r = "   " + r;
            }
            ret = ret + r;
        }

        return ret;
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
