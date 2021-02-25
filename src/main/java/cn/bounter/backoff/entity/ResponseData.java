package cn.bounter.backoff.entity;

/**
 * 封装请求返回的json数据
 * @param <T>
 */
public class ResponseData<T> {

    //请求成功失败的标志
    private boolean success;

    //成功时返回的数据，可以不传
    private T data;

    //请求出错时的错误信息
    private String errorMsg;

    //10位的时间戳
    private final long timestamp = System.currentTimeMillis() / 1000;

    //返回数据的签名
    private String sign;

    public static <T> ResponseData<T> success() {
        ResponseData<T> responseData = new ResponseData<>();
        responseData.setSuccess(true);
        return responseData;
    }

    public static <T> ResponseData<T> success(T data) {
        ResponseData<T> responseData = new ResponseData<>();
        responseData.setSuccess(true);
        responseData.setData(data);
        return responseData;
    }

    public static<T> ResponseData<T> error(String errorMsg) {
        ResponseData<T> responseData = new ResponseData<>();
        responseData.setSuccess(false);
        responseData.setErrorMsg(errorMsg);
        return responseData;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getSign() {
        return sign;
    }

    public ResponseData<T> sign(String sign) {
        this.sign = sign;
        return this;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
