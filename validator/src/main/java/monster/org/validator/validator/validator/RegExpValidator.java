package monster.org.validator.validator.validator;

import android.content.Context;

import java.util.regex.Pattern;

import monster.org.validator.R;
import monster.org.validator.validator.AbstractValidator;
import monster.org.validator.validator.ValidatorException;


/**
 * This validator test value with custom Regex Pattern.
 */
public class RegExpValidator extends AbstractValidator {


    private Pattern mPattern;

    private int mErrorMessage = R.string.validator_regexp;

    public RegExpValidator(Context c) {
        super(c);
    }

    public RegExpValidator(Context c, int errorMessage) {
        super(c);
        mErrorMessage = errorMessage;
    }

    public void setPattern(String pattern){
        mPattern = Pattern.compile(pattern);
    }

    public void setPattern(Pattern pattern) {
        mPattern = pattern;
    }

    @Override
    public boolean isValid(String value) throws ValidatorException {
        if(mPattern != null){
            return mPattern.matcher(value).matches();
        }else{
            throw new ValidatorException("You can set Regexp Pattern first");
        }
    }

    @Override
    public String getMessage() {
        return mContext.getString(mErrorMessage);
    }
}
