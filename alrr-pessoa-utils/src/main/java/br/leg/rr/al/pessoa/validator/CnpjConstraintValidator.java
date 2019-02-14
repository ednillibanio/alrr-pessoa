/**
 * 
 */
package br.leg.rr.al.pessoa.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.leg.rr.al.core.utils.MessageUtils;
import br.leg.rr.al.pessoa.CadastroValidationMessages;
import br.leg.rr.al.pessoa.utils.CnpjUtils;

/**
 * @author Ednil Libanio da Costa Junior
 * @date 06-04-2018
 */
public class CnpjConstraintValidator implements ConstraintValidator<Cnpj, String> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.
	 * Annotation)
	 */
	@Override
	public void initialize(Cnpj constraintAnnotation) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object,
	 * javax.validation.ConstraintValidatorContext)
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (value != null && !CnpjUtils.isValid(value.toString())) {

			String cnpj = CnpjUtils.format(value.toString());
			String msg = MessageUtils.formatMessage(CadastroValidationMessages.CNPJ_INVALIDO, cnpj);
			context.disableDefaultConstraintViolation();
			// build new violation message and add it
			context.buildConstraintViolationWithTemplate(msg).addConstraintViolation();
			return false;
		}
		return true;

	}

}
