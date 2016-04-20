/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.field.config;

import com.pungwe.cms.core.field.formatters.*;
import com.pungwe.cms.core.field.services.FieldTypeManagementService;
import com.pungwe.cms.core.field.types.*;
import com.pungwe.cms.core.field.widgets.*;
import com.pungwe.cms.core.security.field.widget.UserCredentialsWidget;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FieldAPIConfig {

    //================================================
    // Field Services
    //================================================

    @Bean
    public FieldTypeManagementService fieldTypeManagementService() {
        return new FieldTypeManagementService();
    }

    //================================================
    // Field Types
    //================================================

    @Bean(name = "boolean_field")
    public BooleanFieldType booleanFieldType() {
        return new BooleanFieldType();
    }

    @Bean(name = "decimal_field")
    public DecimalFieldType decimalFieldType() {
        return new DecimalFieldType();
    }

    @Bean(name = "email_field")
    public EmailFieldType emailFieldType() {
        return new EmailFieldType();
    }

    @Bean(name = "numeric_field")
    public NumericFieldType numericFieldType() {
        return new NumericFieldType();
    }

    @Bean(name = "string_field")
    public StringFieldType stringFieldType() {
        return new StringFieldType();
    }

    //================================================
    // Field Widgets
    //================================================

    @Bean(name = "boolean_widget")
    public BooleanWidget booleanWidget() {
        return new BooleanWidget();
    }

    @Bean(name = "decimal_widget")
    public DecimalWidget decimalWidget() {
        return new DecimalWidget();
    }

    @Bean(name = "email_widget")
    public EmailWidget emailWidget() {
        return new EmailWidget();
    }

    @Bean(name = "numeric_widget")
    public NumericWidget numericWidget() {
        return new NumericWidget();
    }

    @Bean(name = "textarea_widget")
    public TextareaWidget textareaWidget() {
        return new TextareaWidget();
    }

    @Bean(name = "textfield_widget")
    public TextfieldWidget textfieldWidget() {
        return new TextfieldWidget();
    }

    @Bean(name = "user_details_widget")
    public UserCredentialsWidget userDetailsWidget() {
        return new UserCredentialsWidget();
    }

    //================================================
    // Field Formatters
    //================================================

    @Bean(name = "boolean_formatter")
    public BooleanFormatter booleanFormatter() {
        return new BooleanFormatter();
    }

    @Bean(name = "decimal_formatter")
    public DecimalFormatter decimalFormatter() {
        return new DecimalFormatter();
    }

    @Bean(name = "email_formatter")
    public EmailFormatter emailFormatter() {
        return new EmailFormatter();
    }

    @Bean(name = "numeric_formatter")
    public NumericFormatter numericFormatter() {
        return new NumericFormatter();
    }

    @Bean(name = "string_formatter")
    public StringFormatter stringFormatter() {
        return new StringFormatter();
    }
}
