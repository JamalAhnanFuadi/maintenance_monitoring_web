package id.tsi.mmw.rest.validator;

import id.tsi.mmw.util.helper.StringHelper;

import java.util.List;

public class BaseValidator {

    public boolean notNull(Object obj) {
        return null != obj;
    }

    public boolean validate(String str) {
        return StringHelper.validate(str);
    }

    public boolean validate(int number) {
        return number > 0;
    }

    public boolean validate(String... strs) {
        for (String str : strs)
            if (!StringHelper.validate(str)) {
                return false;
            }
        return true;
    }

    public boolean validate(List<String> list) {

        for (String str : list) {
            if (!validate(str)) {
                return false;
            }
        }
        return true;
    }
}
