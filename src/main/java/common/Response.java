package common;

/**
 * @Author: Tsuna
 * @Date: 2023-04-23-22:06
 * @Description:
 */

public class Response {
    private boolean success;
    private String message;

    public Response(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}

