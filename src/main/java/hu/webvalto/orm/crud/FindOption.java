package hu.webvalto.orm.crud;

import static hu.webvalto.orm.sql.BaseSQL.WHERE;

public class FindOption {
    private String where;
    private Object value;
    public static FindOption Where(String fieldName, FindType findType, Object value) {
        FindOption findOption = new FindOption();
        switch (findType) {
            case EQUELS:
                findOption.where = WHERE.replace("__COLUMN__", fieldName);
                break;
            case LESS:
                findOption.where = WHERE.replace("__COLUMN__", fieldName).replace("=", "<");
                break;
            case GRATHER:
                findOption.where = WHERE.replace("__COLUMN__", fieldName).replace("=", ">");
                break;
            case LESS_EQ:
                findOption.where = WHERE.replace("__COLUMN__", fieldName).replace("=", "<=");
                break;
            case GRATHER_EQ:
                findOption.where = WHERE.replace("__COLUMN__", fieldName).replace("=", ">=");
                break;
            case LIKE:
                findOption.where = WHERE.replace("__COLUMN__", fieldName).replace("=", "like");
        }
        findOption.value = value;
        return findOption;
    }

}
