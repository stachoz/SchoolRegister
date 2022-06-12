package validators;

public class Validator {
    public boolean isInRangeValidator(int lowNum, int highNum, int givenNumber){
        if ((givenNumber >=  lowNum) && (givenNumber <= highNum)) {
            return true;
        }
        return false;
    }
}
