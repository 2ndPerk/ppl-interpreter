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
        try{
            String retString = "Program \n";
            while(++curr < tokens.size()) {
                retString += parseTopDef(ind + 1)+"Program\n";
            }

            return retString;
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    private String parseTopDef(int ind) throws TokenizedException{
        Token c = tokens.get(curr);
        String ret =  "TopDef \n";

        //Checks for fun/cofun/typedef
        if(c.getIntType() == 5){
            ret = ret + this.parseFunDef(ind + 1);
        }else if(c.getIntType() == 6){
            ret = ret + this.parseCofunDef(ind + 1);
        }else if(c.getIntType() == 7){
            ret = ret + this.parseTypeDef(ind + 1);
        }else{
            throw new TokenizedException("Error for token with value: "+ c.toString()
                                            + ", at Line: " + c.getLineCount()
                                            + ", Word: " + c.getWordCount());
        }

        ret = indent(ind) + ret;

        return ret;
    }
    private String parseFunDef(int ind) throws TokenizedException{
        Token c = tokens.get(curr);
        String ret = c.toString() + "\n";
        ret = indent(ind + 1) + ret;
        c = tokens.get(++curr);
        //makes sure second token is an id
        if(c.getIntType() != 9){
            throw new TokenizedException("Error for token with value: "+ c.toString()
            + ", at Line: " + c.getLineCount()
            + ", Word: " + c.getWordCount());
        }else{
            ret = ret +indent(ind + 1)+c.toString()+ "\n";
        }
        //checks for more Body content
        int t = tokens.get(++curr).getIntType();
        if(t < 13 || t == 14) {
            ret = ret + this.parseBody(ind + 1);
        }
        c = tokens.get(curr);
        //makes sure last token is a '.'
        if(c.getIntType() != 16){
            throw new TokenizedException("Error for token with value: "+ c.toString()
            + ", at Line: " + c.getLineCount()
            + ", Word: " + c.getWordCount());
        }
        ret = indent(ind) + "FunDef\n" + ret + indent(ind) + c.toString()+"\n";
        return ret;
    }
    //same as fundef, but for cofun
    private String parseCofunDef(int ind) throws TokenizedException {
        Token c = tokens.get(curr);
        String ret = c.toString() + "\n";
        ret = indent(ind) + ret;
        c = tokens.get(++curr);
        if(c.getIntType() != 9){
            throw new TokenizedException("Error for token with value: "+ c.toString()
            + ", at Line: " + c.getLineCount()
            + ", Word: " + c.getWordCount());
        }else{
            String r = c.toString() + "\n";
            ret = ret +indent(ind + 1)+ c.toString()+"\n";
        }
        int t = tokens.get(++curr).getIntType();
        if(t < 13 || t == 14) {
            ret = ret + this.parseBody(ind + 1);
        }
        c = tokens.get(curr);
        if(c.getIntType() != 16){
            throw new TokenizedException("Error for token with value: "+ c.toString()
            + ", at Line: " + c.getLineCount()
            + ", Word: " + c.getWordCount());
        }
        ret = indent(ind) + "CofunDef\n" + ret + indent(ind) + c.toString()+"\n";
        return ret;
    }
    private String parseTypeDef(int ind) throws TokenizedException{
        Token c = tokens.get(curr);
        String ret = indent(ind) + c.toString() + "\n";
        c = tokens.get(++curr);
        //makes sure second token is an id
        if(c.getIntType() == 9){
            ret = ret + indent(ind + 1) + c.toString() + "\n";
        }else{
            throw new TokenizedException("Error for token with value: "+ c.toString()
            + ", at Line: " + c.getLineCount()
            + ", Word: " + c.getWordCount());
        }
        //if next token is ID, parse fields
        if(tokens.get(++curr).getIntType() == 9){
            ret = ret + parseFields(ind);
        }
        return ret;
    }
    private String parseFields(int ind) throws TokenizedException{
        String ret = "";
        Token c = tokens.get(curr);
        if(c.getIntType() == 9){
            ret = ret + indent(ind + 1) + c.toString() + "\n";
        }else{
            throw new TokenizedException("Error for token with value: "+ c.toString()
            + ", at Line: " + c.getLineCount()
            + ", Word: " + c.getWordCount());
        }
        //fields can be infinite, as long as next token is ID
        if(tokens.get(++curr).getIntType() == 9){
            ret = ret + parseFields(ind);
        }
        return ret;
    }
    private String parseBody(int ind) throws TokenizedException{
        String ret = "";
        //first token of body is always statement
        ret = ret + this.parseStatement(ind + 1);
        int t = tokens.get(++curr).getIntType();
        //body can continue into newbody
        if(t < 13 || t == 14) {
            ret = ret + this.parseBody(ind + 1);
        }
        ret = indent(ind) + "Body\n" + ret;
        return ret;
    }
    private String parseStatement(int ind) throws TokenizedException{
        String ret = "";
        Token c = tokens.get(curr);
        int t = c.getIntType();
        //checks for type of statement and parses based on that
        if(t == 5){
            ret = ret + this.parseFunDef(ind + 1);
        }else if(t == 6){
            ret = ret + this.parseCofunDef(ind + 1);
        }else if(t == 7){
            ret = ret + this.parseTypeDef(ind + 1);
        }else if(t < 5 || (t > 8 && t < 12)){
            ret = ret + this.parseSimpleStatement(ind + 1);
        }else if(t == 8){
            ret = ret + this.parseVarDef(ind + 1);
        }else if( t == 12 || t == 14){
            ret = ret + parseLambda(ind + 1);
        }
        ret = indent(ind)+ "Statement\n" + ret;
        return ret;
    }
    private String parseVarDef(int ind) throws TokenizedException{
        String ret = indent(ind) + "VarDef\n";
        Token c = tokens.get(curr);

        ret = ret + indent(ind + 1) + c.toString() + "\n";
        c = tokens.get(++curr);
        if(c.getIntType() != 9){
            ret = ret + indent(ind + 1) + c.toString() + "\n";
        }else{
            throw new TokenizedException("Error for token with value: "+ c.toString()
            + ", at Line: " + c.getLineCount()
            + ", Word: " + c.getWordCount());
        }
        return ret;
    }
    //parses a simple statement, always a single end node on the tree
    private String parseSimpleStatement(int ind) throws TokenizedException{
        Token c = tokens.get(curr);
        String ret = c.toString()+"\n";
        ret = indent(ind) + "SimpleStatement\n" + indent(ind+1) + ret;
        return ret;
    }
    private String parseLambda(int ind) throws TokenizedException{
        String ret = indent(ind) + "Lambda\n";
        //check if lambda or colambda
        if(tokens.get(curr).getIntType() == 12){
            ret = ret + parseFunLambda(ind + 1);
        }else if(tokens.get(curr).getIntType() == 12){
            ret = ret + parseCofunLambda(ind + 1);
        }
        return ret;
    }
    private String parseFunLambda(int ind) throws TokenizedException{
        String ret = indent(ind) + "FunLambda\n";
        Token c = tokens.get(curr);

        ret = ret + indent(ind + 1) + c.toString() + "\n";

        c = tokens.get(++curr);
        ret = ret + parseBody(ind + 1);

        //makes sure the lambda function ends
        c = tokens.get(curr);
        if(c.getIntType() != 13){
            throw new TokenizedException("Error for token with value: "+ c.toString()
            + ", at Line: " + c.getLineCount()
            + ", Word: " + c.getWordCount());
        }else{
            ret = ret + indent(ind + 1) + c.toString() + "\n";
        }
        return ret;
    }
    private String parseCofunLambda(int ind) throws TokenizedException{
        String ret = indent(ind) + "CofunLambda\n";
        Token c = tokens.get(curr);

        ret = ret + indent(ind + 1) + c.toString() + "\n";

        ++curr;
        ret = ret + parseBody(ind + 1);

        c = tokens.get(curr);
        if(c.getIntType() != 15){
            throw new TokenizedException("Error for token with value: "+ c.toString()
            + ", at Line: " + c.getLineCount()
            + ", Word: " + c.getWordCount());
        }else{
            ret = ret + indent(ind + 1) + c.toString() + "\n";
        }

        return ret;
    }

    //this returns a string of spaces equal to the required indent
    //the only purpose this serves is to build the tree aesthetically, does not provide any actual parsing value
    private String indent(int ind){
        String ret = "";
        for(int i = 0; i < ind; i++){
            ret = " " + ret;
        }
        return ret;
    }
}
