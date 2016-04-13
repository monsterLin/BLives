package monster.org.validator.validator.validator;

import android.content.Context;
import android.util.Patterns;

import java.util.regex.Pattern;

import monster.org.validator.R;
import monster.org.validator.validator.AbstractValidator;
import monster.org.validator.validator.ValidatorException;


/**
 * Validator to check if Phone number is correct.
 * Created by throrin19 on 13/06/13.
 */
public class PhoneValidator extends AbstractValidator {

    private static final Pattern mPattern = Patterns.PHONE;

    private int mErrorMessage = R.string.validator_phone;

    public PhoneValidator(Context c) {
        super(c);
    }

    public PhoneValidator(Context c, int errorMessage) {
        super(c);
        mErrorMessage = errorMessage;
    }

    @Override
    public boolean isValid(String value) throws ValidatorException {
        return mPattern.matcher(value).matches();
    }

    @Override
    public String getMessage() {
        return mContext.getString(mErrorMessage);
    }
}
