package hu.webvalto.orm.crud;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FindOptions {
    private List<FindOption> options;
    public FindOptions() {
        options = new ArrayList<>();
    }
    public void Where(String fieldName, FindType findType, Object value) {
        FindOption findOption = FindOption.Where(fieldName, findType, value);
        options.add(findOption);
    }

    public List<FindOption> getOptions() {
        return options;
    }
}
