export interface RestResponse<T> {
    /**
     * 状态码
     */
    code?: number;
    /**
     * 数据
     */
    data?: T;
    /**
     * 信息
     */
    msg?: string;
}
