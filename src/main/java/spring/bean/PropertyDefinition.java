package spring.bean;

/**
 * @author ljs
 */

public class PropertyDefinition {
    private String name;
    private String value;
    private String ref;

    public PropertyDefinition(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public PropertyDefinition(String name, String value, String ref) {
        this.name = name;
        this.value = value;
        this.ref = ref;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
