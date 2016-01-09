package com.pungwe.cms.core.field;

/**
 * Created by ian on 09/12/2015.
 */
public interface FieldType {

    String getName();

    String getLabel();

    String getDefaultWidget();

    String getDefaultFormatter();

    String getDefaultFieldStorageType();

}
