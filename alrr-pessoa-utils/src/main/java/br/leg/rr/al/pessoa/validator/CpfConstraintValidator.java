/**
 * 
 */
package br.leg.rr.al.pessoa.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.leg.rr.al.core.utils.MessageUtils;
import br.leg.rr.al.pessoa.CadastroValidationMessages;
import br.leg.rr.al.pessoa.utils.CnpjUtils;
import br.leg.rr.al.pessoa.utils.CpfUtils;

/**
 * @author Ednil Libanio da Costa Junior
 * @date 11-04-2018
 */
public class CpfConstraintValidator implements ConstraintValidator<Cpf, String> {

	String msg;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.
	 * Annotation)
	 */
	@Override
	public void initialize(Cpf constraintAnnotation) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object,
	 * javax.validation.ConstraintValidatorContext)
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (value != null && !CpfUtils.isValid(value.toString())) {

			String cpf = CnpjUtils.format(value.toString());
			String msg = MessageUtils.formatMessage(CadastroValidationMessages.CPF_INVALIDO, cpf);
			context.disableDefaultConstraintViolation();
			// build new violation message and add it
			context.buildConstraintViolationWithTemplate(msg).addConstraintViolation();
			return false;
		}
		return true;

	}

}
