package cn.rofs.excel.dto;

/**
 * @author rainofsilence
 * @date 2022/8/3 周三
 */
public class ResultDTO<T> {

    private Integer code;

    private String message;

    private T data;

    public ResultDTO(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <E> ResultDTO<E> SUCCESS() {
        return new ResultDTO<>(200, "SUCCESS", null);
    }

    public static <E> ResultDTO<E> FAIL(String message) {
        return new ResultDTO<>(500, message, null);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
