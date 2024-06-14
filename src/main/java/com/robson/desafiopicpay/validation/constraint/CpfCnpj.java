package com.robson.desafiopicpay.validation.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.robson.desafiopicpay.validation.CpfCnpjValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = CpfCnpjValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CpfCnpj {
    
    String message() default "Cpf ou Cnpj inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
