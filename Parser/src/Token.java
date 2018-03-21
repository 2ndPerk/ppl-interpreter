/**
 * Created by Riley Newson on 18-03-06.
 */
public class Token {
    String type;
    String value;
    int intType;
    int index;
    int wordCount;
    int lineCount;

    public Token(int type, String value, int wordCount, int lineCount){
        setIntType(type);
        setType(type);
        setValue(value);
        setWordCount(wordCount);
        setLineCount(lineCount);
    }

    public int getIndex(){return index;}

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public int getIntType(){
        return intType;
    }

    public void  setIntType(int intType){
        this.intType = intType;

    }

    public void setType(int type) {
        switch(type){
            case Type.INT: //int
                this.type = "int";
                break;
            case Type.FLOAT: //float
                this.type = "float";
                break;
            case Type.CHAR: //char
                this.type = "char";
                break;
            case Type.STRING: //string
                this.type = "string";
                break;
            case Type.FUN: //fun
                this.type = "fun";
                break;
            case Type.COFUN: //cofun
                this.type = "cofun";
                break;
            case Type.TYPE: //type
                this.type = "type";
                break;
            case Type.VAR: //var
                this.type = "var";
                break;
            case Type.ID: //id
                this.type = "id";
                break;
            case Type.SYMBOL: //symbol
                this.type = "symbol";
                break;
            case Type.REFERENCE: //reference
                this.type = "reference";
                break;
            case Type.LAMBDABEGIN: // lambdabegin "["
                this.type = "lambdabegin";
                break;
            case Type.LAMBDAEND: // lambdaend "]"
                this.type = "lambdaend";
                break;
            case Type.COFUNLAMBDASTART: // Cofun Lambda End "[|"
                this.type = "cofunlambdastart";
                break;
            case Type.COFUNLAMBDAEND: // Cofun Lambda End "|]"
                this.type = "cofunlambdaend";
                break;
            case Type.END: // end "."
                this.type = "end";
                break;

        }
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getWordCount(){
        return this.wordCount;
    }

    public void setWordCount(int wordCount){
        this.wordCount = wordCount;
    }

    public void setLineCount(int lineCount){
        this.lineCount = lineCount;
    }

    public int getLineCount(){
        return this.lineCount;
    }

    public String toString(){
        return this.type+"("+this.value+")";
    }
}
