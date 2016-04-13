package monster.org.validator.validator.validate;

import android.content.Context;
import android.widget.TextView;

import monster.org.validator.R;
import monster.org.validator.validator.AbstractValidate;
import monster.org.validator.validator.AbstractValidator;


public class ConfirmValidate extends AbstractValidate {

	private TextView _field1;
	private TextView _field2;
	private Context mContext;
	private TextView source;
	private int _errorMessage = R.string.validator_confirm;
	
	public ConfirmValidate(TextView field1, TextView field2){
		this._field1 = field1;
		this._field2 = field2;
		source = _field2;
		mContext = field1.getContext();
	}

	@Override
	public boolean isValid(String value) {
		if(_field1.getText().toString().length() > 0 && _field1.getText().toString().equals(_field2.getText().toString())){
			return true;
		}else{
			return false;
		}
	}


	@Override
	public String getMessages() {
		// TODO Auto-generated method stub
		return mContext.getString(_errorMessage);
	}


	@Override
	public void addValidator(AbstractValidator validator) {
	}

	@Override
	public TextView getSource() {
		return source;
	}
	
	
}
