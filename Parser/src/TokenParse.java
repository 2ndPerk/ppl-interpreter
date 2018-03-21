import java.util.List;

/**
 * Created by Perk on 2018-03-19.
 */
public class TokenParse {

    private List<Token> tokens;
    private int curr = 0;
    public TokenParse(List<Token> tokens){
        this.tokens = tokens;
    }


    public String parseProgram(int ind){
        String retString = "Program \n";
        while(++curr < tokens.size()) {
            retString += parseTopDef(ind + 1);
        }

        return retString;
    }

    private String parseTopDef(int ind){

        Token c = tokens.get(curr);
        String ret =  "TopDef \n";

        System.out.println(c.getIntType());

        if(c.getIntType() == 5){
            ret = ret + this.parseFunDef(ind + 1);
        }else if(c.getIntType() == 6){
            ret = ret + this.parseCofunDef(ind + 1);
        }else if(c.getIntType() == 7){
            ret = ret + this.parseTypeDef(ind + 1);
        }else{
            System.out.println("ERROR");
        }

        ret = indent(ind) + ret;

        return ret;
    }
    private String parseFunDef(int ind){
        Token c = tokens.get(curr);
        String ret = c.toString() + "\n";
        ret = indent(ind) + ret;
        //System.out.println(c.getIntType() + " fundef x");
        c = tokens.get(++curr);
        //System.out.println(c.getIntType() + " fundef");
        if(c.getIntType() != 9){
            System.out.println("ERROR fundef");
        }else{
            ret = ret +indent(ind + 1)+c.toString()+ "\n";
        }
        int t = tokens.get(++curr).getIntType();
        if(t < 13 || t == 14) {
            ret = ret + this.parseBody(ind + 1);
        }
        c = tokens.get(++curr);
        //System.out.println(c.getIntType() + " fundef end");
        if(c.getIntType() != 16){
            System.out.println("Error expected '.'");
        }
        ret = indent(ind) + "FunDef\n" + ret;
        return ret;
    }
    private String parseCofunDef(int ind){
        Token c = tokens.get(curr);
        String ret = c.toString() + "\n";
        ret = indent(ind) + ret;
        c = tokens.get(++curr);
        if(c.getIntType() != 9){
            System.out.println("ERROR cofundef");
        }else{
            String r = c.toString() + "\n";
            ret = ret +indent(ind + 1)+ c.toString()+"\n";
        }
        int t = tokens.get(++curr).getIntType();
        if(t < 13 || t == 14) {
            ret = ret + this.parseBody(ind + 1);
        }
        c = tokens.get(++curr);
        if(c.getIntType() != 16){
            System.out.println("Error expected '.'");
        }
        ret = indent(ind) + "CofunDef\n" + ret;
        return ret;
    }
    private String parseTypeDef(int ind){
        //type
        Token c = tokens.get(curr);
        String ret = indent(ind) + c.toString() + "\n";
        //id
        c = tokens.get(++curr);
        if(c.getIntType() == 9){
            ret = ret + indent(ind + 1) + c.toString() + "\n";
        }else{
            System.out.println("Error expected ID got "+c.getType());
        }
        //Fields
        if(tokens.get(++curr).getIntType() == 9){
            ret = ret + parseFields(ind);
        }
        return ret;
    }
    private String parseFields(int ind){
        String ret = "";
        Token c = tokens.get(curr);
        if(c.getIntType() == 9){
            ret = ret + indent(ind + 1) + c.toString() + "\n";
        }else{
            System.out.println("Error expected ID got "+c.getType());
        }
        //Fields
        if(tokens.get(++curr).getIntType() == 9){
            ret = ret + parseFields(ind);
        }
        return ret;
    }
    private String parseBody(int ind){
        String ret = "";
        ret = ret + this.parseStatement(ind + 1);
        int t = tokens.get(++curr).getIntType();
        System.out.println(t + " Body");
        if(t < 13 || t == 14) {
            ret = ret + this.parseBody(ind + 1);
        }
        ret = indent(ind) + "Body\n" + ret;
        return ret;
    }
    private String parseStatement(int ind){
        String ret = "";
        Token c = tokens.get(curr);
        int t = c.getIntType();
        System.out.println(t + " Statement");
        if(t == 5){
            ret = ret + this.parseFunDef(ind + 1);
        }else if(t == 6){
            ret = ret + this.parseCofunDef(ind + 1);
        }else if(t == 7){
            ret = ret + this.parseTypeDef(ind + 1);
        }else if(t < 5 || (t > 8 || t < 12)){
            ret = ret + this.parseSimpleStatement(ind + 1);
        }else if(t == 8){
            ret = ret + this.parseVarDef(ind + 1);
        }else if( t == 12 || t == 14){
            parseLambda(ind + 1);
        }
        String temp = "Statement\n";

        temp = indent(ind) + temp;
        ret = temp + ret;
        return ret;
    }
    private String parseVarDef(int ind){
        String ret = indent(ind) + "VarDef\n";
        Token c = tokens.get(curr);

        ret = ret + indent(ind + 1) + c.toString() + "\n";
        c = tokens.get(++curr);
        if(c.getIntType() != 9){
            ret = ret + indent(ind + 1) + c.toString() + "\n";
        }else{
            System.out.println("Error expected ID got "+c.getType());
        }
        return ret;
    }
    private String parseSimpleStatement(int ind){
        Token c = tokens.get(curr);
        String ret = c.toString()+"\n";
        ret = indent(ind) + ret;
        return ret;
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

    private String indent(int ind){
        String ret = "";
        for(int i = 0; i < ind; i++){
            ret = "  " + ret;
        }
        return ret;
    }
}
