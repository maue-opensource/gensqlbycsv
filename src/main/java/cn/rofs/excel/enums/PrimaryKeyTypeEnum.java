package cn.rofs.excel.enums;

/**
 * @author rainofsilence
 * @date 2022/8/4 周四
 */
public enum PrimaryKeyTypeEnum implements EnumAbility{
    AUTO_INCREMENT("AI","主键自增"),
    SNOWFLAKES("SN","雪花算法"),
    STRING("S","字符串"),
    NUMBER("N","数值");

    private String code;

    private String desc;

    PrimaryKeyTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public Object getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
